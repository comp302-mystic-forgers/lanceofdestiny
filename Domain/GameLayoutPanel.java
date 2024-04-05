package Domain;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class GameLayoutPanel extends JPanel {
    private BufferedImage backgroundImage;
    private BufferedImage simpleBarrierImage;
    private BufferedImage firmBarrierImage;
    private BufferedImage explosiveBarrierImage;
    private BufferedImage giftBarrierImage;


    public GameLayoutPanel() {
        setOpaque(false);
        try {
            backgroundImage = ImageIO.read(new File("Assets/Images/200Background.png"));
            simpleBarrierImage = ImageIO.read(new File("Assets/Images/200Bluegem.png"));
            firmBarrierImage = ImageIO.read(new File("Assets/Images/200Firm.png"));
            explosiveBarrierImage = ImageIO.read(new File("Assets/Images/200Redgem.png"));
            giftBarrierImage = ImageIO.read(new File("Assets/Images/200Greengem.png"));
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
        // Draw the barriers based on their count and positions
/*        for (int i = 0; i < 1; i++) {
            int x = (i % 10) * (simpleBarrierImage.getWidth() + 5); // Calculate the x position
            int y = (i / 10) * (simpleBarrierImage.getHeight() + 5); // Calculate the y position
            g.drawImage(simpleBarrierImage, x, y, this);
        }*/
    }
}