package Domain;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import org.bson.UuidRepresentation;

import java.util.ArrayList;
import java.util.List;
public class Database {
    private static final String DEFAULT_CONNECTION_STRING = "mongodb+srv://gaymaz19:YkGu8iPQz2oj7MfZ@cluster0.teltnbl.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
    public MongoClient mongoClient;
    public MongoDatabase database;
    public MongoClientSettings settings;

    public Database() {
        this(DEFAULT_CONNECTION_STRING);
    }

    public Database(String connectionString) {
        ConnectionString connString = new ConnectionString(connectionString);
        settings = MongoClientSettings.builder()
                .applyConnectionString(connString)
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .build();
        mongoClient = MongoClients.create(settings);
        database = mongoClient.getDatabase("LanceOfDesitny");
    }
}