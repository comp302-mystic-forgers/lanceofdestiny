package DomainTest;

import Domain.GameController;
import Domain.Login;
import Domain.UserSession;
import com.mongodb.MongoConfigurationException;
import org.junit.jupiter.api.Test;
import javax.swing.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.junit.jupiter.api.Assertions.*;

class LoginTest extends JFrame {

    // BB-Tests: Create PlayerAccounts with (in-)correct username and password
    // GB-Tests: Starting game with (non-)existing PlayerAccount credentials, possibility to move on to play game

    // 1st BlackBox-Test: Check if signing up worked with the right input, by searching for PlayerAccount in Database
    @Test
    void testValidSignUp() {
        // Initializing GameController in order to construct a Login object
        GameController gameController = new GameController();
        Login login = gameController.getBuildingModeController().getLogin();
        // Try to sign up with valid username and password (length)
        login.getUserNameField().setText("testuser");
        login.getPasswordField().setText("testpassword");
        login.actionPerformed(new ActionEvent(login.getSignupButton(), ActionEvent.ACTION_PERFORMED, "Sign Up"));
        // Check if PlayerAccount can be found in Database
        assertNotNull(login.getPlayerAccountDAO().findPlayerAccountByUsername("testuser"));
        if(login.getPlayerAccountDAO().findPlayerAccountByUsername("testuser") != null){
            System.out.println("User found. Test successful!");
        }
    }


    // 2nd BlackBox-Test: Check if signing up worked with the wrong input (case: values too short), by searching for UserAccount in Database
    @Test
    void testInvalidShortSignUp() {
        // Initializing GameController in order to construct a Login object
        GameController gameController = new GameController();
        Login login = gameController.getBuildingModeController().getLogin();
        // Try signing up with invalid username and password
        login.getUserNameField().setText("te");
        login.getPasswordField().setText("tes");
        login.actionPerformed(new ActionEvent(login.getSignupButton(), ActionEvent.ACTION_PERFORMED, "Sign Up"));
        // Check if PlayerAccount can be found in Database, should not be in Database
        assertTrue(login.getPlayerAccountDAO().findPlayerAccountByUsername("te") == null);
        if(login.getPlayerAccountDAO().findPlayerAccountByUsername("te") == null){
            System.out.println("PlayerAccount has not been signed up. Test successful!");
        }
    }

    // 2nd BlackBox-Test: Check if signing up worked with the wrong input (case: same values), by searching for UserAccount in Database
    @Test
    void testInvalidSameSignUp() {
        // Initializing GameController in order to construct a Login object
        GameController gameController = new GameController();
        Login login = gameController.getBuildingModeController().getLogin();
        // Try signing up with invalid username and password
        login.getUserNameField().setText("samelength");
        login.getPasswordField().setText("samelength");
        login.actionPerformed(new ActionEvent(login.getSignupButton(), ActionEvent.ACTION_PERFORMED, "Sign Up"));
        // Check if PlayerAccount can be found in Database, should not be in Database
        assertTrue(login.getPlayerAccountDAO().findPlayerAccountByUsername("samelength") == null);
        if(login.getPlayerAccountDAO().findPlayerAccountByUsername("samelength") == null){
            System.out.println("PlayerAccount has not been signed up. Test successful!");
        }
    }


    // 1st GlassBox-Test: Check if logging in worked with login data for existing PlayerAccount and if we can continue to play the game
    @Test
    void testValidLogin() {
        // Initializing GameController in order to construct a Login object
        GameController gameController = new GameController();
        Login login = gameController.getBuildingModeController().getLogin();
        // Try to log In with valid username and password
        login.getUserNameField().setText("testuser");
        login.getPasswordField().setText("testpassword");
        login.actionPerformed(new ActionEvent(login.getLoginButton(), ActionEvent.ACTION_PERFORMED, "Login"));
        // Check if UserSession is not null and has the right PlayerAccount set
        assertNotNull(UserSession.getInstance().getCurrentPlayer());
        assertEquals(UserSession.getInstance().getCurrentPlayer().getUsername(), "testuser");
        assertEquals(gameController.getBuildingModeController().getCurrentMode(), "assembly_menu");
        if (UserSession.getInstance().getCurrentPlayer() != null){
            System.out.println("UserSession has been set, we can continue to play the game. Test successful!");
        }
    }

    // 2nd GlassBox-Test: Check if logging in worked with non-existing, incorrect login data, should not work to play the game
    @Test
    void testInvalidLogin() {
        // Initializing GameController in order to construct a Login object
        GameController gameController = new GameController();
        Login login = gameController.getBuildingModeController().getLogin();
        // Try logging in with non-existing username and password
        login.getUserNameField().setText("test");
        login.getPasswordField().setText("bad");
        login.actionPerformed(new ActionEvent(login.getLoginButton(), ActionEvent.ACTION_PERFORMED, "Login"));
        // Check if UserSession is null
        assertTrue(UserSession.getInstance().getCurrentPlayer() == null);
        if(UserSession.getInstance().getCurrentPlayer() == null){
            System.out.println("UserSession has not been set. Test successful!");
        }
    }
}