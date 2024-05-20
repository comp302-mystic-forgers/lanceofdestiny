package Domain;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.List;
import java.util.stream.Collectors;

public class GameInfoDAO {

    protected MongoCollection<Document> gameInfoCollection;

    public GameInfoDAO(Database connection) {
        gameInfoCollection = connection.database.getCollection("Games");
    }

    public void saveGameInfo(GameInfo gameInfo) {
        if (gameInfo.getPlayer() == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        Document gameDoc = new Document("game_id", gameInfo.getGameId())
                .append("player", new Document("player_id", gameInfo.getPlayer().getPlayerId())
                        .append("username", gameInfo.getPlayer().getUsername()))
                .append("score", gameInfo.getScore())
                .append("lives", gameInfo.getLives())
                .append("game_state", gameInfo.getGameState().toString())
                .append("last_saved", gameInfo.getLastSaved());
        List<Document> spellsList = gameInfo.getSpellsAcquired().isEmpty() ?
                null :
                gameInfo.getSpellsAcquired().stream()
                        .map(spell -> {
                            Document spellDoc = new Document("spell_id", spell.getSpellId())
                                    .append("type", spell.getClass().getSimpleName());
                            if (spell instanceof Hex) {
                                spellDoc.append("staff_equipped", ((Hex) spell).getStaff().isCanonsEquipped());
                            } else if (spell instanceof OverwhelmingFireBall) {
                                spellDoc.append("activated", ((OverwhelmingFireBall) spell).isActivated())
                                        .append("activation_time", ((OverwhelmingFireBall) spell).getTime());
                            } else if (spell instanceof MagicalStaffExp) {
                                spellDoc.append("activated", ((MagicalStaffExp) spell).isActivated())
                                        .append("activation_time", ((MagicalStaffExp) spell).getTime());
                            } else if (spell instanceof FelixFelicis) {
                                spellDoc.append("luck_factor", ((FelixFelicis) spell).getLuckFactor());
                            }
                            return spellDoc;
                        })
                        .collect(Collectors.toList());

        if (spellsList != null) {
            gameDoc.append("spellsAcquired", spellsList);
        }
        List<Document> barriersList = gameInfo.getBarriersRemaining().isEmpty() ?
                null :
                gameInfo.getBarriersRemaining().stream()
                        .map(barrier -> {
                            Document barrierDoc = new Document("barrierId", barrier.getBarrierId())
                                    .append("type", barrier.getClass().getSimpleName());

                            if (barrier instanceof SimpleBarrier) {
                                barrierDoc.append("x_loc", ((SimpleBarrier) barrier).getX())
                                        .append("y_loc", ((SimpleBarrier) barrier).getY());
                            } else if (barrier instanceof RewardingBarrier) {
                                barrierDoc.append("x_loc", ((RewardingBarrier) barrier).getX())
                                        .append("y_loc", ((RewardingBarrier) barrier).getY())
                                        .append("destroyed", ((RewardingBarrier) barrier).isDestroyed());
                            } else if (barrier instanceof ReinforcedBarrier) {
                                barrierDoc.append("hitsRequired", ((ReinforcedBarrier) barrier).getHitsRequired())
                                        .append("hitsReceived", ((ReinforcedBarrier) barrier).getHitsReceived())
                                        .append("x_loc", ((ReinforcedBarrier) barrier).getX())
                                        .append("y_loc", ((ReinforcedBarrier) barrier).getY());
                            } else if (barrier instanceof ExplosiveBarrier) {
                                barrierDoc.append("destroyed", ((ExplosiveBarrier) barrier).isDestroyed())
                                        .append("x_loc", ((ExplosiveBarrier) barrier).getX())
                                        .append("y_loc", ((ExplosiveBarrier) barrier).getY());
                            }
                            return barrierDoc;
                        })
                        .collect(Collectors.toList());

        if (barriersList != null) {
            gameDoc.append("barriersRemaining", barriersList);
        }

        gameInfoCollection.insertOne(gameDoc);
    }

}
