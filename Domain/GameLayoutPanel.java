package Domain;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

class GameLayoutPanel extends JPanel {
    private BufferedImage backgroundImage;
    private BufferedImage simpleBarrierImage;
    private BufferedImage firmBarrierImage;
    private BufferedImage explosiveBarrierImage;
    private BufferedImage giftBarrierImage;
    private int simpleBarriersCount;
    private int firmBarriersCount;
    private int explosiveBarriersCount;
    private int giftBarriersCount;
    private ArrayList<Rectangle> placedBarriers;
    private Random rand;
    private double scale;


    public GameLayoutPanel(int simple, int firm, int explosive, int gift, double scale) {
        this.simpleBarriersCount = simple;
        this.firmBarriersCount = firm;
        this.explosiveBarriersCount = explosive;
        this.giftBarriersCount = gift;
        this.scale = scale;
        rand = new Random();
        placedBarriers = new ArrayList<>();
        setOpaque(false);
        try {
            backgroundImage = ImageIO.read(new File("Assets/Images/200Background.png"));
            simpleBarrierImage = scaleImage(ImageIO.read(new File("Assets/Images/200Bluegem.png")), scale);
            firmBarrierImage = scaleImage(ImageIO.read(new File("Assets/Images/200Firm.png")), scale);
            explosiveBarrierImage = scaleImage(ImageIO.read(new File("Assets/Images/200Redgem.png")), scale);
            giftBarrierImage = scaleImage(ImageIO.read(new File("Assets/Images/200Greengem.png")), scale);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private BufferedImage scaleImage(BufferedImage original, double scale) {
        int newWidth = (int) (original.getWidth() * scale);
        int newHeight = (int) (original.getHeight() * scale);
        BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, newWidth, newHeight, null);
        g2.dispose();
        return scaledImage;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            placeBarriersRowByRow(g);


        }
    }

    private void placeBarriersRowByRow(Graphics g) {
        int padding = 5;
        int offsetY = 25;

        offsetY = drawBarriers(g,simpleBarriersCount + firmBarriersCount + explosiveBarriersCount + giftBarriersCount, offsetY, padding);
    }

    private int drawBarriers(Graphics g, int count, int offsetY, int padding) {
        int width = (simpleBarrierImage.getWidth() + firmBarrierImage.getWidth() + explosiveBarrierImage.getWidth() + giftBarrierImage.getWidth()) / 4;
        int height = (simpleBarrierImage.getHeight() + firmBarrierImage.getHeight()+ explosiveBarrierImage.getHeight() + giftBarrierImage.getHeight()) / 4;
        int offsetX = 20;

        for (int i = 0; i < count; i++) {
            if (offsetX + width > getWidth()) {
                offsetY += padding + height;
                offsetX = 20;
            }

            g.drawImage(chooseBarrierImage(), offsetX, offsetY, this);
            placedBarriers.add(new Rectangle(offsetX, offsetY, width, height));
            offsetX += width + padding;
        }

        return offsetY + height + padding;
    }

    private BufferedImage chooseBarrierImage() {
        int total = simpleBarriersCount + firmBarriersCount + explosiveBarriersCount + giftBarriersCount;
        int choice = rand.nextInt(total);
        if (choice < simpleBarriersCount) {
            simpleBarriersCount--;
            return simpleBarrierImage;
        } else if (choice < simpleBarriersCount + firmBarriersCount) {
            firmBarriersCount--;
            return firmBarrierImage;
        } else if (choice < simpleBarriersCount + firmBarriersCount + explosiveBarriersCount) {
            explosiveBarriersCount--;
            return explosiveBarrierImage;
        } else {
            giftBarriersCount--;
            return giftBarrierImage;
        }
    }

    public void redrawBarriers() {
        placedBarriers.clear();
    }

    public void setSimpleBarriersCount(int simpleBarriersCount) {
        this.simpleBarriersCount = simpleBarriersCount;
    }
    public void setFirmBarriersCount(int firmBarriersCount) {
        this.firmBarriersCount = firmBarriersCount;
    }

    public void setExplosiveBarriersCount(int explosiveBarriersCount) {
        this.explosiveBarriersCount = explosiveBarriersCount;
    }

    public void setGiftBarriersCount(int giftBarriersCount) {
        this.giftBarriersCount = giftBarriersCount;
    }
}