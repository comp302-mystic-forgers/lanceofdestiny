package Domain;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class BuildingModeMenu extends JFrame {

    // Path to your background image
    private String backgroundImagePath = "Assets/Images/BuidlingModeStartBackground.png"; // Update this path

    public BuildingModeMenu() {
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
        newGameButton.addActionListener(e -> System.out.println("New game action"));

        buttonPanel.add(loadGameButton);
        buttonPanel.add(newGameButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BuildingModeMenu::new);
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
}