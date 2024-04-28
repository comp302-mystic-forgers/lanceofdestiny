package Domain;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class PlayerAccountDAO {
    private MongoCollection<Document> playerAccountCollection;

    public PlayerAccountDAO(Database connection) {
        playerAccountCollection = connection.database.getCollection("playerAccount");
    }

    public void savePlayerAccount(PlayerAccount playerAccount) {
        Document document = new Document()
                .append("username", playerAccount.getUsername())
                .append("password", playerAccount.getPassword())
                .append("games", playerAccount.getGames());

        playerAccountCollection.insertOne(document);
    }

    public PlayerAccount findPlayerAccountByUsername(String username) {
        FindIterable<Document> iterable = playerAccountCollection.find(new Document("username", username));
        Document document = iterable.first();
        if (document == null) {
            return null;
        }
        String usernameFromDB = document.getString("username");
        String passwordFromDB = document.getString("password");
        List<GameInfo> gamesFromDB = new ArrayList<>();
        // Convert the "games" field from the document to a list of GameInfo objects
        // ...
        PlayerAccount playerAccount = new PlayerAccount(usernameFromDB, passwordFromDB, gamesFromDB);
        return playerAccount;
    }

    public PlayerAccount loadPlayerAccount(String username) {

        FindIterable<Document> iterable = playerAccountCollection.find(new Document("username", username));
        MongoCursor<Document> cursor = iterable.iterator();

        if (cursor.hasNext()) {
            Document document = cursor.next();
            List<Document> games = (List<Document>) document.get("games");
            List<GameInfo> gameInfos = new ArrayList();

            for (Document game : games) {
                int simpleBarriersRemaining = game.getInteger("simpleBarriersRemaining");
                int reinforcedBarriersRemaining = game.getInteger("reinforcedBarriersRemaining");
                int explosiveBarriersRemaining = game.getInteger("explosiveBarriersRemaining");
                int rewardingBarriersRemaining = game.getInteger("rewardingBarriersRemaining");
                int lives = game.getInteger("lives");
                int score = game.getInteger("score");
                List<String> spellsAcquired = (List<String>) game.get("spellsAcquired");

                GameInfo gameInfo = new GameInfo(simpleBarriersRemaining, reinforcedBarriersRemaining, explosiveBarriersRemaining, rewardingBarriersRemaining, lives, score, spellsAcquired);
                gameInfos.add(gameInfo);
            }

            PlayerAccount playerAccount = new PlayerAccount(document.getString("username"), document.getString("password"));
            playerAccount.setGames(gameInfos);

            return playerAccount;
        } else {
            return null;
        }
    }

}