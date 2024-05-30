package Domain;

import java.util.Timer;
import java.util.TimerTask;

public class DoubleAccel extends Spell{
    private FireBall fireball;
    private Timer timer;

    public DoubleAccel(FireBall fireball) {
        super(SpellType.valueOf("Double Accel"), "Reduces fireball speed by half for 15 seconds");
        this.fireball = fireball;
        this.timer = new Timer();
    }

    @Override
    public void activate() {
        if (!isActivated()) {
            setActivated(true);
            double originalXVelocity = fireball.xVelocity;
            double originalYVelocity = fireball.yVelocity;

            fireball.setVelocity(fireball.xVelocity / 2, fireball.yVelocity / 2);

            // Schedule deactivation after 15 seconds
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    fireball.setVelocity(originalXVelocity, originalYVelocity);
                    setActivated(false);
                }
            }, 15000); // 15 seconds in milliseconds
        }
    }
}
