package Domain;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import static Domain.BuildingModePage.BUILDING_MODE_MENU;

public class WelcomePage extends JFrame {
    private BuildingModeController buildingModeController;

    private String backgroundImagePath = "Assets/Images/BuildingModeStartBackground.png";

    private AudioInputStream audioInputStream;

    private Clip clip;

    private JPanel buttonPanel;
    private JPanel loginPanel;

    private JTextField userNameField;
    private JPasswordField passwordField;
    private PlayerAccountDAO playerAccountDAO;

    public WelcomePage(BuildingModeController buildingModeController) {
        this.buildingModeController = buildingModeController;
        Database connection = new Database();
        playerAccountDAO = new PlayerAccountDAO(connection);
        setTitle("Lance of Destiny");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setResizable(false);
        setLayout(new BorderLayout());

        // Load and set the background image
        setContentPane(new BackgroundPanel(backgroundImagePath));

        JLabel titleLabel = new JLabel("Welcome to Lance of Destiny!", SwingConstants.CENTER);
        titleLabel.setForeground(Color.white);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 48));
        titleLabel.setPreferredSize(new Dimension(800, 100));
        add(titleLabel, BorderLayout.NORTH);

        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);

        JButton startButton = new JButton("Start");
        JButton exitButton = new JButton("Exit");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLoginFields();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(WelcomePage.this, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        buttonPanel.add(startButton);
        buttonPanel.add(exitButton);

        loginPanel = createLoginPanel();
        loginPanel.setVisible(false);

        add(buttonPanel, BorderLayout.CENTER);
        add(loginPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);

        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File("Assets/Audio/StartMusic.wav"));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.setOpaque(false);

        JLabel userNameLabel = new JLabel("Username: ");
        userNameLabel.setForeground(Color.white);
        panel.add(userNameLabel);

        userNameField = new JTextField();
        panel.add(userNameField);

        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setForeground(Color.white);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton signupButton = new JButton("Sign Up");
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSignUp();
            }
        });
        panel.add(signupButton);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        panel.add(loginButton);

        return panel;
    }

    private void showLoginFields() {
        buttonPanel.setVisible(false);
        loginPanel.setVisible(true);
        revalidate();
        repaint();
    }

    private void handleLogin() {
        String username = userNameField.getText();
        String password = new String(passwordField.getPassword());

        if (isValidLogin(username, password)) {
            PlayerAccount playerAccount = playerAccountDAO.findPlayerAccountByUsername(username);
            if (playerAccount != null && playerAccount.getPassword().equals(password)) {
                //JOptionPane.showMessageDialog(this, "Login is successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                UserSession.getInstance().setCurrentPlayer(playerAccount);
                buildingModeController.setCurrentMode(BUILDING_MODE_MENU);
                buildingModeController.switchScreens();
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleSignUp() {
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

    private boolean isValidLogin(String username, String password) {
        return username.length() > 5 && password.length() > 6 && !username.equals(password);
    }

    public Clip getClip() {
        return clip;
    }

    public void setClip(Clip clip) {
        this.clip = clip;
    }

    static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            try {
                backgroundImage = ImageIO.read(new File(imagePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }
}
