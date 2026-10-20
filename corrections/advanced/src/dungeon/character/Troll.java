package dungeon.character;

import dungeon.combat.Element;
import dungeon.combat.NoProtection;
import dungeon.loot.Loot;
import dungeon.loot.Lootable;
import dungeon.loot.Rarity;

public class Troll extends Character implements Regenerating, Lootable {
    private static final int REGENERATION = 5;

    public Troll() {
        super("Troll", 80, 15, 5, Element.POISON, new NoProtection());
    }

    @Override
    public String getBattleCry() {
        return "Troll écraser !";
    }

    @Override
    public Loot dropLoot() {
        return new Loot("Massue de troll", 80, Rarity.RARE);
    }

    @Override
    public int regenerationRate() {
        return REGENERATION;
    }

    @Override
    public void endOfTurn() {
        if (!this.isAlive() || this.getHealth() == this.getMaxHealth()) {
            return;
        }
        this.regenerate();
        IO.println("Le Troll régénère " + REGENERATION + " PV. " + this);
    }
}
