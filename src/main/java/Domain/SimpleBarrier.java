package Domain;
import javax.swing.*;
import java.awt.*;

public class SimpleBarrier extends Barrier {
    private ImageIcon icon;

    private int xSpeed;
    private int ySpeed;

    public SimpleBarrier(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.icon = new ImageIcon("Assets/Images/200Bluegem.png");
    }

    @Override
    public void draw(Graphics g) {
        if (!destroyed) {
            g.drawImage(icon.getImage(), x, y, width, height, null);
            if (isFrozen()) {
                g.drawImage(icon.getImage(), x, y, width, height, null);
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.BOLD, 15));
                g.drawString("FROZEN", x + width / 4, y + height / 2);
            }
        }
    }

    public boolean collidesWithFireBall(FireBall fireBall) {
        return !destroyed && !isFrozen() && fireBall.getX() + fireBall.getDiameter() >= x &&
                fireBall.getX() <= x + width && fireBall.getY() + fireBall.getDiameter() >= y &&
                fireBall.getY() <= y + height;
    }

    public void handleCollisionResponse(FireBall fireBall) {
        fireBall.reverseYDirection();
    }


    public int getxSpeed() {
        return xSpeed;
    }

    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public int getySpeed() {
        return ySpeed;
    }

    public void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }
}

