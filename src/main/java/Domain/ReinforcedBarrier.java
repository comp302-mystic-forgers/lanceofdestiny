package Domain;
import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.awt.*;

public class ReinforcedBarrier extends Barrier{
    private int hitsRequired;
    private int hitsReceived;
    public ImageIcon icon;
    public ImageIcon frozenIcon;
    // Can move horizontally, if it has a free space around it in the x-axis.
    // Might be moving back and forth with a probability of 0.2, or stiff with
    // a probability of 0.8. It will of course move in its free space, meaning
    // that if it is about to collide with another barrier it will reverse its direction.
    public ReinforcedBarrier(int x, int y, int width, int height) {
        super(x, y, width, height);
        Random random = new Random();
        this.hitsRequired = random.nextInt(5) + 1;
        this.hitsReceived = hitsRequired;
        this.icon = new ImageIcon("Assets/Images/200Firm.png");
        this.frozenIcon = new ImageIcon("Assets/Images/FrozenReinforcement.png");
        moveable();
        moveHorizontally();
    }

    @Override
    public void draw(Graphics g) {
        if(!destroyed){
            if (hitsReceived > 0) {
                if(!isFrozen) {
                    g.drawImage(icon.getImage(), x, y, width, height, null);
                }
                else {
                    g.drawImage(frozenIcon.getImage(), x, y, width, height, null);
                }
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Arial", Font.BOLD, 15));
                    g.drawString(String.valueOf(hitsReceived), x + 20, y + 17);


            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
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

    public int getHitsRequired() {
        return hitsRequired;
    }

    public void setHitsRequired(int hitsRequired) {
        this.hitsRequired = hitsRequired;
    }

    public int getHitsReceived() {
        return hitsReceived;
    }

    public void setHitsReceived(int hitsReceived) {
        this.hitsReceived = hitsReceived;
    }
}
