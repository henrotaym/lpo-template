package be.ifapme.dungeon.domain.behavior;

public class NoProtection implements Protection {
    @Override
    public int reduce(int damage) {
        return damage;
    }
}
