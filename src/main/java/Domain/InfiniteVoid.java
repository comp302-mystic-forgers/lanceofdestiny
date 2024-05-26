package Domain;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;

public class InfiniteVoid extends Spell{
    private ArrayList<Barrier> barriers;
    private Timer timer;
    private ArrayList<Barrier> frozenBarriers;

    public InfiniteVoid(ArrayList<Barrier> barriers) {
        super("Infinite Void", "Freezes 8 barriers for 15 seconds");
        this.barriers = barriers;
        this.timer = new Timer();
        this.frozenBarriers = new ArrayList<>();
    }

    @Override
    public void activate() {
        if (!isActivated()) {
            setActivated(true);
            frozenBarriers.clear();

            ArrayList<Barrier> unfrozenBarriers = new ArrayList<>();
            for (Barrier barrier : barriers) {
                if (!barrier.isDestroyed()) {
                    unfrozenBarriers.add(barrier);
                }
            }

            Collections.shuffle(unfrozenBarriers);
            int barriersToFreeze = Math.min(8, unfrozenBarriers.size());
            for (int i = 0; i < barriersToFreeze; i++) {
                frozenBarriers.add(unfrozenBarriers.get(i));
            }

            for (Barrier barrier : frozenBarriers) {
                barrier.setFrozen(true);
            }

            // Schedule deactivation after 15 seconds
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    for (Barrier barrier : frozenBarriers) {
                        barrier.setFrozen(false);
                    }
                    setActivated(false);
                }
            }, 15000); // 15 seconds in milliseconds
        }
    }

    public ArrayList<Barrier> getFrozenBarriers() {
        return frozenBarriers;
    }
}
