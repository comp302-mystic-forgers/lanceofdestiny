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
    protected boolean destroyed;

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
    }
    public abstract void draw(Graphics g);

    public void destroy(){
        this.destroyed = true;
        repOK();
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



