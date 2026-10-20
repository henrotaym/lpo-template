package be.ifapme.dungeon.domain.loot;

public class Chest implements Lootable {
    private final String name;
    private final Drop drop;
    private int durability;

    public Chest(String name, int durability, Drop drop) {
        this.name = name;
        this.durability = durability;
        this.drop = drop;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Drop getDrop() {
        return this.drop;
    }

    public boolean isOpen() {
        return this.durability == 0;
    }

    public void takeDamage(int amount) {
        if (amount <= 0) {
            return;
        }
        this.durability = Math.max(0, this.durability - amount);
    }

    @Override
    public String toString() {
        return this.name + " [" + this.durability + " de solidité]";
    }
}
