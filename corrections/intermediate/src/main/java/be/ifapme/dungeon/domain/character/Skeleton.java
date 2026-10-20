package be.ifapme.dungeon.domain.character;

import be.ifapme.dungeon.domain.behavior.ArmorProtection;
import be.ifapme.dungeon.domain.behavior.HalvingProtection;
import be.ifapme.dungeon.domain.behavior.NoEffect;
import be.ifapme.dungeon.domain.loot.ShieldDrop;

public class Skeleton extends Character {
    public Skeleton() {
        super("Squelette", 55, 9, 3, new HalvingProtection(), new NoEffect(), new ShieldDrop(new ArmorProtection(2)));
    }

    @Override
    public String getBattleCry() {
        return "Clac, clac, clac...";
    }
}
