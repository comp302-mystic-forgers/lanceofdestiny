package Domain;

public enum SpellType {
    FELIX_FELICIS("Felix Felicis"),
    MAGICAL_STAFF_EXPANSION("Magical Staff Expansion"),
    HEX("Hex"),
    OVERWHELMING_FIRE_BALL("Overwhelming Fire Ball");

    private final String name;

    SpellType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
