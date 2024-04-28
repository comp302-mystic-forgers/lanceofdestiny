package Domain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame implements ActionListener {
    private JTextField userNameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JPanel newPanel;
    private JButton signupButton;
    private BuildingModeController buildingModeController;
    private PlayerAccountDAO playerAccountDAO;

    public Login(BuildingModeController buildingModeController) {
        this.buildingModeController = buildingModeController;
        Database connection = new Database();
        playerAccountDAO = new PlayerAccountDAO(connection);
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 100);
        setLayout(new GridLayout(3, 2));
        JLabel userNameLabel = new JLabel("username: ");
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
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = userNameField.getText();
            String password = new String(passwordField.getPassword());
            if (isValidLogin(username, password)) {
                PlayerAccount playerAccount = playerAccountDAO.findPlayerAccountByUsername(username);
                if (playerAccount != null && playerAccount.getPassword().equals(password)) {
                    JOptionPane.showMessageDialog(this, "Login is successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                    buildingModeController.setCurrentMode("building_mode_menu");
                    buildingModeController.switchScreens();
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
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
                JOptionPane.showMessageDialog(this, "Username or password too short. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean isValidLogin(String username, String password) {
        return username.length() > 5 && password.length() > 6;
    }

   /**
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BuildingModeController buildingModeController = new BuildingModeController();
            Login login = new Login(buildingModeController);
        });
    }**/

}
