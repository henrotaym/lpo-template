package be.ifapme.dungeon.domain.behavior;

public class ArmorProtection implements Protection {
    private static final int MINIMUM_DAMAGE = 1;

    private final int armor;

    public ArmorProtection(int armor) {
        if (armor < 0) {
            throw new IllegalArgumentException("Une armure ne peut pas être négative.");
        }
        this.armor = armor;
    }

    @Override
    public int reduce(int damage) {
        return Math.max(MINIMUM_DAMAGE, damage - this.armor);
    }
}
