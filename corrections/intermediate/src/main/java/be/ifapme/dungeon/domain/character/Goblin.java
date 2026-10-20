package be.ifapme.dungeon.domain.character;

import be.ifapme.dungeon.domain.behavior.NoEffect;
import be.ifapme.dungeon.domain.behavior.NoProtection;
import be.ifapme.dungeon.domain.loot.Loot;
import be.ifapme.dungeon.domain.loot.LootDrop;

public class Goblin extends Character {
    public Goblin() {
        super("Gobelin", 40, 7, 2, new NoProtection(), new NoEffect(), new LootDrop(new Loot("Dague ébréchée", 15)));
    }

    @Override
    public String getBattleCry() {
        return "Mon trésor, à moi !";
    }
}
