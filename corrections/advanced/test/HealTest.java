import static org.junit.jupiter.api.Assertions.assertEquals;

import dungeon.character.Hero;
import dungeon.combat.Element;
import dungeon.loot.Potion;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HealTest {

    @Test
    @DisplayName("On soigne avec un nombre de PV")
    void healWithAmount() {
        Hero hero = new Hero("Aria", 100, 10, 0, Element.FIRE);
        hero.takeDamage(50);
        hero.heal(20);

        assertEquals(70, hero.getHealth());
    }

    @Test
    @DisplayName("On soigne avec une potion")
    void healWithPotion() {
        Hero hero = new Hero("Aria", 100, 10, 0, Element.FIRE);
        hero.takeDamage(50);
        hero.heal(Potion.small());

        assertEquals(90, hero.getHealth());
    }

    @Test
    @DisplayName("Un soin ne dépasse jamais les PV maximum")
    void healNeverExceedsMaximum() {
        Hero hero = new Hero("Aria", 100, 10, 0, Element.FIRE);
        hero.takeDamage(10);
        hero.heal(Potion.small());

        assertEquals(100, hero.getHealth());
    }

    @Test
    @DisplayName("Aria boit une potion en fin de tour sous 30 % de ses PV")
    void heroDrinksWhenWounded() {
        Hero hero = new Hero("Aria", 100, 10, 0, Element.FIRE);
        hero.carry(Potion.small());
        hero.takeDamage(75);
        hero.endOfTurn();

        assertEquals(65, hero.getHealth());
        assertEquals(0, hero.getPotions().size());
    }
}
