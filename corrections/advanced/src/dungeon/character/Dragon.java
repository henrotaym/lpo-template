package dungeon.character;

import dungeon.combat.Element;
import dungeon.combat.NoProtection;
import dungeon.loot.Loot;
import dungeon.loot.Lootable;
import dungeon.loot.Rarity;

public class Dragon extends Character implements Regenerating, Flying, Lootable {
    private static final int REGENERATION = 8;

    public Dragon() {
        super("Dragon", 120, 20, 8, Element.FIRE, new NoProtection());
    }

    @Override
    public String getBattleCry() {
        return "Brûlez, misérables !";
    }

    @Override
    public int regenerationRate() {
        return REGENERATION;
    }

    @Override
    public int dodgeChance() {
        return 15;
    }

    @Override
    public Loot dropLoot() {
        return new Loot("Écaille de dragon", 500, Rarity.LEGENDARY);
    }

    @Override
    public void endOfTurn() {
        if (!this.isAlive() || this.getHealth() == this.getMaxHealth()) {
            return;
        }
        this.regenerate();
        IO.println("Le Dragon régénère " + REGENERATION + " PV. " + this);
    }
}
