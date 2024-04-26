package Domain;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class WelcomePage extends JFrame {
    private BuildingModeController buildingModeController;

    private String backgroundImagePath = "Assets/Images/BuildingModeStartBackground.png";

    public WelcomePage(BuildingModeController buildingModeController) {
        this.buildingModeController = buildingModeController;

        setTitle("Lance of Destiny");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setResizable(false);
        setLayout(new BorderLayout());

        // Load and set the background image
        setContentPane(new BuildingModeMenu.BackgroundPanel(backgroundImagePath));

        JLabel titleLabel = new JLabel("Welcome to Lance of Destiny!", SwingConstants.CENTER);
        titleLabel.setForeground(Color.white);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 48));
        titleLabel.setPreferredSize(new Dimension(800, 100));
        add(titleLabel, BorderLayout.NORTH);

        JButton loginButton = new JButton("Start");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buildingModeController.setCurrentMode("login");
                buildingModeController.switchScreens();
            }
        });
        add(loginButton, BorderLayout.SOUTH);
    }
    // Inner class to use a background image
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

    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BuildingModeController buildingModeController = new BuildingModeController();
            Domain.WelcomePage welcomePage = new Domain.WelcomePage(buildingModeController);
        });
    }*/
}