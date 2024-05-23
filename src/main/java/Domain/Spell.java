package Domain;

import java.awt.*;
import java.util.UUID;

public abstract class Spell {

    private UUID spellId;
    private String name;
    private String description;
    private boolean activated;

    public Spell() {
        this.spellId = UUID.randomUUID();
    }
    public Spell(String name, String description) {
        this.name = name;
        this.description = description;
        this.activated = false;
        this.spellId = UUID.randomUUID();
    }
    public abstract void activate();
    public UUID getSpellId() {
        return spellId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
