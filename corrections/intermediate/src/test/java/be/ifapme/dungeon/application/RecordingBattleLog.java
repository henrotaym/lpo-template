package be.ifapme.dungeon.application;

import java.util.ArrayList;
import java.util.List;

import be.ifapme.dungeon.domain.character.Character;
import be.ifapme.dungeon.domain.character.Hero;
import be.ifapme.dungeon.domain.loot.Chest;
import be.ifapme.dungeon.domain.loot.Loot;

class RecordingBattleLog implements BattleLog {
    private final List<String> events = new ArrayList<>();

    @Override
    public void heroEntered(Hero hero) {
        this.events.add("entered " + hero.getName());
    }

    @Override
    public void monsterAppeared(Character monster) {
        this.events.add("appeared " + monster.getName());
    }

    @Override
    public void heroStruck(Hero hero, Character monster, int damage) {
        this.events.add("struck " + monster.getName() + " " + damage);
    }

    @Override
    public void monsterRetaliated(Character monster, Hero hero, int damage) {
        this.events.add("retaliated " + monster.getName() + " " + damage);
    }

    @Override
    public void regenerated(Character character, int amount) {
        this.events.add("regenerated " + character.getName() + " " + amount);
    }

    @Override
    public void monsterDefeated(Character monster) {
        this.events.add("defeated " + monster.getName());
    }

    @Override
    public void lootCollected(Loot loot) {
        this.events.add("loot " + loot.getName());
    }

    @Override
    public void shieldPickedUp(Hero hero, String formerOwner) {
        this.events.add("shield from " + formerOwner);
    }

    @Override
    public void chestReached(Chest chest) {
        this.events.add("chest reached");
    }

    @Override
    public void chestStruck(Hero hero, Chest chest) {
        this.events.add("chest struck");
    }

    @Override
    public void explorationEnded(int victories, int monsterCount, int gold) {
        this.events.add("ended " + victories + "/" + monsterCount + " " + gold);
    }

    List<String> getEvents() {
        return this.events;
    }
}
