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

import static Domain.BuildingModePage.*;

public class BuildingModeMenu extends JFrame {

    private BuildingModeController buildingModeController;

    private AudioInputStream audioInputStream;

    private Clip clip;

    // Path to your background image
    private String backgroundImagePath = "Assets/Images/BuildingModeStartBackground.png";

    private boolean multiButtonsAdded = false;

    private JPanel multiButtonsPanel;

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

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false); // Make panel transparent to show the background image

        JPanel secondButtonPanel = new JPanel();
        secondButtonPanel.setLayout(new BoxLayout(secondButtonPanel, BoxLayout.Y_AXIS));
        secondButtonPanel.setOpaque(false);

        JButton loadGameButton = new JButton("Load Game");
        //loadGameButton.addActionListener(e -> System.out.println("Load game action"));
        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buildingModeController.setCurrentMode(LOAD_GAME);
                buildingModeController.switchScreens();
            }
        });

        JButton newSingleButton = new JButton("New Single PLayer Game");
        newSingleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buildingModeController.setCurrentMode(ASSEMBLY_MENU);
                buildingModeController.setNewGame(true);
                buildingModeController.switchScreens();
            }
        });

        JButton newMultiButton = new JButton("New Dual Player Game");
        newMultiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JPanel multiButtonsPanel = new JPanel();;
                if (!multiButtonsAdded) {
                    multiButtonsPanel.setLayout(new FlowLayout());
                    multiButtonsPanel.setOpaque(false);

                    JButton hostButton = new JButton("Host a game");
                    JButton joinButton = new JButton("Join the game");

                    multiButtonsPanel.add(hostButton);
                    multiButtonsPanel.add(joinButton);

                    System.out.println(isSingle);

                    hostButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            isSingle = false;
                            buildingModeController.setCurrentMode(ASSEMBLY_MENU);
                            buildingModeController.switchScreens();

                        }
                    });

                    joinButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            isSingle = false;
                            buildingModeController.setCurrentMode(MULTIJOIN);
                            buildingModeController.switchScreens();

                        }
                    });

                    secondButtonPanel.add(multiButtonsPanel);
                    multiButtonsAdded = true;
                }

                //else {
                  //  secondButtonPanel.remove(multiButtonsPanel);
                  //  multiButtonsAdded = false;
                //}
                revalidate();
                repaint();


            }
        });

        buttonPanel.add(loadGameButton);
        buttonPanel.add(newSingleButton);
        buttonPanel.add(newMultiButton);

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

    public Clip getClip() {
        return clip;
    }

    public boolean getIsSingle() {return isSingle;}

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