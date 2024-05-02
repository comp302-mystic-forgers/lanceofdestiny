package Domain;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final String CONNECTION_STRING = "mongodb+srv://vuk23:AqqXgvXcPx5eQ7sY@cluster0.teltnbl.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";

    public MongoClient mongoClient;
    public MongoDatabase database;

    public Database() {
        mongoClient = MongoClients.create(CONNECTION_STRING);
        database = mongoClient.getDatabase("LanceOfDesitny");
    }
}