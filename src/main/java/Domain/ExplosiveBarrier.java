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
        return  !destroyed && fireBall.getX() + fireBall.getDiameter() >= x &&
                fireBall.getX() <= x + width && fireBall.getY() + fireBall.getDiameter() >= y &&
                fireBall.getY() <= y + height;
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
