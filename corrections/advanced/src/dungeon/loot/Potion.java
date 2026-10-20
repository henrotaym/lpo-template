package dungeon.loot;

public record Potion(String name, int healing, int goldValue) implements Valuable {
    private static final int SMALL_HEALING = 40;
    private static final int SMALL_VALUE = 20;

    public Potion {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Une potion doit avoir un nom.");
        }
        if (healing <= 0) {
            throw new IllegalArgumentException("Une potion doit soigner au moins 1 PV.");
        }
        if (goldValue < 0) {
            throw new IllegalArgumentException("La valeur d'une potion ne peut pas être négative.");
        }
    }

    public static Potion small() {
        return new Potion("Potion de soin", SMALL_HEALING, SMALL_VALUE);
    }

    @Override
    public String toString() {
        return this.name + " (+" + this.healing + " PV)";
    }
}
