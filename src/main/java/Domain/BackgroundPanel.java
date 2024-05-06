package src.main.java.Domain;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class BackgroundPanel extends JPanel {
    private BufferedImage backgroundImage;

    public BackgroundPanel() {
        setLayout(new BorderLayout());
        try {
            backgroundImage = ImageIO.read(new File("Assets/Images/200Background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
