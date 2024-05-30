package Domain;

public enum SpellType {
    FELIX_FELICIS("Felix Felicis"),
    MAGICAL_STAFF_EXPANSION("Magical Staff Expansion"),
    HEX("Hex"),
    OVERWHELMING_FIRE_BALL("Overwhelming Fire Ball"),
    INFINITE_VOID("Infinite Void"),
    DOUBLE_ACCELERATION("Double Acceleration"),
    HOLLOW_PURPLE("Hollow Purple");
    private final String name;

    SpellType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
