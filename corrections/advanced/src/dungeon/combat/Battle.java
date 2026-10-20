package dungeon.combat;

import dungeon.character.Character;
import dungeon.character.Flying;
import dungeon.character.Hero;
import dungeon.loot.Chest;
import dungeon.loot.Loot;
import dungeon.loot.Lootable;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Battle {
    public static final int CRITICAL_CHANCE = 20;

    private final Hero hero;
    private final DamageCalculator calculator;
    private final Random random;

    public Battle(Hero hero, DamageCalculator calculator, Random random) {
        this.hero = hero;
        this.calculator = calculator;
        this.random = random;
    }

    public void explore(List<Character> monsters) {
        IO.println(this.hero + " entre dans le donjon.");
        IO.println(this.hero.getName() + " : « " + this.hero.getBattleCry() + " »");

        for (Character monster : monsters) {
            if (!this.hero.isAlive()) {
                return;
            }
            this.fight(monster);
        }
    }

    public void open(Chest chest) {
        if (!this.hero.isAlive()) {
            return;
        }

        IO.println("");
        IO.println("--- " + chest + " ---");
        while (chest.isAlive()) {
            chest.takeDamage(this.hero.attack(false).power());
            IO.println(this.hero.getName() + " frappe le coffre. " + chest);
        }
        this.collect(chest);
    }

    public void report(int monsterCount) {
        List<Character> defeated = this.hero.getDefeated();
        defeated.sort((first, second) -> first.getName().compareTo(second.getName()));

        IO.println("");
        IO.println("=== Fin de l'exploration ===");
        IO.println("Monstres vaincus : " + defeated.size() + " / " + monsterCount);
        IO.println("Par ordre alphabétique : " + String.join(", ", defeated.stream().map(Character::getName).toList()));
        IO.println("Or récolté : " + this.hero.getLoot().totalValue() + " po");
        IO.println("Potions restantes : " + this.hero.getPotions().size()
                + " (" + this.hero.getPotions().totalValue() + " po)");
    }

    private void fight(Character monster) {
        IO.println("");
        IO.println("--- " + monster + " apparaît ---");
        IO.println(monster.getName() + " : « " + monster.getBattleCry() + " »");

        if (monster instanceof Flying flying) {
            IO.println(monster.getName() + " " + flying.movementDescription()
                    + " (esquive " + flying.dodgeChance() + " %).");
        }

        while (monster.isAlive() && this.hero.isAlive()) {
            new Round(monster).play();
        }

        if (monster.isAlive()) {
            return;
        }

        IO.println(monster.getName() + " est vaincu !");
        this.hero.recordVictory(monster);
        if (monster instanceof Lootable lootable) {
            this.collect(lootable);
        }
    }

    private void collect(Lootable source) {
        Loot loot = source.dropLoot();
        this.hero.store(loot);
        IO.println("Butin : " + loot + " - " + loot.rarity().describe());
    }

    private class Round {
        private final Character monster;

        private Round(Character monster) {
            this.monster = monster;
        }

        private void play() {
            this.heroStrikes();
            if (this.monster.isAlive()) {
                this.monsterStrikesBack();
            }
            this.monster.endOfTurn();
            hero.endOfTurn();
        }

        private void heroStrikes() {
            Attack attack = hero.attack(random.nextInt(100) < CRITICAL_CHANCE);
            int damage = calculator.computeDamage(attack, this.monster);
            double multiplier = attack.element().multiplierAgainst(this.monster.getElement());
            this.monster.takeDamage(damage);

            String critical = "";
            if (attack.critical()) {
                critical = " (critique !)";
            }
            IO.println(hero.getName() + " frappe " + this.monster.getName()
                    + " - " + attack.element().getLabel() + " x" + multiplier
                    + " - " + damage + " dégâts" + critical);
            IO.println("  " + this.monster);
        }

        private void monsterStrikesBack() {
            int damage = calculator.computeDamage(this.monster.attack(false), hero);
            hero.takeDamage(damage);
            IO.println("  " + this.monster.getName() + " riposte pour " + damage + " dégâts. " + hero);
        }
    }
}
