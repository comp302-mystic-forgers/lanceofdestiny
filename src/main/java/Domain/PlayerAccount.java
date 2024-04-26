package Domain;
import java.util.ArrayList;
import java.util.List;

public class PlayerAccount {
     private String username;
     private String password;
     private List<GameInfo> games;

     public PlayerAccount(String username, String password) {
     this.username = username;
     this.password = password;
     this.games = new ArrayList<>();
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

     // ... Other methods


}
