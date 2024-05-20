package DomainTest;
import static org.junit.jupiter.api.Assertions.*;

import Domain.Score;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ScoreTest {
    private Score score;

    @BeforeEach
    public void setUp() {
        score = new Score();
    }

    @Test   // Black-Box Testing
            //Testing initial score
    public void testInitialScore() {
        assertEquals(0, score.getScoreValue(), "Initial score should be zero.");
    }

    @Test   // Black-Box Testing
            // Testing if score is updated
    public void testUpdateScoreIncreasesScore() {
        long initialScore = score.getScoreValue();
        score.updateScore();
        assertTrue(score.getScoreValue() > initialScore, "Score should increase after update.");
    }

    @Test   // Glass-Box Testing
    // Testing the update in exactly 200 ms elapsed time
    public void testUpdateScoreWithExact200msElapsed() throws InterruptedException {
        Thread.sleep(200);
        score.updateScore();
        int newScore;
        newScore = 300/200;
        assertEquals(newScore, score.getScoreValue(), "Score should increase by 200's division to 300 with exactly 200 ms elapsed.");
    }

    @Test   // Glass-Box Testing
    // Testing the update in less than 300 ms elapsed time
    public void testUpdateScoreWithLessThan300msElapsed() throws InterruptedException {
        Thread.sleep(100); // Sleep to simulate less than 300 ms elapsed time
        score.updateScore();
        assertTrue(score.getScoreValue() > 0 && score.getScoreValue() < 300, "Score should be less than 300 for elapsed time less than 300 ms.");
    }

    @Test   // Glass-Box Testing
    // Testing the update in more than 1000 ms elapsed time
    public void testUpdateScoreWithMoreThan1000msElapsed() throws InterruptedException {
        Thread.sleep(1500); // Sleep to simulate more than 300 ms elapsed time
        score.updateScore();
        assertTrue(score.getScoreValue() < 1000, "Score increase should be less than 1000 when more than 300 ms have elapsed.");
    }

    @Test   // Black-Box Testing
    // Testing if multiple update is worked.
    public void testMultipleUpdates() {
        score.updateScore();
        long firstUpdate = score.getScoreValue();
        score.updateScore();
        long secondUpdate = score.getScoreValue();
        assertTrue(secondUpdate > firstUpdate, "Score should progressively increase on each update.");
    }
}

