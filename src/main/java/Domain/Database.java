package src.main.java.Domain;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import org.bson.UuidRepresentation;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final String CONNECTION_STRING = "mongodb+srv://gaymaz19:YkGu8iPQz2oj7MfZ@cluster0.teltnbl.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";

    public MongoClient mongoClient;
    public MongoDatabase database;
    ConnectionString connString = new ConnectionString(CONNECTION_STRING);
    MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(connString)
            .uuidRepresentation(UuidRepresentation.STANDARD)
            .build();

    public Database() {
        mongoClient = MongoClients.create(settings);
        database = mongoClient.getDatabase("LanceOfDesitny");
    }
}