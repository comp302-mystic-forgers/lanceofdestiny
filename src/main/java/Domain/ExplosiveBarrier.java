package Domain;

import javax.swing.*;
import java.awt.*;

public class ExplosiveBarrier extends Barrier{
    private ImageIcon icon;
    private int xSpeed; //DO WE NEED IT TO MOVE IT HORIZONTALY? EVET
    private int ySpeed;

    private boolean hitStaff;

    public ExplosiveBarrier(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.icon = new ImageIcon("Assets/Images/200Redgem.png");
        this.xSpeed = 0;
        this.ySpeed = 0;
        this.hitStaff = false;
    }

    @Override
    public void draw(Graphics g) {
        if (!destroyed) {
            g.drawImage(icon.getImage(), x, y, width, height, null);
            if(isFrozen()) {
                g.drawImage(icon.getImage(), x, y, width, height, null);
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.BOLD, 15));
                g.drawString("FROZEN", x + width / 4, y + height / 2);
            }
        }
        else {
            if (!hitStaff) {
                g.drawImage(icon.getImage(), x, y, width, height, null);
                y += ySpeed;
                x += xSpeed;
            }
        }
    }
    public boolean collidesWithFireBall(FireBall fireBall) {
        Rectangle barrierBounds = new Rectangle(x, y, width, height);
        Rectangle ballBounds = new Rectangle((int)fireBall.getX(), (int)fireBall.getY(), fireBall.getDiameter(), fireBall.getDiameter());
        return !destroyed && barrierBounds.intersects(ballBounds);
    }
    public boolean collidesWithMagicalStaff(MagicalStaff magicalStaff) {
        Rectangle barrierBounds = new Rectangle(x, y, width, height);
        Rectangle staffBounds = new Rectangle((int)magicalStaff.getX(), (int) magicalStaff.getY(), (int) magicalStaff.getWidth(), (int) magicalStaff.getHeight());
        return !this.isHitStaff() && barrierBounds.intersects(staffBounds);
    }

    public void handleCollisionResponse(FireBall fireBall) {
        // Reverse FireBall's direction
        fireBall.reverseYDirection();
        destroyed = true;
        xSpeed = (int) (Math.random() * 8);
        ySpeed = 5;
    }
    public void staffHit(){
        hitStaff = true;
    }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getXSpeed() {return xSpeed;}
    public int getYSpeed() {return ySpeed;}
    public int getHeight() { return height; }
    public int getWidth() { return width; }

    public boolean isHitStaff() {
        return hitStaff;
    }
}
