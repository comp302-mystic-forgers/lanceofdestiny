package Domain;

public class Score {
    private int scoreValue;
    private long gameStartingTime;

    public Score() {
        this.scoreValue = 0;
        this.gameStartingTime = System.currentTimeMillis();
    }

    public void updateScore(int barrierDestroyTime) {
        long currentTime = System.currentTimeMillis();
        // Calculate score based on barrier destroy time
        int newScore = scoreValue + 300 / (int) (currentTime - gameStartingTime);
        scoreValue = newScore;
    }

    public int getScoreValue() {
        return scoreValue;
    }
}

