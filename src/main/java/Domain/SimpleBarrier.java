package Domain;
import javax.swing.*;
import java.awt.Graphics;

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

    // Can move horizontally, if it has a free space around it in the x-axis.
    // Might be moving back and forth with a probability of 0.2, or stiff with
    // a probability of 0.8. It will of course move in its free space, meaning
    // that if it is about to collide with another barrier it will reverse its direction.
    @Override
    public void move() {
        moveHorizontally();
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

