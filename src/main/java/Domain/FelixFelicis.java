package Domain;
public class FelixFelicis extends Spell {

    private PlayerAccount player;

    public FelixFelicis(PlayerAccount player) {
        super("Felix Felicis", "Increases the player's chances by 1.");
        this.player = player;
    }
    public FelixFelicis(String name, String description) {
        super(name, description);
    }
    @Override
    public void activate() {
        player.increaseChances();
        System.out.println("Felix Felicis is activated");
    }
}


