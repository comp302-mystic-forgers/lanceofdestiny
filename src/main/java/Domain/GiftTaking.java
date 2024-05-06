package src.main.java.Domain;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GiftTaking extends JFrame {
    private BufferedImage background;
    public GiftTaking() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/Assets/Images/giftBackground.jpg");
            if (inputStream == null) {
                throw new IOException("Image not found.");
            }
            background = ImageIO.read(inputStream);
        } catch (IOException e) {
            System.err.println("Error loading background image: " + e.getMessage());
        }

        setTitle("Lance of Destiny");
        setSize(800, 600);
        setResizable(false);
        setLayout(new BorderLayout());

        // Create a custom JPanel to paint the background image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Lance of Destiny", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 48));
        titleLabel.setPreferredSize(new Dimension(800, 100));
        titleLabel.setForeground(Color.WHITE);
        backgroundPanel.add(titleLabel, BorderLayout.NORTH);

        JLabel giftLabel = new JLabel("You received a gift!", SwingConstants.CENTER);
        giftLabel.setFont(new Font("Serif", Font.BOLD, 24));
        giftLabel.setPreferredSize(new Dimension(800, 50));
        giftLabel.setForeground(Color.WHITE);
        backgroundPanel.add(giftLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);

        JButton takegift = new JButton("Use gift now");
        JButton collectgift = new JButton("Collect gift");

        buttonPanel.add(takegift);
        buttonPanel.add(collectgift);

        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(backgroundPanel);
    }
}
