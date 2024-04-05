package Domain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame implements ActionListener {
    private JTextField userNameField;
    private JPasswordField passwordField;
    private JButton loginButton;


    public Login(){
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300,100);
        setLayout(new GridLayout(3, 2));
        JLabel userNameLabel = new JLabel("username: ");
        add(userNameLabel);
        JLabel passwordLabel = new JLabel("Password: ");
        add(passwordLabel);
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


        }

    }
    private boolean isValidLogin(String username, String password) {
        return username.length() > 5 || password.length() > 12;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login::new);
    }
    // the code has some problems for now but I will fix
}