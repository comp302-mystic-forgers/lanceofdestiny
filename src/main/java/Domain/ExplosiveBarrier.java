package Domain;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ExplosiveBarrier extends Barrier{
    private ImageIcon icon;

    private boolean hitStaff;

    private double angle; // current angle of rotation (in radians)
    private double angularSpeed; // speed of rotation (in radians per frame)
    private double radius; // radius of circular motion (1.5 * L)
    private double centerX; // x-coordinate of center of rotation
    private double centerY; // y-coordinate of center of rotation

    public ExplosiveBarrier(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.icon = new ImageIcon("Assets/Images/200Redgem.png");
        this.xSpeed = 0;
        this.ySpeed = 0;
        this.hitStaff = false;
        this.radius = 1.5 * width; // calculate radius based on width
        this.centerX = x + width / 2; // calculate center x-coordinate
        this.centerY = y + radius; // calculate center y-coordinate
        this.angle = 180.25; // initial angle of rotation
        this.angularSpeed = 0.025; // adjust angular speed to desired value
        moveable();
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

    // The explosive barrier can be either stiff or moving in circular fashion, if the
    // space around it allows. The radius of the circle is 1.5*L and it is centered at
    // (x1,y1) where x1 is the x coordinate of the center of the explosive barrier
    // and y1 is the y coordinate of the barrier minus 1.5*L.
    @Override
    public void move(int panelWidth, int panelHeight, ArrayList<Barrier> allBarriers) {

        moveInCircle(panelWidth, panelHeight, allBarriers);
    }

    public void moveInCircle(int panelWidth, int panelHeight, ArrayList<Barrier> allBarriers) {

        // Update angle of rotation
        angle += angularSpeed;

        // Calculate new position based on circular motion
        x = (int) (centerX + radius * Math.cos(angle));
        y = (int) (centerY + radius * Math.sin(angle));

        if (x < 0 || x + width > panelWidth || y < 0 || y + height > panelHeight) {
            // Reverse both x and y velocity
            reverseDirection();
        }
        else{
            // check collision with other barriers
            for (Barrier one : allBarriers){
                if (this.intersects(one) && one != this) {
                    reverseDirection();
                    break;
                }
            }

        }
    }


    public void reverseDirection() {
        angularSpeed *= -1;
    }

    public void staffHit(){
        hitStaff = true;
    }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getHeight() { return height; }
    public int getWidth() { return width; }

    public boolean isHitStaff() {
        return hitStaff;
    }
}
