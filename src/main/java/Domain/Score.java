package Domain;

public class Score {
    private long scoreValue;
    private long gameStartingTime;

    public Score() {
        this.scoreValue = 0;
        this.gameStartingTime = System.currentTimeMillis()/1000;
    }

    public void updateScore() {
        long currentTime = System.currentTimeMillis()/1000;
        long elapsedTime = currentTime - gameStartingTime;
        if (elapsedTime <= 0) {
            elapsedTime = 1; // Add a small constant value to prevent division by zero
        }
        /**if (elapsedTime >= 300) {
            elapsedTime = elapsedTime/ 1000; // Try to keep increasing the score equally
        }
        **/
        // Calculate score based on barrier destroy time
        long newScore = (scoreValue + (300 / elapsedTime));
        scoreValue = newScore;
        System.out.println(gameStartingTime);
        System.out.println(currentTime);
        System.out.println(elapsedTime);
        System.out.println(getScoreValue());

    }

    public long getScoreValue() {
        return scoreValue;
    }

    public static void main(String[] args){
        Score score = new Score();
        score.updateScore();score.updateScore();

    }

    public void setScoreValue(long scoreValue) {
        this.scoreValue = scoreValue;
    }
}




