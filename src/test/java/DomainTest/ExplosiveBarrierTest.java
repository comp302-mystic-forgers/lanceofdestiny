package DomainTest;

import static org.junit.jupiter.api.Assertions.*;

import Domain.Barrier;
import Domain.FireBall;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class ExplosiveBarrierTest {

    private ExplosiveBarrier explosiveBarrier;
    private FireBall fireBall;

    @BeforeEach
    public void setUp() {
        explosiveBarrier = new ExplosiveBarrier(50, 50, 100, 100);
        fireBall = new FireBall(60, 60);
    }

    //GlassBox Test
    @Test
    public void testReversedY() {
        explosiveBarrier.handleCollisionResponse(fireBall);

        double oldyVel = fireBall.yVelocity;
        explosiveBarrier.handleCollisionResponse(fireBall);
        assertEquals(oldyVel * (-1), fireBall.yVelocity, "Fireball's Y direction should be reversed");

    }

    //GlassBox Test
    @Test
    public void testReversedX() {
        explosiveBarrier.handleCollisionResponse(fireBall);

        double oldxVel = fireBall.xVelocity;
        explosiveBarrier.handleCollisionResponse(fireBall);
        assertNotEquals(oldxVel * (-1), fireBall.xVelocity, "Fireball's X direction should not be reversed");

    }

    //GlassBox Test
    @Test
    public void testIsDestroyed() {
        explosiveBarrier.handleCollisionResponse(fireBall);

        assertTrue(explosiveBarrier.isDestroyed(), "ExplosiveBarrier should be marked as destroyed");

    }

    //GlassBox Test
    @Test
    public void testxSpeed() {
        explosiveBarrier.handleCollisionResponse(fireBall);

        int xSpeed = explosiveBarrier.getXSpeed();
        assertTrue(xSpeed >= 0 && xSpeed < 8, "xSpeed should be between 0 and 7");

    }

    //GlassBox Test
    @Test
    public void testySpeed() {
        explosiveBarrier.handleCollisionResponse(fireBall);

        assertEquals(5, explosiveBarrier.getYSpeed(), "ySpeed should be 5");
    }

    //GlassBox Test: check for expected
    private void handleCollisionResponse(FireBall fireBall) {
        fireBall.reverseYDirection();
        explosiveBarrier.setDestroyed(true);
        explosiveBarrier.setXSpeed((int) (Math.random() * 8));
        explosiveBarrier.setYSpeed(5);
    }
}

class ExplosiveBarrier extends Barrier {
    private int xSpeed;
    private int ySpeed;
    private boolean hitStaff;

    public ExplosiveBarrier(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.xSpeed = 0;
        this.ySpeed = 0;
        this.hitStaff = false;
    }

    @Override
    public void draw(Graphics g) {

    }

    public void handleCollisionResponse(FireBall fireBall) {
        fireBall.reverseYDirection();
        destroyed = true;
        xSpeed = (int) (Math.random() * 8);
        ySpeed = 5;
    }

    public int getXSpeed() {
        return xSpeed;
    }

    public int getYSpeed() {
        return ySpeed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public void setXSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setYSpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

}
