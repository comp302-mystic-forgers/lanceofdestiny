package Domain;

import javax.swing.*;
import java.awt.*;

public class ExplosiveBarrier extends Barrier{
    private boolean destroyed;
    private ImageIcon icon;
    private int xSpeed; //DO WE NEED IT TO MOVE IT HORIZONTALY?
    private int ySpeed;

    public ExplosiveBarrier(int x, int y, int width, int height) {
        super(x, y, width, height);
        destroyed = false;
        this.icon = new ImageIcon("Assets/Images/200Redgem.png");
        this.xSpeed = 0;
        this.ySpeed = 0;
    }

    @Override
    public void draw(Graphics g) {
        if (!destroyed) {
            g.drawImage(icon.getImage(), x, y, width, height, null);
        } else {
            g.drawImage(icon.getImage(), x, y, width, height, null);
            y += ySpeed;
            x += xSpeed;
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
        xSpeed = (int) (Math.random() * 5) - 2; // Random horizontal speed
        ySpeed = 5; // Vertical speed towards the Magical Staff
    }

    // Method to handle collision response for FireBall
    public void handleCollisionResponse(FireBall fireBall) {
        // Reverse FireBall's direction
        fireBall.reverseYDirection();
        destroy();
    }
}
