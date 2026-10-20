package dungeon.loot;

public record Loot(String name, int baseGoldValue, Rarity rarity) implements Valuable {
    public Loot {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Un butin doit avoir un nom.");
        }
        if (baseGoldValue < 0) {
            throw new IllegalArgumentException("La valeur d'un butin ne peut pas être négative.");
        }
        if (rarity == null) {
            throw new IllegalArgumentException("Un butin doit avoir une rareté.");
        }
    }

    @Override
    public int goldValue() {
        return this.rarity.applyTo(this.baseGoldValue);
    }

    @Override
    public String toString() {
        return this.name + " (" + this.goldValue() + " po) " + this.rarity.getSymbol() + " " + this.rarity.getLabel();
    }
}
