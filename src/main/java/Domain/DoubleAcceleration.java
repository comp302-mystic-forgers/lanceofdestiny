package Domain;

import java.util.Timer;
import java.util.TimerTask;

public class DoubleAcceleration extends Spell {
    private FireBall fireball;

    public DoubleAcceleration(FireBall fireball) {
        super(SpellType.DOUBLE_ACCELERATION, "Reduces fireball speed by half for 15 seconds.",1);
        this.fireball = fireball;
    }

    @Override
    public void activate() {
        fireball.setSpeedMultiplier(0.5); // Fireball hızını yarıya düşür
        setActivated(true);
        System.out.println("Double Acceleration activated: Fireball speed reduced by half for 15 seconds.");

        // 15 saniye sonra orijinal hızı geri yükle
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                fireball.setSpeedMultiplier(1.0); // Fireball hızını orijinal haline döndür
                setActivated(false);
                System.out.println("Double Acceleration deactivated: Fireball speed restored.");
            }
        }, 15000); // 15 saniye
    }
}

