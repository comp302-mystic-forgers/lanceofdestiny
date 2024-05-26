package Domain;
import java.util.Timer;
import java.util.TimerTask;

public class Ymir {
    private DoubleAccel doubleAccel;
    private Timer timer;

    public Ymir(FireBall fireball) {
        this.doubleAccel = new DoubleAccel(fireball);
        this.timer = new Timer();
        scheduleSpellActivation();
    }

    private void scheduleSpellActivation() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                doubleAccel.activate();
            }
        }, 0, 30000); // 30 seconds in milliseconds
    }
}
