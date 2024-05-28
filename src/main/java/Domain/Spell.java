package Domain;

import java.awt.*;
import java.util.UUID;

public abstract class Spell {

    private UUID spellId;
    private String description;
    private boolean activated;
    private SpellType spellType;

    public Spell() {
        this.spellId = UUID.randomUUID();
    }
    public Spell(SpellType spellType, String description) {
        this.spellType = spellType;
        this.description = description;
        this.activated = false;
        this.spellId = UUID.randomUUID();
    }
    public abstract void activate();
    public UUID getSpellId() {
        return spellId;
    }

    public String getName() {
        return this.spellType.getName();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
    public SpellType getSpellType() {
        return spellType;
    }
}
