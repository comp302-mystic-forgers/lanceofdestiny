package Domain;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

public class Ymir {
    private static final int SPELL_INTERVAL = 30000; // 30 seconds
    private static final int SPELL_DURATION = 15000; // 15 seconds
    private GameView gameView;
    private List<String> abilities;
    private Deque<String> lastAbilities;
    private Random random;
    private ScheduledExecutorService scheduler;

    public Ymir(GameView gameView) {
        this.gameView = gameView;
        this.abilities = Arrays.asList("Infinite Void", "Double Accel", "Hollow Purple");
        this.lastAbilities = new LinkedList<>();
        this.random = new Random();
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.lastAbilities.add(abilities.get(random.nextInt(abilities.size())));
        this.lastAbilities.add(abilities.get(random.nextInt(abilities.size())));
        scheduleNextSpell();
    }

    public void scheduleNextSpell() {
        scheduler.schedule((Runnable) this::activateSpell, SPELL_INTERVAL, TimeUnit.MILLISECONDS);
    }

    public void activateSpell() {
        if (random.nextBoolean()) {
            String spell = selectRandomSpell();
            activateSpell(spell);
            lastAbilities.pollFirst();
            lastAbilities.addLast(spell);
        }
        scheduleNextSpell();
    }

    public String selectRandomSpell() {
        String spell;
        do {
            spell = abilities.get(random.nextInt(abilities.size()));
        } while (Collections.frequency(lastAbilities, spell) == 2);
        return spell;
    }

    public void activateSpell(String spell) {
        System.out.println("Activating spell: " + spell);

        switch (spell) {
            case "Infinite Void":
                activateInfiniteVoid();
                break;
            case "Double Accel":
                activateDoubleAccel();
                break;
            case "Hollow Purple":
                activateHollowPurple();
                break;
        }


    }

    public void activateInfiniteVoid() {
        List<Barrier> barriers = gameView.getAllBarriers();
        Collections.shuffle(barriers);
        List<Barrier> frozenBarriers = barriers.subList(0, Math.min(8, barriers.size()));
        System.out.println("Activating Infinite Void. Freezing the following barriers:");
        frozenBarriers.forEach(barrier -> {
            barrier.freeze(SPELL_DURATION);

            System.out.println("Frozen Barrier ID: " + barrier.getBarrierId() + ", Position: (" + barrier.getX() + ", " + barrier.getY() + "), Duration: " + SPELL_DURATION + " ms");
        });
    }



    public void activateDoubleAccel() {
        FireBall fireball = gameView.getFireball();
        System.out.println("Activating DoubleAccel");
        System.out.println("Initial velocity: xVelocity=" + fireball.xVelocity + ", yVelocity=" + fireball.yVelocity);

        fireball.setSpeedMultiplier(0.5); // Hızı yarıya indir
        System.out.println("After DoubleAccel: xVelocity=" + fireball.xVelocity + ", yVelocity=" + fireball.yVelocity);

        scheduler.schedule(() -> {
            fireball.setSpeedMultiplier(2.0); // Hızı eski haline getir
            System.out.println("After restoring speed: xVelocity=" + fireball.xVelocity + ", yVelocity=" + fireball.yVelocity);
        }, SPELL_DURATION, TimeUnit.MILLISECONDS);
    }



    public void activateHollowPurple() {
        System.out.println("Activating Hollow Purple");
        List<Barrier> existingBarriers = gameView.getAllBarriers();
        Rectangle magicalStaffBounds = gameView.getMagicalStaffBounds();

        for (int i = 0; i < 8; i++) {
            int x, y;
            boolean validPosition;
            do {
                int maxX = gameView.getWidth() - 60;
                int maxY = magicalStaffBounds.y - 60;

                // Ensure bounds are positive
                if (maxX <= 0 || maxY <= 0) {
                    System.err.println("Invalid dimensions for placing Hollow Purple barriers");
                    return;
                }

                x = random.nextInt(maxX);
                y = random.nextInt(maxY); // Ensure it is above the MagicalStaff
                validPosition = true;

                Rectangle newBarrierBounds = new Rectangle(x, y, 60, 60);
                // Check if the new barrier intersects with any existing barriers
                for (Barrier barrier : existingBarriers) {
                    if (newBarrierBounds.intersects(barrier.getBounds())) {
                        validPosition = false;
                        break;
                    }
                }
            } while (!validPosition);

            gameView.addHollowPurpleBarrier(new HollowPurpleBarrier(x, y, 60, 60));
            System.out.println("Hollow Purple Barrier placed at: x=" + x + ", y=" + y);
        }
        System.out.println("Finished placing Hollow Purple barriers.");
    }




}

