package Domain;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.util.ArrayList; // Import ArrayList class

public class GameView extends JPanel implements ComponentListener, ActionListener {
    private MagicalStaff magicalStaff;
    private FireBall fireball;
    private ArrayList<SimpleBarrier> simpleBarriers; // ArrayList to hold SimpleBarrier objects
    private Timer timer;
    private boolean gameRunning = true;

    public GameView(int panelWidth, int panelHeight) {
        super();
        this.magicalStaff = new MagicalStaff(panelWidth, panelHeight - 50); // Position MagicalStaff towards the bottom
        this.fireball = new FireBall(panelWidth / 2, 0); // Start Fireball from the top middle
        this.simpleBarriers = new ArrayList<>(); // Initialize the ArrayList
        addComponentListener(this);

        // Create multiple SimpleBarrier objects and add them to the ArrayList
        simpleBarriers.add(new SimpleBarrier(100, 200, 50, 20));
        simpleBarriers.add(new SimpleBarrier(300, 150, 50, 20));

        timer = new Timer(10, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gameRunning) {
            magicalStaff.draw(g);
            fireball.draw(g);
            // Draw all SimpleBarrier objects in the ArrayList
            for (SimpleBarrier barrier : simpleBarriers) {
                barrier.draw(g);
            }
        } else {
            // Optionally, draw a "Game Over" message directly onto the panel
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

        // Check collision with all SimpleBarrier objects
        for (SimpleBarrier barrier : simpleBarriers) {
            if (barrier.collidesWithFireBall(fireball)) {
                barrier.destroy(); // Destroy the barrier
                barrier.handleCollisionResponse(fireball); // Handle collision response
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
