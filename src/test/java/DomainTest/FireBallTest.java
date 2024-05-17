package DomainTest;

import Domain.FireBall;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FireBallTest {
    @Test
    public void testMoveWithoutCollision() {
        FireBall fireBall = new FireBall(50, 50);
        fireBall.setVelocity(3, 2);
        fireBall.move(200, 200); // Assuming panel size is 200x200

        assertEquals(53, fireBall.getX(), 0.01);
        assertEquals(52, fireBall.getY(), 0.01);
    }

    @Test
    public void testMoveWithLeftBorderCollision() {
        FireBall fireBall = new FireBall(1, 50);
        fireBall.setVelocity(-3, 2);
        fireBall.move(200, 200);

        assertEquals(0, fireBall.getX(), 0.01);
        assertEquals(52, fireBall.getY(), 0.01);
        assertEquals(-3, fireBall.xVelocity, 0.01);
    }

    @Test
    public void testMoveWithRightBorderCollision() {
        FireBall fireBall = new FireBall(180, 50);
        fireBall.setVelocity(3, 2);
        fireBall.move(200, 200);

        assertEquals(180, fireBall.getX(), 0.01);
        assertEquals(52, fireBall.getY(), 0.01);
        assertEquals(3, fireBall.xVelocity, 0.01);
    }

    @Test
    public void testMoveWithTopBorderCollision() {
        FireBall fireBall = new FireBall(50, 1);
        fireBall.setVelocity(3, -2);
        fireBall.move(200, 200);

        assertEquals(53, fireBall.getX(), 0.01);
        assertEquals(0, fireBall.getY(), 0.01);
        assertEquals(-2, fireBall.yVelocity, 0.01);
    }

    @Test
    public void testMoveWithBottomBorderCollision() {
        FireBall fireBall = new FireBall(50, 180);
        fireBall.setVelocity(3, 2);
        fireBall.move(200, 200);

        assertEquals(53, fireBall.getX(), 0.01);
        assertEquals(180, fireBall.getY(), 0.01);
        assertEquals(2, fireBall.yVelocity, 0.01);
    }

    @Test
    public void testMoveDiagonallyWithoutCollision() {
        FireBall fireBall = new FireBall(50, 50);
        fireBall.setVelocity(2, 2);
        fireBall.move(200, 200); // Assuming panel size is 200x200

        assertEquals(52, fireBall.getX(), 0.01);
        assertEquals(52, fireBall.getY(), 0.01);
    }

    @Test
    public void testMoveWithLeftBorderCollisionAndBounceBack() {
        FireBall fireBall = new FireBall(2, 50);
        fireBall.setVelocity(-5, 2);
        fireBall.move(200, 200);

        assertEquals(0, fireBall.getX(), 0.01); // Check if fireball's position is corrected to border
        assertEquals(52, fireBall.getY(), 0.01); // Check if fireball moved vertically as expected
        assertEquals(-5, fireBall.xVelocity, 0.01); // Check if fireball's x velocity reversed
    }

    @Test
    public void testMoveWithRightBorderCollisionAndBounceBack() {
        FireBall fireBall = new FireBall(180, 50);
        fireBall.setVelocity(5, 2);
        fireBall.move(200, 200);

        assertEquals(180, fireBall.getX(), 0.01); // Check if fireball's position is corrected to border
        assertEquals(52, fireBall.getY(), 0.01); // Check if fireball moved vertically as expected
        assertEquals(5, fireBall.xVelocity, 0.01); // Check if fireball's x velocity reversed
    }

    @Test
    public void testMoveWithTopBorderCollisionAndBounceBack() {
        FireBall fireBall = new FireBall(50, 2);
        fireBall.setVelocity(3, -4);
        fireBall.move(200, 200);

        assertEquals(53, fireBall.getX(), 0.01); // Check if fireball moved horizontally as expected
        assertEquals(0, fireBall.getY(), 0.01); // Check if fireball's position is corrected to border
        assertEquals(-4, fireBall.yVelocity, 0.01); // Check if fireball's y velocity reversed
    }

    @Test
    public void testMoveWithBottomBorderCollisionAndBounceBack() {
        FireBall fireBall = new FireBall(50, 180);
        fireBall.setVelocity(3, 4);
        fireBall.move(200, 200);

        assertEquals(53, fireBall.getX(), 0.01); // Check if fireball moved horizontally as expected
        assertEquals(180, fireBall.getY(), 0.01); // Check if fireball's position is corrected to border
        assertEquals(4, fireBall.yVelocity, 0.01); // Check if fireball's y velocity reversed
    }

}