public class Chest implements Lootable {
    private String name;
    private int durability;
    private Drop drop;

    public Chest(String name, int durability, Drop drop) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }

        if (durability <= 0) {
            throw new IllegalArgumentException("Durability cannot be less than 0.");
        }

        if (drop == null) {
            throw new IllegalArgumentException("Drop cannot be null.");
        }

        this.name = name;
        this.durability = durability;
        this.drop = drop;
    }

    public String getName() {
        return this.name;
    }

    public int getDurability() {
        return this.durability;
    }

    public Drop getDrop() {
        return this.drop;
    }

    public void takeDamage(int amount) {
        if (amount <= 0) {
            return;
        }

        this.durability = Math.max(0, this.durability - amount);
    }

    public boolean isOpen() {
        return this.durability <= 0;
    }

    @Override
    public String toString() {
        return this.name + " [" + this.durability + " de solidité]";
    }
}
