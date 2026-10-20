package be.ifapme.dungeon.domain.loot;

public class Loot {
    private final String name;
    private final int goldValue;

    public Loot(String name, int goldValue) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Un butin doit avoir un nom.");
        }
        if (goldValue < 0) {
            throw new IllegalArgumentException("La valeur d'un butin ne peut pas être négative.");
        }
        this.name = name;
        this.goldValue = goldValue;
    }

    public String getName() {
        return this.name;
    }

    public int getGoldValue() {
        return this.goldValue;
    }

    @Override
    public String toString() {
        return this.name + " (" + this.goldValue + " po)";
    }
}
