package Domain;

import java.awt.*;
import java.util.UUID;

// Abstract Function: The Barrier class provides a way to represent a barrier in a graphical environment
// and render it on the screen using the draw method. It also allows the barrier to be destroyed
// and checks if it is already destroyed. It is an obstacle that needs to be destroyed by the player
// in order to win the game. For this it needs to be hit by a fireball a certain amount of times.
public abstract class Barrier {
    private UUID barrierId;
    protected int x, y; // Position
    protected int width, height; // Dimensions

    protected double xSpeed; // Barrier horizontal movement speed
    protected double ySpeed; // Barrier vertical movement speed
    protected boolean destroyed;

    // probability of moving horizontally or in circle
    private static final double probHoriz = 0.2;
    private static final double probCircle = 0.2;

    //Representation Invariant:
    //x and y has to be non-negative
    //width and height has to be positive
    //destroyed has to be boolean
    private void repOK() {
        assert x >= 0 : "x position non-negative";
        assert y >= 0 : "y position non-negative";
        assert width > 0 : "width positive";
        assert height > 0 : "height positive";
    }

    public Barrier(int x, int y, int width, int height) {
        this.barrierId = UUID.randomUUID();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.destroyed = false;
        repOK();
        move();
    }
    public abstract void draw(Graphics g);

    public void destroy(){
        this.destroyed = true;
        repOK();
    }

    protected void moveHorizontally() {
        if (Math.random() < probHoriz) {
            xSpeed = (int) (Math.random() * 4) - 2;
            ySpeed = 0;
        } else {
            xSpeed = 0;
            ySpeed = 0;
        }
        // add check for collision with borders of map & with other barriers (as extra method that
        // move method calls)
    }

    protected void moveInCircle() {
        if (Math.random() < probCircle) {
            xSpeed = (int) (Math.random() * 8) - 4;
            ySpeed = (int) (Math.random() * 8) - 4;
            double distance = Math.sqrt(xSpeed * xSpeed + ySpeed * ySpeed);
            double scale = 1.5 * width;
            xSpeed = (int) (xSpeed * scale / distance);
            ySpeed = (int) (ySpeed * scale / distance);
        } else {
            xSpeed = 0;
            ySpeed = 0;
        }
    }

    // almost same as move method in FireBall for borders
    protected void dontCollideWithBorders(int panelWidth, int panelHeight){
        x += xSpeed;
        y += ySpeed;

        // Check for collision with left and right borders
        if (x <= 0 || x + width >= panelWidth) {
            // Reverse x velocity
            xSpeed = -xSpeed;
            // Adjust position to ensure Barrier stays within bounds
            x = Math.max(0, Math.min(x, panelWidth - width));
        }

        // Check for collision with top and bottom borders
        if (y <= 0 || y + height >= panelHeight) {
            // Reverse y velocity
            ySpeed = -ySpeed;
            // Adjust position to ensure FireBall stays within bounds
            y = Math.max(0, Math.min(y, panelHeight - width));
        }

        // Check for collision with left and right borders
        if (x <= 0 || x + width >= panelWidth || y <= 0 || y + height >= panelHeight) {
            // Reverse both x and y velocity (for corners)
            if (x <= 0 || x + width >= panelWidth) {
                xSpeed = -xSpeed;
            }
            if (y <= 0 || y + height >= panelHeight) {
                ySpeed = -ySpeed;
            }
        }
    }

    protected void dontCollideWithBarriers(){
        x += xSpeed;
        y += ySpeed;
        // Check for collision with left and right neighbours

        //  Check for collision with top and bottom neighbours
    }



    public boolean isDestroyed() {
        return destroyed;
    }


    public UUID getBarrierId() {
        return barrierId;
    }

    // The movement speed of the moving barriers is L/4 per second.
    public void move(){

    }

    public void setSpeed(double xVel, double yVel) {
        this.xSpeed = xVel;
        this.ySpeed = yVel;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}



