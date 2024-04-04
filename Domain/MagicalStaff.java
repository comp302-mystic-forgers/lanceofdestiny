package Domain;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.KeyEvent;

public class MagicalStaff {
    private int xPosition, yPosition;
    private int width, height = 20;
    private double angle = 0; // Rotation angle in degrees

    public MagicalStaff(int panelWidth, int panelHeight) {
        this.width = (int) (panelWidth * 0.1); // Initialize staff width as 10% of panel width
        this.xPosition = (panelWidth - width) / 2; // Center the staff horizontally
        this.yPosition = panelHeight - height - 30; // Position staff from the bottom
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        // Translate the graphics context to the rotation anchor point
        g2d.translate(xPosition + width / 2, yPosition + height / 2);
        // Rotate the graphics context
        g2d.rotate(Math.toRadians(angle));
        // Translate back and draw the staff
        g2d.setColor(Color.BLACK);
        g2d.fillRect(-width / 2, -height / 2, width, height);

        g2d.dispose(); // Dispose of this graphics context and release any system resources that it is using
    }

    public void move(int keyCode, int panelWidth) {
        int movementSpeed = 10;
        if (keyCode == KeyEvent.VK_LEFT) {
            xPosition = Math.max(0, xPosition - movementSpeed);
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            xPosition = Math.min(panelWidth - width, xPosition + movementSpeed);
        }
    }

    public void rotate(int keyCode) {
        if (keyCode == KeyEvent.VK_A) {
            angle -= 45; // Rotate left
            if (angle < -135) angle = -135; // Limit rotation
        } else if (keyCode == KeyEvent.VK_D) {
            angle += 45; // Rotate right
            if (angle > 135) angle = 135; // Limit rotation
        }
    }

    // Reset rotation to horizontal
    public void resetRotation() {
        angle = 0;
    }
    // In MagicalStaff.java, add a method to update position:
    public void updatePosition(int panelWidth, int panelHeight) {
        this.width = (int) (panelWidth * 0.1); // 10% of the panel width
        this.xPosition = (panelWidth - this.width) / 2; // Center the staff horizontally
        this.yPosition = panelHeight - this.height - 30; // Position from the bottom
    }


    // Getters
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getX() { return xPosition; }
    public int getY() { return yPosition; }
}

