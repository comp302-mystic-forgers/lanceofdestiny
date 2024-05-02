package Domain;
import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.awt.*;

public class ReinforcedBarrier extends Barrier{
    private int hitsRequired;
    private int hitsReceived;
    private ImageIcon icon;

    public ReinforcedBarrier(int x, int y, int width, int height) {
        super(x, y, width, height);
        Random random = new Random();
        this.hitsRequired = random.nextInt(5) + 1;
        this.hitsReceived = hitsRequired;
        this.icon = new ImageIcon("Assets/Images/200Firm.png");
    }

    @Override
    public void draw(Graphics g) {
        if(!destroyed){
            if (hitsReceived > 0) {
                g.drawImage(icon.getImage(), x, y, width, height, null);
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 15));
                g.drawString(String.valueOf(hitsReceived), x + 20, y + 17);
            }
        }
    }

    // Method to handle collision with FireBall
    public boolean collidesWithFireBall(FireBall fireBall) {
        return !isDestroyed() && fireBall.getX() + fireBall.getDiameter() >= x &&
                fireBall.getX() <= x + width && fireBall.getY() + fireBall.getDiameter() >= y &&
                fireBall.getY() <= y + height;
    }

    public void decreaseHitsReceived() {
        hitsReceived--;
    }

    public boolean isDestroyed() {
        return hitsReceived <= 0;
    }

    public void handleCollisionResponse(FireBall fireBall) {
        fireBall.reverseYDirection();
        decreaseHitsReceived();
    }
}
