package Domain;

import java.util.List;

public class GameInfo {
    private int simpleBarriersRemaining;
    private int reinforcedBarriersRemaining;
    private int explosiveBarriersRemaining;
    private int rewardingBarriersRemaining;
    private int lives;
    private int score;
    private List<String> spellsAcquired;

    public GameInfo(int simpleBarriersRemaining, int reinforcedBarriersRemaining, int explosiveBarriersRemaining, int rewardingBarriersRemaining, int lives, int score, List<String> spellsAcquired) {
        this.simpleBarriersRemaining = simpleBarriersRemaining;
        this.reinforcedBarriersRemaining = reinforcedBarriersRemaining;
        this.explosiveBarriersRemaining = explosiveBarriersRemaining;
        this.rewardingBarriersRemaining = rewardingBarriersRemaining;
        this.lives = lives;
        this.score = score;
        this.spellsAcquired = spellsAcquired;
    }

    // ... Getters and setters for the attributes

    public int getSimpleBarriersRemaining() {
        return simpleBarriersRemaining;
    }

    public void setSimpleBarriersRemaining(int simpleBarriersRemaining) {
        this.simpleBarriersRemaining = simpleBarriersRemaining;
    }

    public int getReinforcedBarriersRemaining() {
        return reinforcedBarriersRemaining;
    }

    public void setReinforcedBarriersRemaining(int reinforcedBarriersRemaining) {
        this.reinforcedBarriersRemaining = reinforcedBarriersRemaining;
    }

    public int getExplosiveBarriersRemaining() {
        return explosiveBarriersRemaining;
    }

    public void setExplosiveBarriersRemaining(int explosiveBarriersRemaining) {
        this.explosiveBarriersRemaining = explosiveBarriersRemaining;
    }

    public int getRewardingBarriersRemaining() {
        return rewardingBarriersRemaining;
    }

    public void setRewardingBarriersRemaining(int rewardingBarriersRemaining) {
        this.rewardingBarriersRemaining = rewardingBarriersRemaining;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<String> getSpellsAcquired() {
        return spellsAcquired;
    }

    public void setSpellsAcquired(List<String> spellsAcquired) {
        this.spellsAcquired = spellsAcquired;
    }


    // ... Other methods
}