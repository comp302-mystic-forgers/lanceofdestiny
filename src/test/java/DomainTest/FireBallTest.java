package DomainTest;

import Domain.FireBall;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class FireBallTest {



    // Glass Box Tests
    @Test
    void testMoveWithinBounds() {
        FireBall fireBall = new FireBall(50, 50, Color.RED);
        fireBall.setVelocity(3, 2);
        fireBall.move(200, 200);
        assertEquals(53, fireBall.getX(), 0.01);
        assertEquals(52, fireBall.getY(), 0.01);
    }

    @Test
    void testCollisionWithLeftBorder() {
        FireBall fireBall = new FireBall(0, 50, Color.RED);
        fireBall.setVelocity(-3, 2);
        fireBall.move(200, 200);
        assertEquals(0, fireBall.getX(), 0.01);
        assertEquals(-3, fireBall.xVelocity, 0.01);
        assertEquals(52, fireBall.getY(), 0.01);
    }

    @Test
    void testCollisionWithRightBorder() {
        FireBall fireBall = new FireBall(180, 50, Color.RED);
        fireBall.setVelocity(3, 2);
        fireBall.move(200, 200);
        assertEquals(180, fireBall.getX(), 0.01);
        assertEquals(3, fireBall.xVelocity, 0.01);
        assertEquals(52, fireBall.getY(), 0.01);
    }

    @Test
    void testCollisionWithTopBorder() {
        FireBall fireBall = new FireBall(50, 0, Color.RED);
        fireBall.setVelocity(3, -2);
        fireBall.move(200, 200);
        assertEquals(53, fireBall.getX(), 0.01);
        assertEquals(0, fireBall.getY(), 0.01);
        assertEquals(-2, fireBall.yVelocity, 0.01);
    }

    @Test
    void testCollisionWithBottomBorder() {
        FireBall fireBall = new FireBall(50, 180, Color.RED);
        fireBall.setVelocity(3, 2);
        fireBall.move(200, 200);
        assertEquals(53, fireBall.getX(), 0.01);
        assertEquals(180, fireBall.getY(), 0.01);
        assertEquals(2, fireBall.yVelocity, 0.01);
    }

    @Test
    void testCollisionWithCorner1() {
        FireBall fireBall = new FireBall(0, 0, Color.RED);
        fireBall.setVelocity(-3, -2);
        fireBall.move(200, 200);
        assertEquals(0, fireBall.getX(), 0.01);
        assertEquals(0, fireBall.getY(), 0.01);
        assertEquals(-3, fireBall.xVelocity, 0.01);
        assertEquals(-2, fireBall.yVelocity, 0.01);
    }

    // Black Box Tests
    @Test
    void testBasicMovement() {
        FireBall fireBall = new FireBall(100, 100, Color.RED);
        fireBall.setVelocity(5, 5);
        fireBall.move(300, 300);
        assertEquals(105, fireBall.getX(), 0.01);
        assertEquals(105, fireBall.getY(), 0.01);
    }

    @Test
    void testCollisionWithVerticalBorderLeft() {
        FireBall fireBall = new FireBall(0, 100, Color.RED);
        fireBall.setVelocity(-5, 5);
        fireBall.move(300, 300);
        assertEquals(0, fireBall.getX(), 0.01);
        assertEquals(-5, fireBall.xVelocity, 0.01);
        assertEquals(105, fireBall.getY(), 0.01);
    }

    @Test
    void testCollisionWithHorizontalBorderTop() {
        FireBall fireBall = new FireBall(100, 0, Color.RED);
        fireBall.setVelocity(5, -5);
        fireBall.move(300, 300);
        assertEquals(105, fireBall.getX(), 0.01);
        assertEquals(0, fireBall.getY(), 0.01);
        assertEquals(-5, fireBall.yVelocity, 0.01);
    }

    @Test
    void testCollisionWithCorner() {
        FireBall fireBall = new FireBall(0, 0, Color.RED);
        fireBall.setVelocity(-5, -5);
        fireBall.move(300, 300);
        assertEquals(0, fireBall.getX(), 0.01);
        assertEquals(0, fireBall.getY(), 0.01);
        assertEquals(-5, fireBall.xVelocity, 0.01);
        assertEquals(-5, fireBall.yVelocity, 0.01);
    }
    @Test
    void testLargePanelNoCollision() {
        FireBall fireBall = new FireBall(100, 100, Color.RED);
        fireBall.setVelocity(20, 15);
        fireBall.move(1000, 1000);
        assertEquals(120, fireBall.getX(), 0.01);
        assertEquals(115, fireBall.getY(), 0.01);
    }

}