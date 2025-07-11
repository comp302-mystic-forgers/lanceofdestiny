package Domain;
import javax.swing.*;
import java.awt.Graphics;

public class SimpleBarrier extends Barrier {
    public ImageIcon icon;
    public ImageIcon frozenIcon;

    // Can move horizontally, if it has a free space around it in the x-axis.
    // Might be moving back and forth with a probability of 0.2, or stiff with
    // a probability of 0.8. It will of course move in its free space, meaning
    // that if it is about to collide with another barrier it will reverse its direction.
    public SimpleBarrier(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.icon = new ImageIcon("Assets/Images/200Bluegem.png");
        this.frozenIcon = new ImageIcon("Assets/Images/FrozenSimple.png");
        moveable();
        moveHorizontally();
    }

    @Override
    public void draw(Graphics g) {
        if (!destroyed) {
            if (isFrozen) {
                g.drawImage(frozenIcon.getImage(), x, y, width, height, null);
            } else {
                g.drawImage(icon.getImage(), x, y, width, height, null);
            }
        }
    }

    public boolean collidesWithFireBall(FireBall fireBall) {
        return !destroyed && fireBall.getX() + fireBall.getDiameter() >= x &&
                fireBall.getX() <= x + width && fireBall.getY() + fireBall.getDiameter() >= y &&
                fireBall.getY() <= y + height;
    }

    public void handleCollisionResponse(FireBall fireBall) {
        fireBall.reverseYDirection();
    }


}

