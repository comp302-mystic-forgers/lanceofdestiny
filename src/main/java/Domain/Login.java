package Domain;

import com.mongodb.MongoConfigurationException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Domain.BuildingModePage.BUILDING_MODE_MENU;


/**
 * The Login class provides a GUI for users to log in or sign up to the game.
 * It contains a username field, a password field, a login button, and a sign-up button.
 * The class implements the ActionListener interface to handle button clicks.
 *
 * REQUIRES:
 * - A BuildingModeController object to handle the game's building mode.
 * - A PlayerAccountDAO object to handle database operations for player accounts.
 * - Subsequently, also a (MongoDB) Database is needed, for the PlayerAccountDAO to connect to it.
 * - A Us
 * */
public class Login extends JFrame implements ActionListener {
    private JTextField userNameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JPanel newPanel;
    private JButton signupButton;
    private BuildingModeController buildingModeController;
    private PlayerAccountDAO playerAccountDAO;

    /**
     * Constructor
     * Constructs a new Login object with the given BuildingModeController and adds the buttons.
     *
     * @param buildingModeController The BuildingModeController to handle the game's building mode.
     */
    public Login(BuildingModeController buildingModeController) {
        this.buildingModeController = buildingModeController;
        Database connection = new Database();
        playerAccountDAO = new PlayerAccountDAO(connection);
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 100);
        setLayout(new GridLayout(3, 2));
        JLabel userNameLabel = new JLabel("Username: ");
        add(userNameLabel);
        userNameField = new JTextField();
        add(userNameField);

        JLabel passwordLabel = new JLabel("Password: ");
        add(passwordLabel);
        passwordField = new JPasswordField();
        add(passwordField);

        signupButton = new JButton("Sign Up: ");
        signupButton.addActionListener(this);
        add(signupButton);

        loginButton = new JButton("Login: ");
        loginButton.addActionListener(this);
        add(loginButton);
        setLocationRelativeTo(null);
    }
    /**
     * Handles button clicks for the login and sign-up buttons.
     *
     * @param e The ActionEvent that triggered the method.
     *
     */
    public void actionPerformed(ActionEvent e) {
        // REQUIRES: Player to click either the loginButton or the signUpButton
        // MODIFIES: The Database to add player accounts. The UserSession Singleton is assigned the PlayerAccount
        // EFFECTS: When the login button is clicked, the user's username and password are validated.
        // *   If the validation is successful, the user is logged in and the building mode menu is displayed.
        // *   If the validation is unsuccessful, an error message is displayed.
        // *   When the sign-up button is clicked, the user's username and password are validated.
        // *   If the validation is successful, a new player account is created and saved to the database.
        // *   If the validation is unsuccessful, an error message is displayed.
        if (e.getSource() == loginButton) {
            String username = userNameField.getText();
            String password = new String(passwordField.getPassword());
            if (isValidLogin(username, password)) {
                PlayerAccount playerAccount = playerAccountDAO.findPlayerAccountByUsername(username);
                if (playerAccount != null && playerAccount.getPassword().equals(password)) {
                    JOptionPane.showMessageDialog(this, "Login is successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                    UserSession.getInstance().setCurrentPlayer(playerAccount);
                    buildingModeController.setCurrentMode(BUILDING_MODE_MENU);
                    buildingModeController.switchScreens();
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == signupButton){
            String username = userNameField.getText();
            String password = new String(passwordField.getPassword());
            if (isValidLogin(username, password)) {
                PlayerAccount playerAccount = new PlayerAccount(username, password);
                playerAccountDAO.savePlayerAccount(playerAccount);
                JOptionPane.showMessageDialog(this, "Sign up is successful. Please log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
                userNameField.setText("");
                passwordField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Username or password too short/ of same length. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Validates the user's sign-up credentials.
     *
     * @param username The user's username.
     * @param password The user's password.
     * @return True if the sign-up credentials are meet the requirements, false otherwise.
     */
    private boolean isValidLogin(String username, String password) {
        return username.length() > 5 && password.length() > 6 && !username.equals(password);
    }

    public JTextField getUserNameField() {
        return userNameField;
    }

    public void setUserNameField(JTextField userNameField) {
        this.userNameField = userNameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(JPasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public void setLoginButton(JButton loginButton) {
        this.loginButton = loginButton;
    }

    public JButton getSignupButton() {
        return signupButton;
    }

    public PlayerAccountDAO getPlayerAccountDAO() {
        return playerAccountDAO;
    }

    /**
     public static void main(String[] args) {
     SwingUtilities.invokeLater(() -> {
     BuildingModeController buildingModeController = new BuildingModeController();
     Login login = new Login(buildingModeController);
     });
     }**/

}
