package dungeon.character;

import dungeon.combat.Element;
import dungeon.combat.NoProtection;
import dungeon.loot.Loot;
import dungeon.loot.Lootable;
import dungeon.loot.Rarity;

public class Goblin extends Character implements Lootable {
    public Goblin() {
        super("Gobelin", 40, 7, 2, Element.PHYSICAL, new NoProtection());
    }

    @Override
    public String getBattleCry() {
        return "Ma dague a soif !";
    }

    @Override
    public Loot dropLoot() {
        return new Loot("Dague ébréchée", 15, Rarity.COMMON);
    }
}
