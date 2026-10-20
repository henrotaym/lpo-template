package be.ifapme.dungeon.application;

import be.ifapme.dungeon.domain.behavior.Protection;
import be.ifapme.dungeon.domain.character.Hero;
import be.ifapme.dungeon.domain.loot.Loot;
import be.ifapme.dungeon.domain.loot.Looter;

class AnnouncedLooter implements Looter {
    private final Hero hero;
    private final BattleLog log;
    private final String source;

    AnnouncedLooter(Hero hero, BattleLog log, String source) {
        this.hero = hero;
        this.log = log;
        this.source = source;
    }

    @Override
    public void collect(Loot loot) {
        this.hero.collect(loot);
        this.log.lootCollected(loot);
    }

    @Override
    public void equip(Protection shield) {
        this.hero.equip(shield);
        this.log.shieldPickedUp(this.hero, this.source);
    }
}
