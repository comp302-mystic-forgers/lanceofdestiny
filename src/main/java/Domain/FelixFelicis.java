package Domain;
public class FelixFelicis extends Spell {

    private PlayerAccount player;

    public FelixFelicis(PlayerAccount player) {
        super(SpellType.FELIX_FELICIS, "Increases the player's chances by 1.");
        this.player = player;
    }
    public FelixFelicis(SpellType spellType, String description) {
        super(SpellType.FELIX_FELICIS, description);
    }
    @Override
    public void activate() {
        player.increaseChances();
        System.out.println("Felix Felicis is activated");
    }
}


