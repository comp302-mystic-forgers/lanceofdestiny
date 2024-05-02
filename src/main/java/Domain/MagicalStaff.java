package Domain;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class MagicalStaff {
    private double xPosition, yPosition;
    private double width, height = 20;
    private double angle = 0; // Rotation angle in degrees
    private ImageIcon icon;

    public MagicalStaff(double panelWidth, double panelHeight) {
        this.width = panelWidth * 0.1; // Initialize staff width as 10% of panel width
        this.xPosition = (panelWidth - width) / 2; // Center the staff horizontally
        this.yPosition = panelHeight - height - 30; // Position staff from the bottom
        this.icon = new ImageIcon("Assets/Images/200Player.png");
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void draw(Graphics g) {
        if (icon!= null) {
            Graphics2D g2d = (Graphics2D) g.create();

            // Translate the graphics context to the rotation anchor point
            g2d.translate(xPosition + width / 2, yPosition + height / 2);
            // Rotate the graphics context
            g2d.rotate(Math.toRadians(angle));
            // Translate back and draw the staff
            g2d.drawImage(icon.getImage(), -icon.getIconWidth() / 2, -icon.getIconHeight() / 2, null);

            g2d.dispose(); // Dispose of this graphics context and release any system resources that it is using
        } else {
            Graphics2D g2d = (Graphics2D) g.create();

            // Translate the graphics context to the rotation anchor point
            g2d.translate(xPosition + width / 2, yPosition + height / 2);
            // Rotate the graphics context
            g2d.rotate(Math.toRadians(angle));
            // Translate back and draw the staff
            g2d.setColor(Color.BLACK);
            g2d.fillRect((int) -width / 2, (int) -height / 2, (int) width, (int) height);

            g2d.dispose(); // Dispose of this graphics context and release any system resources that it is using
        }
    }

    public void move(int keyCode, int panelWidth, int type) {
        double pressSpeed = this.width/2;
        double holdSpeed = 2*this.width;
        if (type == 0) {
            if (keyCode == KeyEvent.VK_LEFT) {
                xPosition = Math.max(0, xPosition - pressSpeed);
            } else if (keyCode == KeyEvent.VK_RIGHT) {
                xPosition = Math.min(panelWidth - width, xPosition + pressSpeed);
            }
        } else if (type == 1) {
            if (keyCode == KeyEvent.VK_LEFT) {
                xPosition = Math.max(0, xPosition - 0.01 * holdSpeed);
            } else if (keyCode == KeyEvent.VK_RIGHT) {
                xPosition = Math.min(panelWidth - width, xPosition + 0.01 * holdSpeed);
            }
        }

        // Ensure staff stays within bounds
        if (xPosition < 0) {
            xPosition = 0;
        } else if (xPosition + width > panelWidth) {
            xPosition = panelWidth - width;
        }
    }


    public void rotate(int keyCode) {
        if (keyCode == KeyEvent.VK_A) {
            angle -= 20 * 0.1; // Rotate left
            if (angle < -45) angle = -45; // Limit rotation
        } else if (keyCode == KeyEvent.VK_D) {
            angle += 20 * 0.1; // Rotate right
            if (angle > 45) angle = 45; // Limit rotation
        }
    }

    // Reset rotation to horizontal
    public void resetRotation() {
        while (angle >= 45) {
            angle = angle - 45;
        }
        angle = 0;
    }

    // In MagicalStaff.java, add a method to update position:
    public void updatePosition(int panelWidth, int panelHeight) {
        this.width = panelWidth * 0.1; // 10% of the panel width
        this.xPosition = (panelWidth - this.width) / 2; // Center the staff horizontally
        this.yPosition = panelHeight - this.height - 30; // Position from the bottom
    }


    // Getters
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public double getX() { return xPosition; }
    public double getY() { return yPosition; }

}

