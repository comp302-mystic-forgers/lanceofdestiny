package Domain;

import java.awt.*;
import java.util.ArrayList;
import java.util.UUID;

// Abstract Function: The Barrier class provides a way to represent a barrier in a graphical environment
// and render it on the screen using the draw method. It also allows the barrier to be destroyed
// and checks if it is already destroyed. It is an obstacle that needs to be destroyed by the player
// in order to win the game. For this it needs to be hit by a fireball a certain amount of times.
public abstract class Barrier {
    private UUID barrierId;
    protected int x, y; // Position
    protected int width, height; // Dimensions

    public double xSpeed; // Barrier horizontal movement speed
    public double ySpeed; // Barrier vertical movement speed
    protected boolean destroyed;
    private boolean frozen;

    protected boolean moves;

    // probability of moving horizontally or in circle
    private static final double probMovable = 0.2;

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
        this.frozen = false;
        this.moves = false;
        moveable();
        repOK();
    }
    public abstract void draw(Graphics g);

    public void destroy(){
        if(!frozen){
            this.destroyed = true;
        }
        repOK();
    }

    protected void moveable(){
        if (Math.random() < probMovable){
            moves = true;
        }
    }

    protected void moveHorizontally() {
        if (moves) {
            xSpeed = (getWidth()/4)/15;
            ySpeed = 0;
        } else {
            xSpeed = 0;
            ySpeed = 0;
        }
    }

    // The movement speed of the moving barriers is L/4 per second.
    public void move(int panelWidth, int panelHeight, ArrayList<Barrier> allBarriers){
        dontCollide(panelWidth, panelHeight, allBarriers);
    }

    // almost same as move method in FireBall for borders for border detection
    protected void dontCollide(int panelWidth, int panelHeight, ArrayList<Barrier> allBarriers){
        x += xSpeed;
        y += ySpeed;
        if (x < 0 || x + width > panelWidth || y < 0 || y + height > panelHeight) {
            // Reverse both x and y velocity (for corners) and reposition to stay in bounds
            reverseXDirection();
            x = Math.max(0, Math.min(x, panelWidth - width));
            reverseYDirection();
            y = Math.max(0, Math.min(y, panelHeight - width));
        }
        else{
            // check collision with other barriers
            for (Barrier one : allBarriers){
                if (this.intersects(one) && one != this) {
                    reverseXDirection();
                    reverseYDirection();
                    break;
                }
            }

        }
    }

    public boolean intersects (Barrier other){
        return !(x + width < other.x || x > other.x + other.width || y + height < other.y || y > other.y + other.height);
    }

    public void reverseYDirection() {
        ySpeed *= -1;
    }
    public void reverseXDirection() {
        xSpeed *= -1;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public UUID getBarrierId() {
        return barrierId;
    }

    public void setSpeed(double xVel, double yVel) {
        this.xSpeed = xVel;
        this.ySpeed = yVel;
    }

    public void move(){
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



