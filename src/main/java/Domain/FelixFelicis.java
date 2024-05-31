package Domain;
public class FelixFelicis extends Spell {

    private PlayerAccount player;

    public FelixFelicis(PlayerAccount player) {
        super(SpellType.FELIX_FELICIS, "Increases the player's chances by 1.", 1);
        this.player = player;
    }

    @Override
    public void activate() {
        player.increaseChances();
        System.out.println("Felix Felicis is activated");
    }
}


