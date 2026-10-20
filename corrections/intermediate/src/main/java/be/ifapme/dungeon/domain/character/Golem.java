package be.ifapme.dungeon.domain.character;

import be.ifapme.dungeon.domain.behavior.HalvingProtection;
import be.ifapme.dungeon.domain.behavior.Regeneration;
import be.ifapme.dungeon.domain.loot.NoDrop;

public class Golem extends Character {
    public Golem() {
        super("Golem", 70, 12, 4, new HalvingProtection(), new Regeneration(3), new NoDrop());
    }

    @Override
    public String getBattleCry() {
        return "Grrrooonnnk.";
    }
}
