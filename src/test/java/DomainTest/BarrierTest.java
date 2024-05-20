package DomainTest;

import Domain.Barrier;
import Domain.ExplosiveBarrier;
import Domain.SimpleBarrier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BarrierTest {

    // 1t Glass-Box Test: Check if the barrier is created with a unique identifier
    @Test
    public void testBarrierId() {
        Barrier barrier1 = new SimpleBarrier(0, 0, 10, 10);
        Barrier barrier2 = new ExplosiveBarrier(10, 10, 20, 20);
        assertNotEquals(barrier1.getBarrierId(), barrier2.getBarrierId());
        if (barrier1.getBarrierId() != barrier2.getBarrierId()){
            System.out.println("UUID are different. Test sucessful!");
        }
    }

}