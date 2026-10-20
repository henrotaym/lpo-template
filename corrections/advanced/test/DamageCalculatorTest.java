import static org.junit.jupiter.api.Assertions.assertEquals;

import dungeon.character.Ghost;
import dungeon.character.Goblin;
import dungeon.character.Skeleton;
import dungeon.character.Troll;
import dungeon.combat.Attack;
import dungeon.combat.DamageCalculator;
import dungeon.combat.Element;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DamageCalculatorTest {

    private final DamageCalculator calculator = new DamageCalculator();

    @Test
    @DisplayName("Les dégâts valent la puissance moins la défense")
    void normalHitSubtractsDefenseFromPower() {
        assertEquals(10, this.calculator.computeDamage(new Attack(Element.PHYSICAL, 12, false), new Goblin()));
    }

    @Test
    @DisplayName("Un coup critique double la puissance avant la défense")
    void criticalHitDoublesPowerBeforeDefense() {
        assertEquals(22, this.calculator.computeDamage(new Attack(Element.PHYSICAL, 12, true), new Goblin()));
    }

    @Test
    @DisplayName("L'avantage élémentaire multiplie les dégâts par 1,5")
    void elementalAdvantageIncreasesDamage() {
        assertEquals(29, this.calculator.computeDamage(new Attack(Element.FIRE, 20, false), new Ghost()));
    }

    @Test
    @DisplayName("Sans avantage élémentaire, les dégâts ne sont pas majorés")
    void neutralElementDoesNotIncreaseDamage() {
        assertEquals(19, this.calculator.computeDamage(new Attack(Element.PHYSICAL, 20, false), new Ghost()));
    }

    @Test
    @DisplayName("Une défense supérieure à la puissance laisse le plancher de 1")
    void defenseHigherThanPowerStillDealsMinimumDamage() {
        assertEquals(1, this.calculator.computeDamage(new Attack(Element.PHYSICAL, 1, false), new Troll()));
    }

    @Test
    @DisplayName("Le squelette n'encaisse que la moitié des dégâts")
    void skeletonHalvesIncomingDamage() {
        assertEquals(5, this.calculator.computeDamage(new Attack(Element.PHYSICAL, 13, false), new Skeleton()));
    }
}
