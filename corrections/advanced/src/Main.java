import dungeon.character.Character;
import dungeon.character.Dragon;
import dungeon.character.Ghost;
import dungeon.character.Goblin;
import dungeon.character.Hero;
import dungeon.character.Skeleton;
import dungeon.character.Troll;
import dungeon.combat.Battle;
import dungeon.combat.DamageCalculator;
import dungeon.combat.Element;
import dungeon.loot.Chest;
import dungeon.loot.Loot;
import dungeon.loot.Potion;
import dungeon.loot.Rarity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Hero hero = new Hero("Aria", 220, 16, 4, Element.FIRE);
        hero.carry(Potion.small());
        hero.carry(Potion.small());

        List<Character> monsters = new ArrayList<>();
        monsters.add(new Goblin());
        monsters.add(new Skeleton());
        monsters.add(new Ghost());
        monsters.add(new Troll());
        monsters.add(new Dragon());
        monsters.sort(null);

        Chest chest = new Chest("Coffre du donjon", 30, new Loot("Couronne ancienne", 250, Rarity.EPIC));
        Battle battle = new Battle(hero, new DamageCalculator(), new Random(42));

        battle.explore(monsters);
        battle.open(chest);
        battle.report(monsters.size());
    }
}
