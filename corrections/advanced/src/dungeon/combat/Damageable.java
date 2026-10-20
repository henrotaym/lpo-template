package dungeon.combat;

public interface Damageable {
    void takeDamage(int amount);

    boolean isAlive();

    String getName();
}
