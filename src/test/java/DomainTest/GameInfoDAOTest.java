package DomainTest;

import Domain.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.*;

import static com.mongodb.assertions.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameInfoDAOTest {

    @Mock
    private MongoDatabase mockDatabase;

    @Mock
    private MongoCollection<Document> mockCollection;

    @Mock
    private Database mockConnection;

    private GameInfoDAO gameInfoDAO;

    @BeforeEach
    public void setUp() throws Exception {
        // Initialize the mocks
        MockitoAnnotations.openMocks(this);

        // Set the database field of the mockConnection to mockDatabase
        Field databaseField = Database.class.getDeclaredField("database");
        databaseField.setAccessible(true);
        databaseField.set(mockConnection, mockDatabase);

        // Mock the getCollection method of the mockDatabase
        when(mockDatabase.getCollection("Games")).thenReturn(mockCollection);

        // Initialize the GameInfoDAO with the mockConnection
        gameInfoDAO = new GameInfoDAO(mockConnection);
    }

    @Test
    public void testSaveGameInfo() {
        // Arrange
        GameInfo mockGameInfo = createMockGameInfo();

        // Act
        gameInfoDAO.saveGameInfo(mockGameInfo);

        // Assert
        verify(mockCollection).insertOne(any(Document.class));
    }

    private GameInfo createMockGameInfo() {
        UUID gameId = UUID.randomUUID();
        PlayerAccount player = new PlayerAccount("player1", "username1");
        List<Spell> spells = Arrays.asList(
                new Hex(new MagicalStaff(20, 20)),
                new OverwhelmingFireBall(new FireBall(50, 20)),
                new MagicalStaffExp(new MagicalStaff(20, 20))
        );
        List<Barrier> barriers = Arrays.asList(
                new SimpleBarrier(0, 0, 10, 10),
                new ExplosiveBarrier(10, 10, 20, 20)
        );
        return new GameInfo(gameId, player, 100, 3, GameState.ACTIVE, new Date(), spells, barriers);
    }

    @Test
    public void testSaveGameInfoNoSpellsNoBarriers() {
        // Arrange
        UUID gameId = UUID.randomUUID();
        PlayerAccount player = new PlayerAccount("player1", "username1");
        GameInfo gameInfo = new GameInfo(gameId, player, 100, 3, GameState.ACTIVE, new Date(), Collections.emptyList(), Collections.emptyList());

        // Act
        gameInfoDAO.saveGameInfo(gameInfo);

        // Assert
        verify(mockCollection).insertOne(any(Document.class));
    }
    @Test
    public void testSaveGameInfoSingleSpellSingleBarrier() {
        // Arrange
        UUID gameId = UUID.randomUUID();
        PlayerAccount player = new PlayerAccount("player1", "username1");
        List<Spell> spells = Collections.singletonList(new Hex(new MagicalStaff(20, 20)));
        List<Barrier> barriers = Collections.singletonList(new SimpleBarrier(0, 0, 10, 10));
        GameInfo gameInfo = new GameInfo(gameId, player, 100, 3, GameState.ACTIVE, new Date(), spells, barriers);

        // Act
        gameInfoDAO.saveGameInfo(gameInfo);

        // Assert
        verify(mockCollection).insertOne(any(Document.class));
    }
    @Test
    public void testSaveGameInfoMultipleSpells() {
        // Arrange
        UUID gameId = UUID.randomUUID();
        PlayerAccount player = new PlayerAccount("player1", "username1");
        List<Spell> spells = Arrays.asList(
                new Hex(new MagicalStaff(20, 20)),
                new OverwhelmingFireBall(new FireBall(50, 20)),
                new MagicalStaffExp(new MagicalStaff(20, 20))
        );
        GameInfo gameInfo = new GameInfo(gameId, player, 100, 3, GameState.ACTIVE, new Date(), spells, Collections.emptyList());

        // Act
        gameInfoDAO.saveGameInfo(gameInfo);

        // Assert
        verify(mockCollection).insertOne(any(Document.class));
    }
    @Test
    public void testSaveGameInfoWithDifferentBarrierTypes() {
        // Arrange
        UUID gameId = UUID.randomUUID();
        PlayerAccount player = new PlayerAccount("player1", "username1");
        List<Barrier> barriers = Arrays.asList(
                new SimpleBarrier(0, 0, 10, 10),
                new RewardingBarrier(10, 10, 20, 20),
                new ReinforcedBarrier(30, 30, 40, 40),
                new ExplosiveBarrier(60, 60, 60, 60)
        );
        GameInfo gameInfo = new GameInfo(gameId, player, 100, 3, GameState.ACTIVE, new Date(), Collections.emptyList(), barriers);

        // Act
        gameInfoDAO.saveGameInfo(gameInfo);

        // Assert
        ArgumentCaptor<Document> captor = ArgumentCaptor.forClass(Document.class);
        verify(mockCollection).insertOne(captor.capture());
        Document insertedDoc = captor.getValue();
        List<Document> insertedBarriers = (List<Document>) insertedDoc.get("barriersRemaining");

        assertNotNull(insertedBarriers);
        assertEquals(4, insertedBarriers.size());

        Document simpleBarrier = insertedBarriers.get(0);
        assertEquals("SimpleBarrier", simpleBarrier.getString("type"));
        assertEquals(0, simpleBarrier.get("x_loc"));
        assertEquals(0, simpleBarrier.get("y_loc"));

        Document rewardingBarrier = insertedBarriers.get(1);
        assertEquals("RewardingBarrier", rewardingBarrier.getString("type"));
        assertEquals(10, rewardingBarrier.get("x_loc"));
        assertEquals(10, rewardingBarrier.get("y_loc"));

        Document reinforcedBarrier = insertedBarriers.get(2);
        assertEquals("ReinforcedBarrier", reinforcedBarrier.getString("type"));
        assertEquals(30, reinforcedBarrier.get("x_loc"));
        assertEquals(30, reinforcedBarrier.get("y_loc"));
    }


    @Test
    public void testSaveGameInfoMultipleBarriers() {
        // Arrange
        UUID gameId = UUID.randomUUID();
        PlayerAccount player = new PlayerAccount("player1", "username1");
        List<Barrier> barriers = Arrays.asList(
                new SimpleBarrier(0, 0, 10, 10),
                new ExplosiveBarrier(10, 10, 20, 20)
        );
        GameInfo gameInfo = new GameInfo(gameId, player, 100, 3, GameState.ACTIVE, new Date(), Collections.emptyList(), barriers);

        // Act
        gameInfoDAO.saveGameInfo(gameInfo);

        // Assert
        verify(mockCollection).insertOne(any(Document.class));
    }
    @Test
    public void testSaveGameInfoNullPlayer() {
        // Arrange
        UUID gameId = UUID.randomUUID();
        GameInfo gameInfo = new GameInfo(gameId, null, 100, 3, GameState.ACTIVE, new Date(), Collections.emptyList(), Collections.emptyList());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            gameInfoDAO.saveGameInfo(gameInfo);
        });

        // Verify that insertOne is never called
        verify(mockCollection, never()).insertOne(any(Document.class));
    }

    @Test
    public void testSaveGameInfoDifferentGameState() {
        // Arrange
        UUID gameId = UUID.randomUUID();
        PlayerAccount player = new PlayerAccount("player1", "username1");
        GameInfo gameInfo = new GameInfo(gameId, player, 100, 3, GameState.PASSIVE, new Date(),  Collections.emptyList(), Collections.emptyList());

        // Act
        gameInfoDAO.saveGameInfo(gameInfo);

        // Assert
        verify(mockCollection).insertOne(any(Document.class));
    }

}
