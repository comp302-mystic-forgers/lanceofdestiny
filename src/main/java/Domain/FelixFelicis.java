package Domain;
public class FelixFelicis extends Spell {

    private int luckFactor;
    private boolean activated;

    public FelixFelicis() {
        this.activated = false;
        this.luckFactor = 1;
    }

    @Override
    public void activate() {
        activated = true;
        System.out.println("Felix Felicis activated: Luck increased by " + luckFactor);
    }

    @Override
    public void deactivate() {
        activated = false;
        System.out.println("Felix Felicis deactivated: Luck normalized");

    }

    public int getLuckFactor() {
        return luckFactor;
    }

    public void setLuckFactor(int luckFactor) {
        this.luckFactor = luckFactor;
    }

    public boolean isActivated() {
        return activated;
    }

}


