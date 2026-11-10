public class LootDrop implements Drop {
    private Loot loot;

    public LootDrop(Loot loot) {
        if (loot == null) {
            throw new IllegalArgumentException("Loot value cannot be null.");
        }

        this.loot = loot;
    }

    public void giveTo(Looter hero, Lootable monster) {
        hero.collect(this.loot);
        IO.println("Butin : " + this.loot);
    }
}
