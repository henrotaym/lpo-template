package be.ifapme.dungeon.domain.loot;

import be.ifapme.dungeon.domain.behavior.Protection;

public class ShieldDrop implements Drop {
    private final Protection shield;

    public ShieldDrop(Protection shield) {
        if (shield == null) {
            throw new IllegalArgumentException("Un bouclier à laisser ne peut pas être vide.");
        }
        this.shield = shield;
    }

    @Override
    public void giveTo(Looter looter) {
        looter.equip(this.shield);
    }
}
