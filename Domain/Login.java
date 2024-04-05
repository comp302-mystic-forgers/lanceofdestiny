package Domain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame implements ActionListener {
    private JTextField userNameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JPanel newPanel;





    public Login(){
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300,100);
        setLayout(new GridLayout(3, 2));
        JLabel userNameLabel = new JLabel("username: ");
        add(userNameLabel);
        userNameField = new JTextField();
        add(userNameField);


        JLabel passwordLabel = new JLabel("Password: ");
        add(passwordLabel);
        passwordField = new JPasswordField();
        add(passwordField);

        loginButton = new JButton("Login: ");
        loginButton.addActionListener(this);
        add(loginButton);


        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton){
            String username = userNameField.getText();
            String password = new String(passwordField.getPassword());
            if (isValidLogin(username, password)){
                JOptionPane.showMessageDialog(this, "Login is successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                BuildingModeMenu buildingModeMenu = new BuildingModeMenu();
                buildingModeMenu.setVisible(true);
                this.dispose();
            }
            else {
                JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);

            }


        }

    }
    private boolean isValidLogin(String username, String password) {
        return username.length() > 5 && password.length() > 12;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login::new);
    }
    // the code has some problems for now but I will fix
}