package dungeon.combat;

public class HalvingProtection implements Protection {
    private static final int MINIMUM_DAMAGE = 1;

    @Override
    public int reduce(int damage) {
        return Math.max(MINIMUM_DAMAGE, damage / 2);
    }
}
