package Domain;

import java.util.Random;

public class Gift {
    private SpellType spellType;
    private int x, y; // Position of the gift
    private int fallingSpeed = 5; // Speed at which the gift falls

    public Gift(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.spellType = getRandomSpellType();
    }

    private SpellType getRandomSpellType() {
        Random random = new Random();
        return SpellType.values()[random.nextInt(SpellType.values().length)];
    }

    public void updatePosition() {
        this.y += fallingSpeed;
    }

    public SpellType getSpellType() {
        return spellType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

