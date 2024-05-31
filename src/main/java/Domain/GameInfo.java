package Domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class GameInfo {
    private String playerId;
    private static int score;
    private static int  lives;
    private GameState gameState;
    private Date lastSaved;
    private List<Spell> spellsAcquired;
    private List<SimpleBarrier> simpleBarrierList;
    private List<ReinforcedBarrier> reinforcedBarrierList;
    private List<RewardingBarrier> rewardingBarrierList;

    private List<ExplosiveBarrier> explosiveBarrierList;

    public  GameInfo(){
        this.playerId = UserSession.getInstance().getCurrentPlayer().getPlayerId();
        this.gameState = GameState.ACTIVE;
        this.lives = 3;
        this.score = 0;
        this.spellsAcquired = new ArrayList<>();
        this.simpleBarrierList = new ArrayList<>();
        this.reinforcedBarrierList = new ArrayList<>();
        this.rewardingBarrierList = new ArrayList<>();
        this.explosiveBarrierList = new ArrayList<>();
    };

    public GameInfo(String playerId, int score, int lives, GameState gameState, Date lastSaved, List<Spell> spellsAcquired, List<SimpleBarrier> simpleBarrierList, List<ReinforcedBarrier> reinforcedBarrierList, List<RewardingBarrier> rewardingBarrierList,List<ExplosiveBarrier> explosiveBarrierList) {
        this.playerId = playerId;
        this.score = score;
        this.lives = lives;
        this.gameState = gameState;
        this.lastSaved = lastSaved;
        this.spellsAcquired = spellsAcquired;
        this.simpleBarrierList = simpleBarrierList;
        this.reinforcedBarrierList = reinforcedBarrierList;
        this.rewardingBarrierList = rewardingBarrierList;
        this.explosiveBarrierList = explosiveBarrierList;
    }

    public static int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public static int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Date getLastSaved() {
        return lastSaved;
    }

    public void setLastSaved(Date lastSaved) {
        this.lastSaved = lastSaved;
    }

    public List<Spell> getSpellsAcquired() {
        return spellsAcquired;
    }

    public void setSpellsAcquired(List<Spell> spellsAcquired) {
        this.spellsAcquired = spellsAcquired;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public List<SimpleBarrier> getSimpleBarrierList() {
        return simpleBarrierList;
    }

    public void setSimpleBarrierList(List<SimpleBarrier> simpleBarrierList) {
        this.simpleBarrierList = simpleBarrierList;
    }

    public List<ReinforcedBarrier> getReinforcedBarrierList() {
        return reinforcedBarrierList;
    }

    public void setReinforcedBarrierList(List<ReinforcedBarrier> reinforcedBarrierList) {
        this.reinforcedBarrierList = reinforcedBarrierList;
    }

    public List<RewardingBarrier> getRewardingBarrierList() {
        return rewardingBarrierList;
    }

    public void setRewardingBarrierList(List<RewardingBarrier> rewardingBarrierList) {
        this.rewardingBarrierList = rewardingBarrierList;
    }

    public List<ExplosiveBarrier> getExplosiveBarrierList() {
        return explosiveBarrierList;
    }

    public void setExplosiveBarrierList(List<ExplosiveBarrier> explosiveBarrierList) {
        this.explosiveBarrierList = explosiveBarrierList;
    }
}