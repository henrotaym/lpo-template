package dungeon.combat;

public class NoProtection implements Protection {
    @Override
    public int reduce(int damage) {
        return damage;
    }
}
