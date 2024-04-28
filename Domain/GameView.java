package Domain;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList; // Import ArrayList class

public class GameView extends JPanel implements ComponentListener, ActionListener {
    private MagicalStaff magicalStaff;
    private FireBall fireball;
    private ArrayList<SimpleBarrier> simpleBarriers; // ArrayList to hold SimpleBarrier objects
    private ArrayList<ReinforcedBarrier> reinforcedBarriers;
    private ArrayList<ExplosiveBarrier> explosiveBarriers;
    private ArrayList<RewardingBarrier> rewardingBarriers;
    private Timer timer;
    private boolean gameRunning = true;
    private BufferedImage background;
    private GiftTaking giftWindow;

    public GameView(int panelWidth, int panelHeight) {
        super();
        try {
            InputStream inputStream = getClass().getResourceAsStream("/Assets/Images/200Background.png");
            if (inputStream == null) {
                throw new IOException("Image not found.");
            }
            background = ImageIO.read(inputStream);
        } catch (IOException e) {
            System.err.println("Error loading background image: " + e.getMessage());
        }
        this.magicalStaff = new MagicalStaff(panelWidth, panelHeight - 100); // Position MagicalStaff towards the bottom
        this.fireball = new FireBall(panelWidth / 2, 0); // Start Fireball from the top middle
        this.simpleBarriers = new ArrayList<>(); // Initialize the ArrayList
        this.reinforcedBarriers = new ArrayList<>();
        this.explosiveBarriers = new ArrayList<>();
        this.rewardingBarriers = new ArrayList<>();
        this.giftWindow = new GiftTaking();
        addComponentListener(this);

        // Create multiple SimpleBarrier objects and add them to the ArrayList
        simpleBarriers.add(new SimpleBarrier(100, 200, 50, 20));
        simpleBarriers.add(new SimpleBarrier(300, 150, 50, 20));
        reinforcedBarriers.add(new ReinforcedBarrier(400, 100, 50, 20));
        reinforcedBarriers.add(new ReinforcedBarrier(300, 100, 50, 20));
        explosiveBarriers.add(new ExplosiveBarrier(500, 100, 50, 15));
        explosiveBarriers.add(new ExplosiveBarrier(600, 100, 50, 15));
        rewardingBarriers.add(new RewardingBarrier(700, 100, 50, 20));
        rewardingBarriers.add(new RewardingBarrier(800, 100, 50, 20));

        timer = new Timer(10, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gameRunning) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            magicalStaff.draw(g);
            fireball.draw(g);
            // Draw all SimpleBarrier objects in the ArrayList
            for (SimpleBarrier barrier : simpleBarriers) {
                barrier.draw(g);
            }
            for (ReinforcedBarrier rbarrier : reinforcedBarriers) {
                rbarrier.draw(g);
            }
            for (ExplosiveBarrier ebarrier : explosiveBarriers) {
                ebarrier.draw(g);
            }
            for (RewardingBarrier rwbarrier : rewardingBarriers) {
                rwbarrier.draw(g);
            }
        } else {
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameRunning) {
            fireball.move(getWidth(), getHeight());
            checkCollisions();
            repaint();
        }
    }

    private void checkCollisions() {
        if (fireball.getX() <= 0 || fireball.getX() + fireball.getDiameter() >= getWidth()) {
            fireball.reverseXDirection();
        }

        if (fireball.getY() <= 0) {
            fireball.reverseYDirection();
        }

        if (fireball.collidesWithMagicalStaff(magicalStaff)) {
            fireball.reverseYDirection();
        }

        // Game Over condition: Fireball falls below the Magical Staff
        if (fireball.getY() + fireball.getDiameter() > magicalStaff.getY() + magicalStaff.getHeight()) {
            gameRunning = false;
            timer.stop(); // Stop the game loop
            JOptionPane.showMessageDialog(this, "Game Over", "End", JOptionPane.INFORMATION_MESSAGE);
            // Further actions to reset or end the game can be added here
        }


        for (SimpleBarrier barrier : simpleBarriers) {
            if (barrier.collidesWithFireBall(fireball)) {
                barrier.destroy();
                barrier.handleCollisionResponse(fireball);
            }
        }

        for (ReinforcedBarrier rbarrier : reinforcedBarriers) {
            if (rbarrier.collidesWithFireBall(fireball)) {
                rbarrier.isDestroyed();
                rbarrier.handleCollisionResponse(fireball);
            }
        }

        for (ExplosiveBarrier ebarrier : explosiveBarriers) {
            if (ebarrier.collidesWithFireBall(fireball)) {
                ebarrier.destroy();
                ebarrier.handleCollisionResponse(fireball);
            }
            if(ebarrier.destroyed) {
                if (ebarrier.getY() + ebarrier.getHeight() > magicalStaff.getY() &&
                        ebarrier.getX() + ebarrier.getWidth() > magicalStaff.getX() &&
                        ebarrier.getX() < magicalStaff.getX() + magicalStaff.getWidth()) {
                    gameRunning = false;
                    timer.stop(); // Stop the game loop
                    JOptionPane.showMessageDialog(this, "Game Over", "End", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }

        for (RewardingBarrier rwbarrier : rewardingBarriers) {
            if (rwbarrier.collidesWithFireBall(fireball)) {
                rwbarrier.destroy();
                rwbarrier.handleCollisionResponse(fireball);
            }
            if(rwbarrier.destroyed) {
                if (rwbarrier.getY() + rwbarrier.getHeight() > magicalStaff.getY() &&
                        rwbarrier.getX() + rwbarrier.getWidth() > magicalStaff.getX() &&
                        rwbarrier.getX() < magicalStaff.getX() + magicalStaff.getWidth()){
                    if (!giftWindow.isVisible()) {
                        gameRunning = false;
                        giftWindow.setVisible(true);
                    }
                    gameRunning = true;
                }
            }
        }
    }

    public void moveStaff(int keyCode) {
        if (gameRunning) {
            magicalStaff.move(keyCode, getWidth());
            repaint();
        }
    }

    public void rotateStaff(int keyCode) {
        if (gameRunning) {
            magicalStaff.rotate(keyCode);
            repaint();
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        magicalStaff.updatePosition(getWidth(), getHeight());
        repaint();
    }
    @Override
    public void componentMoved(ComponentEvent e) {}

    @Override
    public void componentShown(ComponentEvent e) {}

    @Override
    public void componentHidden(ComponentEvent e) {}
}
