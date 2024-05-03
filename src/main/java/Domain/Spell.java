package Domain;

import java.awt.*;
import java.util.UUID;

public abstract class Spell {

    private UUID spellId;

    public Spell() {
        this.spellId = UUID.randomUUID();
    }

    public abstract void activate();
    public abstract void deactivate();


    public UUID getSpellId() {
        return spellId;
    }
}
