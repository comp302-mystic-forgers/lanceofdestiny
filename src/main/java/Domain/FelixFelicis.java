package src.main.java.Domain;
public class FelixFelicis extends Spell {

    private int luckFactor;

    public FelixFelicis() {
        this.luckFactor = 1;
    }

    @Override
    public void activate() {
        System.out.println("Felix Felicis activated: Luck increased by " + luckFactor);
    }

    @Override
    public void deactivate() {
        System.out.println("Felix Felicis deactivated: Luck normalized");
    }

    public int getLuckFactor() {
        return luckFactor;
    }

    public void setLuckFactor(int luckFactor) {
        this.luckFactor = luckFactor;
    }
}

