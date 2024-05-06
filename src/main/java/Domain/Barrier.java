package src.main.java.Domain;

import java.awt.*;
import java.util.UUID;

public abstract class Barrier {
    private UUID barrierId;
    protected int x, y; // Position
    protected int width, height; // Dimensions
    protected boolean destroyed;

    public Barrier(int x, int y, int width, int height) {
        this.barrierId = UUID.randomUUID();
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


    public UUID getBarrierId() {
        return barrierId;
    }

}



