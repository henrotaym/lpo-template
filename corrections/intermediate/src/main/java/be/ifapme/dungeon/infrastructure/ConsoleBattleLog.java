package be.ifapme.dungeon.infrastructure;

import be.ifapme.dungeon.application.BattleLog;
import be.ifapme.dungeon.domain.character.Character;
import be.ifapme.dungeon.domain.character.Hero;
import be.ifapme.dungeon.domain.loot.Chest;
import be.ifapme.dungeon.domain.loot.Loot;

public class ConsoleBattleLog implements BattleLog {
    @Override
    public void heroEntered(Hero hero) {
        IO.println(hero + " entre dans le donjon.");
    }

    @Override
    public void monsterAppeared(Character monster) {
        IO.println("");
        IO.println("--- " + monster + " apparaît ---");
        IO.println(monster.getName() + " : " + monster.getBattleCry());
    }

    @Override
    public void heroStruck(Hero hero, Character monster, int damage) {
        IO.println(hero.getName() + " frappe " + monster.getName() + " pour " + damage + " dégâts. " + monster);
    }

    @Override
    public void monsterRetaliated(Character monster, Hero hero, int damage) {
        IO.println(monster.getName() + " riposte pour " + damage + " dégâts. " + hero);
    }

    @Override
    public void regenerated(Character character, int amount) {
        IO.println(character.getName() + " régénère " + amount + " PV. " + character);
    }

    @Override
    public void monsterDefeated(Character monster) {
        IO.println(monster.getName() + " est vaincu !");
    }

    @Override
    public void lootCollected(Loot loot) {
        IO.println("Butin : " + loot);
    }

    @Override
    public void shieldPickedUp(Hero hero, String formerOwner) {
        IO.println(hero.getName() + " ramasse le bouclier de " + formerOwner + ".");
    }

    @Override
    public void chestReached(Chest chest) {
        IO.println("");
        IO.println("--- " + chest + " ---");
    }

    @Override
    public void chestStruck(Hero hero, Chest chest) {
        IO.println(hero.getName() + " frappe le coffre. " + chest);
    }

    @Override
    public void explorationEnded(int victories, int monsterCount, int gold) {
        IO.println("");
        IO.println("=== Fin de l'exploration ===");
        IO.println("Monstres vaincus : " + victories + " / " + monsterCount);
        IO.println("Or récolté : " + gold + " po");
    }
}
