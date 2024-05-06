package Domain;


public class UserSession {
    private static UserSession instance;
    private PlayerAccount currentPlayer;

    private UserSession() { }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public PlayerAccount getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(PlayerAccount player) {
        this.currentPlayer = player;
    }
}
