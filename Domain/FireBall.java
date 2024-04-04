package Domain;
import java.awt.Graphics;
import java.awt.Color;

public class FireBall {
    private int x, y; // Fireball position
    private final int diameter = 16; // Fireball size
    private int xVelocity = 1; // Fireball horizontal movement speed
    private int yVelocity = 1; // Fireball vertical movement speed

    public FireBall(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }
    public void setVelocity(int xVel, int yVel) {
        this.xVelocity = xVel;
        this.yVelocity = yVel;
    }

    // Moves the Fireball and bounces it off the borders
    public void move(int panelWidth, int panelHeight) {
        x += xVelocity;
        y += yVelocity;

        // Check for collision with left and right borders
        if (x <= 0 || x + diameter >= panelWidth) {
            xVelocity = -xVelocity;
        }

        // Check for collision with top border
        if (y <= 0) {
            yVelocity = -yVelocity;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x, y, diameter, diameter);
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