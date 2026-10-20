package be.ifapme.dungeon.domain.combat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import be.ifapme.dungeon.domain.behavior.ArmorProtection;
import be.ifapme.dungeon.domain.character.Goblin;
import be.ifapme.dungeon.domain.character.Golem;
import be.ifapme.dungeon.domain.character.Hero;
import be.ifapme.dungeon.domain.character.Skeleton;
import be.ifapme.dungeon.domain.character.Troll;

class DamageCalculatorTest {
    private final DamageCalculator calculator = new DamageCalculator();
    private final Hero aria = new Hero("Aria", 200, 20, 4);

    @Test
    void damageIsAttackMinusDefense() {
        assertEquals(18, this.calculator.computeDamage(this.aria, new Goblin()));
    }

    @Test
    void defenderProtectionAppliesAfterDefense() {
        assertEquals(8, this.calculator.computeDamage(this.aria, new Skeleton()));
    }

    @Test
    void shieldOfTheDefenderAppliesAfterDefense() {
        this.aria.changeProtection(new ArmorProtection(2));
        assertEquals(6, this.calculator.computeDamage(new Golem(), this.aria));
    }

    @Test
    void damageIsAtLeastOneBeforeProtection() {
        assertEquals(1, this.calculator.computeDamage(new Hero("Faible", 10, 1, 0), new Troll()));
    }

    @Test
    void damageIsAtLeastOneAfterProtection() {
        assertEquals(1, this.calculator.computeDamage(new Hero("Faible", 10, 1, 0), new Skeleton()));
    }
}
