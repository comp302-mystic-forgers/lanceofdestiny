package src.main.java.Domain;

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

public class BuildingModeMenu extends JFrame {

    private BuildingModeController buildingModeController;

    private AudioInputStream audioInputStream;

    private Clip clip;

    // Path to your background image
    private String backgroundImagePath = "Assets/Images/BuildingModeStartBackground.png"; // Update this path

    public BuildingModeMenu(BuildingModeController buildingModeController) {
        this.buildingModeController = buildingModeController;
        setTitle("Lance of Destiny - Building Mode Menu");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        // Load and set the background image
        setContentPane(new BackgroundPanel(backgroundImagePath));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false); // Make panel transparent to show the background image

        JButton loadGameButton = new JButton("Load Game");
        loadGameButton.addActionListener(e -> System.out.println("Load game action"));


        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buildingModeController.setCurrentMode("assembly_menu");
                buildingModeController.switchScreens();
            }
        });

        buttonPanel.add(loadGameButton);
        buttonPanel.add(newGameButton);

        add(buttonPanel, BorderLayout.SOUTH);

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

    public Clip getClip() {
        return clip;
    }

    /**
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BuildingModeController buildingModeController = new BuildingModeController();
            BuildingModeMenu buildingModeMenu = new BuildingModeMenu(buildingModeController);
        });
    }
     **/

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
}