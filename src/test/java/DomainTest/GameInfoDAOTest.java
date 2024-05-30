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

import java.awt.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.List;

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

    /*@Test
    public void testSaveGameInfo() {
        // Arrange
        GameInfo mockGameInfo = createMockGameInfo();

        // Act
        gameInfoDAO.saveGameInfo(mockGameInfo);

        // Assert
        verify(mockCollection).insertOne(any(Document.class));
    }*/

    private GameInfo createMockGameInfo() {
        UUID gameId = UUID.randomUUID();
        PlayerAccount player = new PlayerAccount("player1", "username1");
        List<Spell> spells = Arrays.asList(
                new Hex(new MagicalStaff(20, 20)),
                new OverwhelmingFireBall(new FireBall(50, 20, Color.RED)),
                new MagicalStaffExp(new MagicalStaff(20, 20))
        );
        List<SimpleBarrier> simpleBarriers = Arrays.asList(
                new SimpleBarrier(0, 0, 10, 10)
        );
        List<ReinforcedBarrier> reinforcedBarriers = Arrays.asList(
                new ReinforcedBarrier(10, 10, 20, 20)
        );
        List<RewardingBarrier> rewardingBarriers = Arrays.asList(
                new RewardingBarrier(30, 30, 40, 40)
        );
        List<ExplosiveBarrier> explosiveBarriers = Arrays.asList(
                new ExplosiveBarrier(60, 60, 60, 60)
        );
        return new GameInfo(player.getPlayerId(), 100, 3, GameState.ACTIVE, new Date(), spells, simpleBarriers, reinforcedBarriers, rewardingBarriers, explosiveBarriers);
    }

    @Test
    public void testSaveGameInfoNoSpellsNoBarriers() {
        // Arrange
        PlayerAccount player = new PlayerAccount("player1", "username1");
        GameInfo gameInfo = new GameInfo(player.getUsername(), 100, 3, GameState.ACTIVE, new Date(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList());

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
        GameInfo gameInfo = new GameInfo(player.getUsername(), 100, 3, GameState.ACTIVE, new Date(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList());

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
                new OverwhelmingFireBall(new FireBall(50, 20, Color.RED)),
                new MagicalStaffExp(new MagicalStaff(20, 20))
        );
        GameInfo gameInfo = new GameInfo(player.getUsername(), 100, 3, GameState.ACTIVE, new Date(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList());

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
        List<SimpleBarrier> simpleBarriers = Arrays.asList(
                new SimpleBarrier(0, 0, 10, 10)
        );
        List<ReinforcedBarrier> reinforcedBarriers = Arrays.asList(
                new ReinforcedBarrier(10, 10, 20, 20)
        );
        List<RewardingBarrier> rewardingBarriers = Arrays.asList(
                new RewardingBarrier(30, 30, 40, 40)
        );
        List<ExplosiveBarrier> explosiveBarriers = Arrays.asList(
                new ExplosiveBarrier(60, 60, 60, 60)
        );
        GameInfo gameInfo = new GameInfo(player.getUsername(), 100, 3, GameState.ACTIVE, new Date(), Collections.emptyList(), simpleBarriers, reinforcedBarriers, rewardingBarriers, explosiveBarriers);

        // Act
        gameInfoDAO.saveGameInfo(gameInfo);

        // Assert
        ArgumentCaptor<Document> captor = ArgumentCaptor.forClass(Document.class);
        verify(mockCollection).insertOne(captor.capture());
        Document insertedDoc = captor.getValue();

        List<Document> insertedSimpleBarriers = (List<Document>) insertedDoc.get("simple_barriers");
        assertNotNull(insertedSimpleBarriers);
        assertEquals(1, insertedSimpleBarriers.size());
        assertEquals("SimpleBarrier", insertedSimpleBarriers.get(0).getString("type"));

        List<Document> insertedReinforcedBarriers = (List<Document>) insertedDoc.get("reinforced_barriers");
        assertNotNull(insertedReinforcedBarriers);
        assertEquals(1, insertedReinforcedBarriers.size());
        assertEquals("ReinforcedBarrier", insertedReinforcedBarriers.get(0).getString("type"));

        List<Document> insertedRewardingBarriers = (List<Document>) insertedDoc.get("rewarding_barriers");
        assertNotNull(insertedRewardingBarriers);
        assertEquals(1, insertedRewardingBarriers.size());
        assertEquals("RewardingBarrier", insertedRewardingBarriers.get(0).getString("type"));

        List<Document> insertedExplosiveBarriers = (List<Document>) insertedDoc.get("explosive_barriers");
        assertNotNull(insertedExplosiveBarriers);
        assertEquals(1, insertedExplosiveBarriers.size());
        assertEquals("ExplosiveBarrier", insertedExplosiveBarriers.get(0).getString("type"));
    }


    @Test
    public void testSaveGameInfoMultipleBarriers() {
        // Arrange
        UUID gameId = UUID.randomUUID();
        PlayerAccount player = new PlayerAccount("player1", "username1");
        List<SimpleBarrier> simpleBarriers = Arrays.asList(
                new SimpleBarrier(0, 0, 10, 10)
        );
        List<ExplosiveBarrier> explosiveBarriers = Arrays.asList(
                new ExplosiveBarrier(10, 10, 20, 20)
        );
        GameInfo gameInfo = new GameInfo(player.getUsername(), 100, 3, GameState.ACTIVE, new Date(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList());

        // Act
        gameInfoDAO.saveGameInfo(gameInfo);

        // Assert
        verify(mockCollection).insertOne(any(Document.class));
    }
    @Test
    public void testSaveGameInfoNullPlayer() {
        // Arrange
        UUID gameId = UUID.randomUUID();
        GameInfo gameInfo = new GameInfo(null, 100, 3, GameState.ACTIVE, new Date(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList());

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
        GameInfo gameInfo = new GameInfo(player.getUsername(), 100, 3, GameState.ACTIVE, new Date(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList());

        // Act
        gameInfoDAO.saveGameInfo(gameInfo);

        // Assert
        verify(mockCollection).insertOne(any(Document.class));
    }
}
