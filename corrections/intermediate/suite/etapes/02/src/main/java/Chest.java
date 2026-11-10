public class Chest implements Lootable {
    private String name;
    private int durability;
    private Drop drop;

    public Chest(String name, int durability, Drop drop) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }

        if (durability <= 0) {
            throw new IllegalArgumentException("Durability value cannot be less than 1.");
        }

        if (drop == null) {
            throw new IllegalArgumentException("Drop value cannot be null.");
        }

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
