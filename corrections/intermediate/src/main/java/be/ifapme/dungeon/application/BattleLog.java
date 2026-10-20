package be.ifapme.dungeon.application;

import be.ifapme.dungeon.domain.character.Character;
import be.ifapme.dungeon.domain.character.Hero;
import be.ifapme.dungeon.domain.loot.Chest;
import be.ifapme.dungeon.domain.loot.Loot;

public interface BattleLog {
    void heroEntered(Hero hero);

    void monsterAppeared(Character monster);

    void heroStruck(Hero hero, Character monster, int damage);

    void monsterRetaliated(Character monster, Hero hero, int damage);

    void regenerated(Character character, int amount);

    void monsterDefeated(Character monster);

    void lootCollected(Loot loot);

    void shieldPickedUp(Hero hero, String formerOwner);

    void chestReached(Chest chest);

    void chestStruck(Hero hero, Chest chest);

    void explorationEnded(int victories, int monsterCount, int gold);
}
