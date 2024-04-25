package Domain;
import javax.swing.*;
import java.awt.Graphics;

public class SimpleBarrier extends Barrier {
    private boolean destroyed;
    private ImageIcon icon;

    public SimpleBarrier(int x, int y, int width, int height) {
        super(x, y, width, height);
        destroyed = false;
        this.icon = new ImageIcon("Assets/Images/200Bluegem.png");
    }

    @Override
    public void draw(Graphics g) {
        if (!destroyed) {
            g.drawImage(icon.getImage(), x, y, width, height, null);
        }
    }

    public boolean collidesWithFireBall(FireBall fireBall) {
        return !destroyed && fireBall.getX() + fireBall.getDiameter() >= x &&
                fireBall.getX() <= x + width && fireBall.getY() + fireBall.getDiameter() >= y &&
                fireBall.getY() <= y + height;
    }

    public void destroy() {
        destroyed = true;
    }
    public void handleCollisionResponse(FireBall fireBall) {
        fireBall.reverseYDirection();
    }
}

