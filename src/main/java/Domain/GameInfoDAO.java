package Domain;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GameInfoDAO {

    protected MongoCollection<Document> gameInfoCollection;

    public GameInfoDAO(Database connection) {
        gameInfoCollection = connection.database.getCollection("Games");
    }

    public void saveGameInfo(GameInfo gameInfo) {
        if (gameInfo.getPlayerId() == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        Document gameDoc = new Document()
                .append("player_id", gameInfo.getPlayerId())
                .append("score", gameInfo.getScore())
                .append("lives", gameInfo.getLives())
                .append("game_state", gameInfo.getGameState().toString())
                .append("last_saved", gameInfo.getLastSaved());

        Map<String, Long> spellsCountMap = gameInfo.getSpellsAcquired().stream()
                .collect(Collectors.groupingBy(spell -> spell.getClass().getSimpleName(), Collectors.counting()));

        List<Document> spellsList = spellsCountMap.entrySet().stream()
                .map(entry -> new Document("type", entry.getKey()).append("count", entry.getValue()))
                .collect(Collectors.toList());

        if (!spellsList.isEmpty()) {
            gameDoc.append("spellsAcquired", spellsList);
        }
        List<Document> simpleBarriersList = gameInfo.getSimpleBarrierList().isEmpty() ?
                null :
                gameInfo.getSimpleBarrierList().stream()
                        .filter(barrier -> !barrier.isDestroyed())
                        .map(barrier -> {
                            Document barrierDoc = new Document()
                                    .append("type", barrier.getClass().getSimpleName())
                                    .append("x_loc", ((SimpleBarrier) barrier).getX())
                                    .append("y_loc", ((SimpleBarrier) barrier).getY())
                                    .append("width", ((SimpleBarrier) barrier) .getWidth())
                                    .append("height", ((SimpleBarrier) barrier) .getHeight());
                            return barrierDoc;
                        })
                        .collect(Collectors.toList());

        if (simpleBarriersList != null) {
            gameDoc.append("simple_barriers", simpleBarriersList);
        }
        List<Document> reinforcedBarriersList = gameInfo.getReinforcedBarrierList().isEmpty() ?
                null :
                gameInfo.getReinforcedBarrierList().stream()
                        .filter(barrier -> !barrier.isDestroyed())
                        .map(barrier -> {
                            Document barrierDoc = new Document()
                                    .append("type", barrier.getClass().getSimpleName())
                                    .append("hits_received", ((ReinforcedBarrier) barrier).getHitsReceived())
                                    .append("x_loc", ((ReinforcedBarrier) barrier).getX())
                                    .append("y_loc", ((ReinforcedBarrier) barrier).getY())
                                    .append("width", ((ReinforcedBarrier) barrier) .getWidth())
                                    .append("height", ((ReinforcedBarrier) barrier) .getHeight());
                            return barrierDoc;
                        })
                        .collect(Collectors.toList());

        if (reinforcedBarriersList != null) {
            gameDoc.append("reinforced_barriers", reinforcedBarriersList);
        }

        List<Document> rewardingBarriersList = gameInfo.getRewardingBarrierList().isEmpty() ?
                null :
                gameInfo.getRewardingBarrierList().stream()
                        .filter(barrier -> !barrier.isDestroyed())
                        .map(barrier -> {
                            Document barrierDoc = new Document()
                                    .append("type", barrier.getClass().getSimpleName())
                                    .append("x_loc", ((RewardingBarrier) barrier).getX())
                                    .append("y_loc", ((RewardingBarrier) barrier).getY())
                                    .append("width", ((RewardingBarrier) barrier).getWidth())
                                    .append("height", ((RewardingBarrier) barrier).getHeight());
                            return barrierDoc;
                        })
                        .collect(Collectors.toList());

        if (rewardingBarriersList!= null) {
            gameDoc.append("rewarding_barriers", rewardingBarriersList);
        }
        List<Document> explosiveBarriersList = gameInfo.getExplosiveBarrierList().isEmpty() ?
                null :
                gameInfo.getExplosiveBarrierList().stream()
                        .filter(barrier -> !barrier.isDestroyed())
                        .map(barrier -> {
                            Document barrierDoc = new Document()
                                    .append("type", barrier.getClass().getSimpleName())
                                    .append("x_loc", ((ExplosiveBarrier) barrier).getX())
                                    .append("y_loc", ((ExplosiveBarrier) barrier).getY())
                                    .append("width", ((ExplosiveBarrier) barrier).getWidth())
                                    .append("height", ((ExplosiveBarrier) barrier).getHeight());
                            return barrierDoc;
                        })
                        .collect(Collectors.toList());

        if (explosiveBarriersList!= null) {
            gameDoc.append("explosive_barriers", explosiveBarriersList);
        }


        gameInfoCollection.insertOne(gameDoc);
    }

    public List<GameInfo> loadGameInfo(String playerId) {
        List<Document> gameDocs = gameInfoCollection.find(Filters.eq("player_id", playerId)).into(new ArrayList<>());

        return gameDocs.stream().map(gameDoc -> {

            GameInfo gameInfo = new GameInfo(
                    gameDoc.getString("player_id"),
                    gameDoc.getInteger("score"),
                    gameDoc.getInteger("lives"),
                    GameState.valueOf(gameDoc.getString("game_state")),
                    gameDoc.getDate("last_saved"),
                    new ArrayList<>(),
                    new ArrayList<>(),
                    new ArrayList<>(),
                    new ArrayList<>(),
                    new ArrayList<>()
            );
            List<Document> spellsDocs = gameDoc.getList("spellsAcquired", Document.class, new ArrayList<>());
            List<Spell> spells = new ArrayList<>();

            MagicalStaff magicalStaff = new MagicalStaff(1280, 620);
            for (Document spellDoc : spellsDocs) {
                String type = spellDoc.getString("type");
                long count = spellDoc.getLong("count");
                for (long i = 0; i < count; i++) {
                    switch (type) {
                        case "Hex":
                            spells.add(new Hex(magicalStaff));
                            break;
                        case "OverwhelmingFireBall":
                            FireBall fireBall = new FireBall(magicalStaff.getX() + magicalStaff.getWidth() / 2 - 8, magicalStaff.getY() - 16, null);
                            spells.add(new OverwhelmingFireBall(fireBall));
                            break;
                        case "MagicalStaffExp":
                            spells.add(new MagicalStaffExp(magicalStaff));
                            break;
                        default:
                            throw new IllegalArgumentException("Unknown spell type: " + type);
                    }
                }
            }
            gameInfo.setSpellsAcquired(spells);

            List<Document> simpleBarrierDocs = gameDoc.getList("simple_barriers", Document.class, new ArrayList<>());
            List<SimpleBarrier> simpleBarriers = simpleBarrierDocs.stream().map(barrierDoc -> {
                String type = barrierDoc.getString("type");
                int x = barrierDoc.getInteger("x_loc");
                int y = barrierDoc.getInteger("y_loc");
                return new SimpleBarrier(x, y, barrierDoc.getInteger("width"), barrierDoc.getInteger("height"));
            }).collect(Collectors.toList());
            gameInfo.setSimpleBarrierList(simpleBarriers);

            List<Document> reinforcedBarrierDocs = gameDoc.getList("reinforced_barriers", Document.class, new ArrayList<>());
            List<ReinforcedBarrier> reinforcedBarriers = reinforcedBarrierDocs.stream().map(barrierDoc -> {
                String type = barrierDoc.getString("type");
                int x = barrierDoc.getInteger("x_loc");
                int y = barrierDoc.getInteger("y_loc");
                ReinforcedBarrier reinforcedBarrier = new ReinforcedBarrier(x, y, barrierDoc.getInteger("width"), barrierDoc.getInteger("height"));
                reinforcedBarrier.setHitsReceived(barrierDoc.getInteger("hits_received"));
                return reinforcedBarrier;
            }).collect(Collectors.toList());
            gameInfo.setReinforcedBarrierList(reinforcedBarriers);

            List<Document> rewardingBarrierDocs = gameDoc.getList("rewarding_barriers", Document.class, new ArrayList<>());
            List<RewardingBarrier> rewardingBarriers = rewardingBarrierDocs.stream().map(barrierDoc -> {
                String type = barrierDoc.getString("type");
                int x = barrierDoc.getInteger("x_loc");
                int y = barrierDoc.getInteger("y_loc");
                return new RewardingBarrier(x, y, barrierDoc.getInteger("width"), barrierDoc.getInteger("height"));
            }).collect(Collectors.toList());
            gameInfo.setRewardingBarrierList(rewardingBarriers);

            List<Document> explosiveBarrierDocs = gameDoc.getList("explosive_barriers", Document.class, new ArrayList<>());
            List<ExplosiveBarrier> explosiveBarriers = explosiveBarrierDocs.stream().map(barrierDoc -> {
                String type = barrierDoc.getString("type");
                int x = barrierDoc.getInteger("x_loc");
                int y = barrierDoc.getInteger("y_loc");
                return new ExplosiveBarrier(x, y, barrierDoc.getInteger("width"), barrierDoc.getInteger("height"));
            }).collect(Collectors.toList());
            gameInfo.setExplosiveBarrierList(explosiveBarriers);

            return gameInfo;
        }).collect(Collectors.toList());
    }
}
