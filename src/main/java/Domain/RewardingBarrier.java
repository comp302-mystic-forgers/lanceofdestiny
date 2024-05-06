package src.main.java.Domain;

import javax.swing.*;
import java.awt.*;

public class RewardingBarrier extends Barrier{
    private ImageIcon icon;
    private ImageIcon icon2;
    private int xSpeed; //DO WE NEED IT TO MOVE IT HORIZONTALY?
    private int ySpeed;

    public RewardingBarrier(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.icon = new ImageIcon("Assets/Images/200Greengem.png");
        this.icon2 = new ImageIcon("Assets/Images/giftbox.png");
        this.xSpeed = 0;
        this.ySpeed = 0;
    }

    @Override
    public void draw(Graphics g) {
        if (!destroyed) {
            g.drawImage(icon.getImage(), x, y, width, height, null);
        } else {
            g.drawImage(icon2.getImage(), x, y, width, height * 2, null);
            y += ySpeed;
            x += xSpeed;
        }
    }

    public boolean collidesWithMagicalStaff(MagicalStaff magicalStaff) {
        return !destroyed && x < magicalStaff.getX() + magicalStaff.getWidth() && x + width > magicalStaff.getX() &&
                y + height >= magicalStaff.getY() && y < magicalStaff.getY() + magicalStaff.getHeight();
    }

    public boolean collidesWithFireBall(FireBall fireBall) {
        return !destroyed && fireBall.getX() + fireBall.getDiameter() >= x &&
                fireBall.getX() <= x + width && fireBall.getY() + fireBall.getDiameter() >= y &&
                fireBall.getY() <= y + height;
    }

    public void destroy() {
        destroyed = true;
        xSpeed = (int) (Math.random() * 5) - 2;
        ySpeed = 5; // Vertical speed towards the Magical Staff
    }

    public void handleCollisionResponse(FireBall fireBall) {
        fireBall.reverseYDirection();
        destroy();
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getHeight() { return height; }
    public int getWidth() { return width; }
}
