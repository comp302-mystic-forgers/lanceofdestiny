package src.main.java.Domain;

public class OverwhelmingFireBall extends Spell{
    private FireBall fireBall;
    private boolean activated;
    private long activationTime;

    public OverwhelmingFireBall(FireBall fireBall) {
        this.fireBall = fireBall;
    }

    public long getTime(){
        return activationTime;
    }

    @Override
    public void activate() {
        if (!activated) {
            activated = true;
            activationTime = System.currentTimeMillis();
        }
    }

    @Override
    public void deactivate() {
        activated = false;
    }


    public boolean isActivated() {
        return activated;
    }


    public void handleCollisionResponse(Barrier barrier) {
        if (barrier != null && activated) {
            barrier.destroy(); // Destroy the barrier
        }
    }
}

