package Domain;

// SimpleBarrier.java
import java.awt.Color;
import java.awt.Graphics;

public class SimpleBarrier extends Barrier {
    private boolean destroyed;

    public SimpleBarrier(int x, int y, int width, int height) {
        super(x, y, width, height);
        destroyed = false;
    }

    @Override
    public void draw(Graphics g) {
        if (!destroyed) {
            g.setColor(Color.BLACK);
            g.fillRect(x, y, width, height);
        }
    }

    // Method to handle collision with FireBall
    public boolean collidesWithFireBall(FireBall fireBall) {
        return !destroyed && fireBall.getX() + fireBall.getDiameter() >= x &&
                fireBall.getX() <= x + width && fireBall.getY() + fireBall.getDiameter() >= y &&
                fireBall.getY() <= y + height;
    }

    // Method to handle destruction by FireBall
    public void destroy() {
        destroyed = true;
    }

    // Method to handle collision response for FireBall
    public void handleCollisionResponse(FireBall fireBall) {
        // Reverse FireBall's direction
        fireBall.reverseYDirection();
    }

    // Add more methods as needed
}

