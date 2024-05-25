package Domain;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerAccount {

    private String playerId;
    private String username;
    private String password;
    private List<GameInfo> games;
    private int chances = 3;

    public PlayerAccount(String username, String password) {
        this.username = username;
        this.password = password;
        this.games = new ArrayList<>();
    }
    public PlayerAccount(){
    }
    public PlayerAccount(String playerId, String username, String password, List<GameInfo> games) {
        this.playerId = playerId;
        this.username = username;
        this.password = password;
        this.games = games;
    }

    public void increaseChances(){
        this.chances++;
    }
    public void decreaseChances(){
        this.chances--;
    }

    // ... Getters and setters for the attributes

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<GameInfo> getGames() {
        return games;
    }

    public void setGames(List<GameInfo> games) {
        this.games = games;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public int getChances() {
        return chances;
    }

    public void setChances(int chances) {
        this.chances = chances;
    }
}