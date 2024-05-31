package Domain;
import javax.swing.*;
import java.awt.Graphics;

public class HollowPurpleBarrier extends SimpleBarrier {


    public HollowPurpleBarrier(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.IsAddScore = false;
        this.icon = new ImageIcon("Assets/Images/HollowBarrier.png");
        this.frozenIcon = new ImageIcon("Assets/Images/FrozenHollow.png");
    }



    @Override
    public void draw(Graphics g) {
        if (!destroyed) {
            if(!isFrozen){
                g.drawImage(icon.getImage(), x, y, width, height, null);
            }
            else{
                g.drawImage(frozenIcon.getImage(), x, y, width, height, null);
            }

        }
    }

    @Override
    public void destroy() {
        this.destroyed = true;
        // Hollow Purple Barrier yıkıldığında puan eklemiyor
    }

    @Override
    public void handleCollisionResponse(FireBall fireBall) {
        fireBall.reverseYDirection();
    }

    @Override
    public boolean collidesWithFireBall(FireBall fireBall) {
        return !destroyed && fireBall.getX() + fireBall.getDiameter() >= x &&
                fireBall.getX() <= x + width && fireBall.getY() + fireBall.getDiameter() >= y &&
                fireBall.getY() <= y + height;
    }
}


