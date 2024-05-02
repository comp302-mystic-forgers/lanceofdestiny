package Domain;

import java.awt.*;

public abstract class Barrier {
    protected int x, y; // Position
    protected int width, height; // Dimensions
    protected boolean destroyed;

    public Barrier(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.destroyed = false;
    }

    public abstract void draw(Graphics g);

    public void destroy(){
        this.destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}



