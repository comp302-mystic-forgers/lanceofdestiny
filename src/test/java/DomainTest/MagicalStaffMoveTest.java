package DomainTest;
import Domain.MagicalStaff;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

public class MagicalStaffMoveTest {
    private MagicalStaff magicalStaff;
    private final int panelWidth = 800;

    @BeforeEach //Works before every test, and sets magical staff in panel
    public void setup() {
        magicalStaff = new MagicalStaff(panelWidth, 600); // Initialize with a standard panel width and height
    }

    //1st Black-Box Test
    //Checking if MagicalStaff moves correctly to the needed left side
    //Checks that input is VK_Left and then checks the expected xPosition
    @Test
    public void testPressLeftWithinBounds() {
        double initialX = magicalStaff.getX();
        magicalStaff.move(KeyEvent.VK_LEFT, panelWidth, 0);
        assertEquals(Math.max(0, initialX - magicalStaff.getWidth() / 2), magicalStaff.getX());
    }

    //2nd Black-Box Test
    //Works the same as the firs one, but checks MagicalStaff moves to the right side
    @Test
    public void testPressRightWithinBounds() {
        double initialX = magicalStaff.getX();
        magicalStaff.move(KeyEvent.VK_RIGHT, panelWidth, 0);
        assertEquals(Math.min(panelWidth - magicalStaff.getWidth(), initialX + magicalStaff.getWidth() / 2), magicalStaff.getX());
    }

    //1st Glass-Box Test
    //Checks if MagicalStaff moves correctly in needed time to left side, when left key is pressed
    //Checks the calculation for moving magicalStaff and checks if its working correctly
    @Test
    public void testHoldLeftWithinBounds() {
        double initialX = magicalStaff.getX();
        magicalStaff.move(KeyEvent.VK_LEFT, panelWidth, 1);
        assertEquals(Math.max(0, initialX - 0.01 * 2 * magicalStaff.getWidth()), magicalStaff.getX());
    }

    //2nd Glass-Box Test
    //works the same as 1st one, just to the different side
    @Test
    public void testHoldRightWithinBounds() {
        double initialX = magicalStaff.getX();
        magicalStaff.move(KeyEvent.VK_RIGHT, panelWidth, 1);
        assertEquals(Math.min(panelWidth - magicalStaff.getWidth(), initialX + 0.01 * 2 * magicalStaff.getWidth()), magicalStaff.getX());
    }

    //3rd Black-Box Test
    //This test is checking if there is no bug in moving magical staff beyond panel bounds (right side)
    //Starting with magicalStaff in the centre and moving it to  panel bound
    //Then trying again to move it further
    @Test
    public void testPressRightOutOfBounds() {
        magicalStaff.updatePosition(panelWidth, 600);
        while (magicalStaff.getX() < panelWidth - magicalStaff.getWidth()) {
            magicalStaff.move(KeyEvent.VK_RIGHT, panelWidth, 0);
        }
        magicalStaff.move(KeyEvent.VK_RIGHT, panelWidth, 0);
        assertEquals(panelWidth - magicalStaff.getWidth(), magicalStaff.getX());
    }

    //4th Black-Box Test
    //Works the same as 3rd one only with left side boundaries
    @Test
    public void testPressLeftOutOfBounds() {
        magicalStaff.updatePosition(panelWidth, 600);
        while (magicalStaff.getX() > 0) {
            magicalStaff.move(KeyEvent.VK_LEFT, panelWidth, 0);
        }
        magicalStaff.move(KeyEvent.VK_LEFT, panelWidth, 0);
        assertEquals(0, magicalStaff.getX());
    }

    //3rd Glass-Box Test
    //Checks if parameters of magicalStaff does not go beyond right panel boundary
    //Starting from the middle of panel
    //Then we are moving MagicalStaff to the right till it reaches boundary
    //Then we are trying to move it more furter two more times
    //Then we are looking at parameters and assure that MagicalStaff position is not beyond the panel
    @Test
    public void testEnsureWithinBoundsRight() {
        magicalStaff.updatePosition(panelWidth, 600);
        while (magicalStaff.getX() < panelWidth - magicalStaff.getWidth()) {
            magicalStaff.move(KeyEvent.VK_RIGHT, panelWidth, 0);
        }
        magicalStaff.move(KeyEvent.VK_RIGHT, panelWidth, 0);
        magicalStaff.move(KeyEvent.VK_RIGHT, panelWidth, 0);
        assertEquals(panelWidth - magicalStaff.getWidth(), magicalStaff.getX());
    }
}
