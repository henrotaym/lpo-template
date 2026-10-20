package dungeon.character;

public interface Regenerating {
    int regenerationRate();

    void heal(int amount);

    default void regenerate() {
        this.heal(this.regenerationRate());
    }
}
