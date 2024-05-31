package Domain;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class FireBall {
    private double x, y; // Fireball position
    private final int diameter = 20; // Fireball size
    public double xVelocity = 3; // Fireball horizontal movement speed
    public double yVelocity = 2; // Fireball vertical movement speed
    private ImageIcon icon;
    private Color color;
    public boolean isBallActive = false;
    private boolean overwhelming;

    public FireBall(double startX, double startY, Color color) {
        this.x = startX;
        this.y = startY;
        this.icon = new ImageIcon("Assets/Images/200FireBall.png");
        this.color = color;
        this.overwhelming = false;
    }
    public void setVelocity(double xVel, double yVel) {
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
        if (color != null) {
            // Draw hex as colored oval
            g.setColor(color);
            g.fillOval((int) x, (int) y, diameter, diameter);
        } else if (icon != null) {
            // Draw normal fireball as image
            g.drawImage(icon.getImage(), (int) x, (int) y, diameter, diameter, null);
        } else {
            // Fallback in case the image is missing
            g.setColor(Color.RED);
            g.fillOval((int) x, (int) y, diameter, diameter);
        }
    }

    // Collision detection with the Magical Staff
    public boolean collidesWithMagicalStaff(MagicalStaff staff) {
        return x < staff.getX() + staff.getWidth() && x + diameter > staff.getX() &&
                y + diameter >= staff.getY() && y < staff.getY() + staff.getHeight();
    }
    public void updatePosition(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public void reverseYDirection() {
        yVelocity *= -1;
    }
    public void reverseXDirection() {
        xVelocity *= -1;
    }
    // Getter methods
    public double getX() { return x; }
    public double getY() { return y; }
    public int getDiameter() { return diameter; }
    public boolean isOverwhelming() {
        return overwhelming;
    }

    public void setOverwhelming(boolean overwhelming) {
        this.overwhelming = overwhelming;
    }
    public void setSpeedMultiplier(double multiplier) {
        this.xVelocity *= multiplier;
        this.yVelocity *= multiplier;
    }
}
