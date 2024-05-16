package DomainTest;

import Domain.BuildingModeController;
import Domain.GameController;
import Domain.Login;
import Domain.UserSession;
import org.junit.jupiter.api.Test;

import java.awt.event.ActionEvent;

import static org.junit.jupiter.api.Assertions.*;

class LoginTest {

    @Test
    void testValidLogin() {
        GameController gameController = new GameController();
        BuildingModeController buildingModeController = new BuildingModeController(gameController);
        Login login = new Login(buildingModeController);
        login.getUserNameField().setText("testuser");
        login.getPasswordField().setText("testpassword");
        login.actionPerformed(new ActionEvent(login.getLoginButton(), ActionEvent.ACTION_PERFORMED, "Login"));
        assertTrue(UserSession.getInstance().getCurrentPlayer() != null);
        assertEquals(UserSession.getInstance().getCurrentPlayer().getUsername(), "testuser");
    }

    @Test
    void testInvalidLogin() {
        GameController gameController = new GameController();
        BuildingModeController buildingModeController = new BuildingModeController(gameController);
        Login login = new Login(buildingModeController);
        login.getUserNameField().setText("test");
        login.getPasswordField().setText("test");
        login.actionPerformed(new ActionEvent(login.getLoginButton(), ActionEvent.ACTION_PERFORMED, "Login"));
        assertTrue(UserSession.getInstance().getCurrentPlayer() == null);
    }

    @Test
    void testValidSignUp() {
        GameController gameController = new GameController();
        BuildingModeController buildingModeController = new BuildingModeController(gameController);
        Login login = new Login(buildingModeController);
        login.getUserNameField().setText("testuser");
        login.getPasswordField().setText("testpassword");
        login.actionPerformed(new ActionEvent(login.getSignupButton(), ActionEvent.ACTION_PERFORMED, "Sign Up"));
        assertTrue(login.getPlayerAccountDAO().findPlayerAccountByUsername("testuser") != null);
    }

    @Test
    void testInvalidSignUp() {
        GameController gameController = new GameController();
        BuildingModeController buildingModeController = new BuildingModeController(gameController);
        Login login = new Login(buildingModeController);
        login.getUserNameField().setText("te");
        login.getPasswordField().setText("tes");
        login.actionPerformed(new ActionEvent(login.getSignupButton(), ActionEvent.ACTION_PERFORMED, "Sign Up"));
        assertTrue(login.getPlayerAccountDAO().findPlayerAccountByUsername("te") == null);
    }

    @Test
    void testLoginButtonAction() {
        GameController gameController = new GameController();
        BuildingModeController buildingModeController = new BuildingModeController(gameController);
        Login login = new Login(buildingModeController);
        login.getLoginButton().doClick();
        assertTrue(login.getUserNameField().getText().isEmpty());
        assertTrue(login.getPasswordField().getPassword().length == 0);
    }

    @Test
    void testSignUpButtonAction() {
        GameController gameController = new GameController();
        BuildingModeController buildingModeController = new BuildingModeController(gameController);
        Login login = new Login(buildingModeController);
        login.getSignupButton().doClick();
        assertTrue(login.getUserNameField().getText().isEmpty());
        assertTrue(login.getPasswordField().getPassword().length == 0);
    }
}