package be.ifapme.dungeon.domain.character;

import be.ifapme.dungeon.domain.behavior.NoEffect;
import be.ifapme.dungeon.domain.behavior.NoProtection;
import be.ifapme.dungeon.domain.behavior.Protection;
import be.ifapme.dungeon.domain.loot.Loot;
import be.ifapme.dungeon.domain.loot.Looter;
import be.ifapme.dungeon.domain.loot.NoDrop;

public class Hero extends Character implements Looter {
    private int victories;
    private int gold;

    public Hero(String name, int maxHealth, int attackPower, int defense) {
        super(name, maxHealth, attackPower, defense, new NoProtection(), new NoEffect(), new NoDrop());
    }

    @Override
    public String getBattleCry() {
        return "Pour la lumière !";
    }

    public void recordVictory() {
        this.victories++;
    }

    public int getVictories() {
        return this.victories;
    }

    public int getGold() {
        return this.gold;
    }

    @Override
    public void collect(Loot loot) {
        this.gold += loot.getGoldValue();
    }

    @Override
    public void equip(Protection shield) {
        this.changeProtection(shield);
    }
}
