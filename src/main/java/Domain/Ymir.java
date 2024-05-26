package Domain;
import org.junit.jupiter.api.DisplayNameGenerator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Ymir {
    private DoubleAccel doubleAccel;
    private InfiniteVoid infiniteVoid;
    private Timer timer;

    public Ymir(FireBall fireball, ArrayList<Barrier> barriers) {
        this.doubleAccel = new DoubleAccel(fireball);
        this.infiniteVoid = new InfiniteVoid(barriers);
        this.timer = new Timer();
        scheduleSpellActivation();
    }

    private void scheduleSpellActivation() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //doubleAccel.activate();
                infiniteVoid.activate();
            }
        }, 0, 30000); // 30 seconds in milliseconds
    }
}
