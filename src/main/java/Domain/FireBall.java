package Domain;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class FireBall {
    private int x, y; // Fireball position
    private final int diameter = 20; // Fireball size
    private int xVelocity = 1; // Fireball horizontal movement speed
    private int yVelocity = - 1; // Fireball vertical movement speed
    private ImageIcon icon;

    public boolean isBallActive = false;

    public FireBall(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.icon = new ImageIcon("Assets/Images/200FireBall.png");
    }
    public void setVelocity(int xVel, int yVel) {
        this.xVelocity = xVel;
        this.yVelocity = yVel;
    }

    public void ballThrower(int keyCode) {
        if (keyCode == KeyEvent.VK_SPACE) {
            isBallActive = true;
        }
    }

    // Moves the Fireball and bounces it off the borders
    public void move(int panelWidth, int panelHeight) {
        x += xVelocity;
        y += yVelocity;

        // Check for collision with left and right borders
        if (x <= 0 || x + diameter >= panelWidth) {
            // Reverse x velocity
            xVelocity = -xVelocity;
            // Adjust position to ensure FireBall stays within bounds
            x = Math.max(0, Math.min(x, panelWidth - diameter));
        }

        // Check for collision with top and bottom borders
        if (y <= 0 || y + diameter >= panelHeight) {
            // Reverse y velocity
            yVelocity = -yVelocity;
            // Adjust position to ensure FireBall stays within bounds
            y = Math.max(0, Math.min(y, panelHeight - diameter));
        }

        // Check for collision with left and right borders
        if (x <= 0 || x + diameter >= panelWidth || y <= 0 || y + diameter >= panelHeight) {
            // Reverse both x and y velocity (for corners)
            if (x <= 0 || x + diameter >= panelWidth) {
                xVelocity = -xVelocity;
            }
            if (y <= 0 || y + diameter >= panelHeight) {
                yVelocity = -yVelocity;
            }
        }
    }

    public void draw(Graphics g) {
        if (icon!= null) {
            g.drawImage(icon.getImage(), x, y, diameter, diameter, null);
        } else {
            g.setColor(Color.RED);
            g.fillOval(x, y, diameter, diameter);
        }
    }

    // Collision detection with the Magical Staff
    public boolean collidesWithMagicalStaff(MagicalStaff staff) {
        return x < staff.getX() + staff.getWidth() && x + diameter > staff.getX() &&
                y + diameter >= staff.getY() && y < staff.getY() + staff.getHeight();
    }

    public void reverseYDirection() {
        yVelocity *= -1;
    }
    public void reverseXDirection() {
        xVelocity *= -1;
    }
    // Getter methods
    public int getX() { return x; }
    public int getY() { return y; }
    public int getDiameter() { return diameter; }
}
