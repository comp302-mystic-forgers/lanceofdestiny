package Domain;

import javax.swing.*;
import java.awt.*;

public class RewardingBarrier extends Barrier{
    private ImageIcon icon;
    private ImageIcon icon2;
    private boolean collected;
    private Gift gift;

    public RewardingBarrier(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.icon = new ImageIcon("Assets/Images/200Greengem.png");
        this.icon2 = new ImageIcon("Assets/Images/giftbox.png");
        this.collected = false;
    }

    @Override
    public void draw(Graphics g) {
        if (!destroyed) {
            g.drawImage(icon.getImage(), x, y, width, height, null);
            if(isFrozen()){
                g.drawImage(icon.getImage(), x, y, width, height, null);
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.BOLD, 15));
                g.drawString("FROZEN", x + width / 4, y + height / 2);
            }
        } else if (!collected && gift != null) {
            g.drawImage(icon2.getImage(), gift.getX(), gift.getY(), width, height * 2, null);
        }
    }
    public void update() {
        if (destroyed && !collected && gift != null) {
            gift.updatePosition();
        }
    }
    public boolean collidesWithMagicalStaff(MagicalStaff magicalStaff) {
        if (gift == null) return false;
        Rectangle giftBounds = new Rectangle(gift.getX(), gift.getY(), width, height);
        Rectangle staffBounds = new Rectangle((int)magicalStaff.getX(), (int) magicalStaff.getY(), (int) magicalStaff.getWidth(), (int) magicalStaff.getHeight());
        return !collected && giftBounds.intersects(staffBounds);
    }

    public boolean collidesWithFireBall(FireBall fireBall) {
        Rectangle barrierBounds = new Rectangle(x, y, width, height);
        Rectangle ballBounds = new Rectangle((int)fireBall.getX(), (int)fireBall.getY(), fireBall.getDiameter(), fireBall.getDiameter());
        return !destroyed && barrierBounds.intersects(ballBounds);
    }

    public void destroy() {
        destroyed = true;
        gift = new Gift(x, y);
    }
    public Gift getGift() {
        return gift;
    }
    public void handleCollisionResponse(FireBall fireBall) {
        fireBall.reverseYDirection();
        destroy();
    }
    public int getX() { return x; }
    public int getY() { return y; }
    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }
}
