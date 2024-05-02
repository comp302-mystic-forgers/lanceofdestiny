package Domain;

import java.awt.*;

public abstract class Barrier {
    protected int x, y; // Position
    protected int width, height; // Dimensions

    public Barrier(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void draw(Graphics g);
}



