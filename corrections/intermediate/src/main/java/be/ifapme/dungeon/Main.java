package be.ifapme.dungeon;

import java.util.List;

import be.ifapme.dungeon.application.Exploration;
import be.ifapme.dungeon.domain.character.Character;
import be.ifapme.dungeon.domain.character.Goblin;
import be.ifapme.dungeon.domain.character.Golem;
import be.ifapme.dungeon.domain.character.Hero;
import be.ifapme.dungeon.domain.character.Skeleton;
import be.ifapme.dungeon.domain.character.Troll;
import be.ifapme.dungeon.domain.combat.DamageCalculator;
import be.ifapme.dungeon.domain.loot.Chest;
import be.ifapme.dungeon.domain.loot.Loot;
import be.ifapme.dungeon.domain.loot.LootDrop;
import be.ifapme.dungeon.infrastructure.ConsoleBattleLog;

public class Main {
    public static void main(String[] args) {
        Hero aria = new Hero("Aria", 200, 20, 4);
        List<Character> monsters = List.of(new Goblin(), new Skeleton(), new Troll(), new Golem());
        Chest chest = new Chest("Coffre du donjon", 30, new LootDrop(new Loot("Couronne ancienne", 250)));

        new Exploration(new DamageCalculator(), new ConsoleBattleLog()).explore(aria, monsters, chest);
    }
}
