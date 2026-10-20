package be.ifapme.dungeon.domain.behavior;

public interface Healable {
    boolean isAlive();

    boolean isWounded();

    void heal(int amount);
}
