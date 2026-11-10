public class LootDrop implements Drop {
    private Loot loot;

    public LootDrop(Loot loot) {
        if (loot == null) {
            throw new IllegalArgumentException("Loot value cannot be null.");
        }

        this.loot = loot;
    }

    public void giveTo(Looter looter) {
        looter.collect(this.loot);
    }
}
