package be.ifapme.dungeon.application;

import java.util.List;

import be.ifapme.dungeon.domain.character.Character;
import be.ifapme.dungeon.domain.character.Hero;
import be.ifapme.dungeon.domain.combat.DamageCalculator;
import be.ifapme.dungeon.domain.loot.Chest;
import be.ifapme.dungeon.domain.loot.Lootable;

public class Exploration {
    private final DamageCalculator calculator;
    private final BattleLog log;

    public Exploration(DamageCalculator calculator, BattleLog log) {
        this.calculator = calculator;
        this.log = log;
    }

    public void explore(Hero hero, List<Character> monsters, Chest chest) {
        this.log.heroEntered(hero);
        for (Character monster : monsters) {
            if (!hero.isAlive()) {
                break;
            }
            this.fight(hero, monster);
        }
        if (hero.isAlive()) {
            this.open(hero, chest);
        }
        this.log.explorationEnded(hero.getVictories(), monsters.size(), hero.getGold());
    }

    private void fight(Hero hero, Character monster) {
        this.log.monsterAppeared(monster);
        while (monster.isAlive() && hero.isAlive()) {
            this.playTurn(hero, monster);
        }
        if (!monster.isAlive()) {
            this.log.monsterDefeated(monster);
            hero.recordVictory();
            this.loot(hero, monster);
        }
    }

    private void playTurn(Hero hero, Character monster) {
        int damage = this.calculator.computeDamage(hero, monster);
        monster.takeDamage(damage);
        this.log.heroStruck(hero, monster, damage);

        if (monster.isAlive()) {
            int riposte = this.calculator.computeDamage(monster, hero);
            hero.takeDamage(riposte);
            this.log.monsterRetaliated(monster, hero, riposte);
        }

        int healthBeforeEffect = monster.getHealth();
        monster.endOfTurn();
        int regenerated = monster.getHealth() - healthBeforeEffect;
        if (regenerated > 0) {
            this.log.regenerated(monster, regenerated);
        }
    }

    private void open(Hero hero, Chest chest) {
        this.log.chestReached(chest);
        while (!chest.isOpen()) {
            chest.takeDamage(hero.getAttackPower());
            this.log.chestStruck(hero, chest);
        }
        this.loot(hero, chest);
    }

    private void loot(Hero hero, Lootable source) {
        source.getDrop().giveTo(new AnnouncedLooter(hero, this.log, source.getName()));
    }
}
