package DomainTest;

import Domain.Database;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.UuidRepresentation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {
    private Database database;

    @BeforeEach
    void setUp() {
        database = new Database();
    }

    @Test
    void testMongoClientNotNull() {
        MongoClient mongoClient = database.mongoClient;
        assertNotNull(mongoClient, "MongoClient should not be null");
    }

    @Test
    void testDatabaseNotNull() {
        MongoDatabase mongoDatabase = database.database;
        assertNotNull(mongoDatabase, "MongoDatabase should not be null");
    }

    @Test
    void testDatabaseName() {
        MongoDatabase mongoDatabase = database.database;
        assertEquals("LanceOfDesitny", mongoDatabase.getName(), "Database name should be 'LanceOfDesitny'");
    }
    @Test
    void testConnectionSettings() {
        MongoClientSettings settings = database.settings;

        // Check the srvHost
        assertEquals("cluster0.teltnbl.mongodb.net", settings.getClusterSettings().getSrvHost(), "srvHost should match");

        // Check the hosts
        assertFalse(settings.getClusterSettings().getHosts().isEmpty(), "Hosts should not be empty");
        assertEquals("127.0.0.1", settings.getClusterSettings().getHosts().get(0).getHost(), "Host should match");

        // Check the UUID representation
        assertEquals(UuidRepresentation.STANDARD, settings.getUuidRepresentation(), "UUID representation should be STANDARD");
    }

    @Test
    void testHandleInvalidConnection() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Database("invalid_connection_string");
        }, "Expected IllegalArgumentException");
    }
    @AfterEach
    void tearDown() {
        if (database.mongoClient != null) {
            database.mongoClient.close();
        }
    }
}