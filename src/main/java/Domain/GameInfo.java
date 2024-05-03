package Domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class GameInfo {
    private UUID gameId;
    private PlayerAccount player;
    private int score;
    private int lives;
    private GameState gameState;
    private Date lastSaved;
    private List<Spell> spellsAcquired;
    private List<Barrier> barriersRemaining;

    public  GameInfo(){
        this.gameId = UUID.randomUUID();
        this.player = new PlayerAccount();
        this.gameState = GameState.ACTIVE;
        this.lives = 3;
        this.score = 0;
        this.spellsAcquired = new ArrayList<>();
        this.barriersRemaining = new ArrayList<>();
    };

    public UUID getGameId() {
        return gameId;
    }

    public PlayerAccount getPlayer() {
        return player;
    }

    public void setPlayer(PlayerAccount playerId) {
        this.player = player;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLives() {
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

    public void setSpellsAcquired(List<Spell> pellsAcquired) {
        this.spellsAcquired = spellsAcquired;
    }

    public List<Barrier> getBarriersRemaining() {
        return barriersRemaining;
    }

    public void setBarriersRemaining(List<Barrier> barriersRemaining) {
        this.barriersRemaining = barriersRemaining;
    }

}