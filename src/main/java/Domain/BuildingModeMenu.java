package Domain;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static Domain.BuildingModePage.*;

public class BuildingModeMenu extends JFrame {

    private BuildingModeController buildingModeController;

    private AudioInputStream audioInputStream;

    private Clip clip;

    // Path to your background image
    private String backgroundImagePath = "Assets/Images/BuildingModeStartBackground.png";

    private JPanel buttonPanel;

    private JPanel secondButtonPanel;

    protected boolean isSingle = true;

    public BuildingModeMenu(BuildingModeController buildingModeController) {
        this.buildingModeController = buildingModeController;
        setTitle("Lance of Destiny - Building Mode Menu");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        // Load and set the background image
        setContentPane(new BackgroundPanel(backgroundImagePath));

        JLabel titleLabel = new JLabel("Get ready!", SwingConstants.CENTER);
        titleLabel.setForeground(Color.white);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 48));
        titleLabel.setPreferredSize(new Dimension(800, 100));
        add(titleLabel, BorderLayout.NORTH);

        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false); // Make panel transparent to show the background image

        secondButtonPanel = new JPanel();
        secondButtonPanel.setLayout(new BoxLayout(secondButtonPanel, BoxLayout.Y_AXIS));
        secondButtonPanel.setOpaque(false);

        addButtonsToPanel();

        add(buttonPanel, BorderLayout.CENTER);
        add(secondButtonPanel, BorderLayout.WEST);

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

    private void addButtonsToPanel() {
        buttonPanel.removeAll();

        JButton loadGameButton = new JButton("Load Game");
        loadGameButton.addActionListener(e -> {
            buildingModeController.setCurrentMode(LOAD_GAME);
            buildingModeController.switchScreens();
        });

        JButton newGameButton = new JButton("New Game");
        JPopupMenu newGameMenu = new JPopupMenu();
        JMenuItem singlePlayerItem = new JMenuItem("Single Player Game");
        JMenuItem dualPlayerItem = new JMenuItem("Dual Player Game");

        singlePlayerItem.addActionListener(e -> {
            buildingModeController.setCurrentMode(ASSEMBLY_MENU);
            buildingModeController.setNewGame(true);
            buildingModeController.switchScreens();
        });

        dualPlayerItem.addActionListener(e -> showDualPlayerOptions());

        newGameMenu.add(singlePlayerItem);
        newGameMenu.add(dualPlayerItem);

        newGameButton.addActionListener(e -> newGameMenu.show(newGameButton, 0, newGameButton.getHeight()));

        buttonPanel.add(loadGameButton);
        buttonPanel.add(newGameButton);

        revalidate();
        repaint();
    }

    private void showDualPlayerOptions() {
        buttonPanel.removeAll();

        JButton hostButton = new JButton("Host a game");
        JButton joinButton = new JButton("Join the game");
        JButton returnButton = new JButton("Return to Menu");

        hostButton.addActionListener(e -> {
            isSingle = false;
            buildingModeController.setCurrentMode(ASSEMBLY_MENU);
            buildingModeController.switchScreens();
        });

        joinButton.addActionListener(e -> {
            isSingle = false;
            buildingModeController.setCurrentMode(MULTIJOIN);
            buildingModeController.switchScreens();
        });

        returnButton.addActionListener(e -> addButtonsToPanel());

        buttonPanel.add(hostButton);
        buttonPanel.add(joinButton);
        buttonPanel.add(returnButton);

        revalidate();
        repaint();
    }

    public Clip getClip() {
        return clip;
    }

    public boolean getIsSingle() {
        return isSingle;
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
