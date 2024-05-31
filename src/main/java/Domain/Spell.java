package Domain;

import java.awt.*;
import java.util.UUID;

public abstract class Spell {
    private String description;
    private boolean activated;
    private SpellType spellType;
    private long count;

    public Spell(SpellType spellType, String description, long count) {
        this.spellType = spellType;
        this.description = description;
        this.activated = false;
        this.count = count;
    }
    public abstract void activate();

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

    public long getCount() {
        return count;
    }
}
