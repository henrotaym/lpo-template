package be.ifapme.dungeon.domain.loot;

public class LootDrop implements Drop {
    private final Loot loot;

    public LootDrop(Loot loot) {
        if (loot == null) {
            throw new IllegalArgumentException("Un butin à laisser ne peut pas être vide.");
        }
        this.loot = loot;
    }

    @Override
    public void giveTo(Looter looter) {
        looter.collect(this.loot);
    }
}
