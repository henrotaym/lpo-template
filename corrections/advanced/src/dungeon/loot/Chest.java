package dungeon.loot;

import dungeon.combat.Damageable;

public class Chest implements Damageable, Lootable {
    private final String name;
    private final Loot content;
    private int durability;

    public Chest(String name, int durability, Loot content) {
        this.name = name;
        this.durability = durability;
        this.content = content;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void takeDamage(int amount) {
        if (amount <= 0) {
            return;
        }
        this.durability = Math.max(0, this.durability - amount);
    }

    @Override
    public boolean isAlive() {
        return this.durability > 0;
    }

    @Override
    public Loot dropLoot() {
        return this.content;
    }

    @Override
    public String toString() {
        return this.name + " [" + this.durability + " de solidité]";
    }
}
