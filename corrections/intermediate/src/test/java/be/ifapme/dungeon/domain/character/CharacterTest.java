package be.ifapme.dungeon.domain.character;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import be.ifapme.dungeon.domain.behavior.ArmorProtection;
import be.ifapme.dungeon.domain.behavior.NoEffect;
import be.ifapme.dungeon.domain.behavior.NoProtection;
import be.ifapme.dungeon.domain.behavior.Protection;
import be.ifapme.dungeon.domain.behavior.Regeneration;
import be.ifapme.dungeon.domain.behavior.TurnEffect;
import be.ifapme.dungeon.domain.loot.Drop;
import be.ifapme.dungeon.domain.loot.NoDrop;

class CharacterTest {
    private static Character create(String name, int maxHealth, int attackPower, int defense, Protection protection, TurnEffect turnEffect, Drop drop) {
        return new Character(name, maxHealth, attackPower, defense, protection, turnEffect, drop) {
            @Override
            public String getBattleCry() {
                return "Test !";
            }
        };
    }

    private static Character withHealth(int maxHealth) {
        return create("Cobaye", maxHealth, 5, 1, new NoProtection(), new NoEffect(), new NoDrop());
    }

    @Test
    void startsWithFullHealth() {
        Character character = withHealth(40);
        assertEquals(40, character.getHealth());
        assertTrue(character.isAlive());
        assertFalse(character.isWounded());
    }

    @Test
    void displaysNameHealthAndAttack() {
        Character character = withHealth(40);
        character.takeDamage(18);
        assertEquals("Cobaye [22/40 PV, ATK 5]", character.toString());
    }

    @Test
    void healthNeverGoesBelowZero() {
        Character character = withHealth(40);
        character.takeDamage(500);
        assertEquals(0, character.getHealth());
        assertFalse(character.isAlive());
    }

    @Test
    void healingNeverExceedsMaxHealth() {
        Character character = withHealth(40);
        character.takeDamage(3);
        character.heal(10);
        assertEquals(40, character.getHealth());
    }

    @Test
    void ignoresNegativeDamageAndHealing() {
        Character character = withHealth(40);
        character.takeDamage(10);
        character.takeDamage(-5);
        character.heal(-5);
        assertEquals(30, character.getHealth());
    }

    @Test
    void isWoundedBelowMaxHealth() {
        Character character = withHealth(40);
        character.takeDamage(1);
        assertTrue(character.isWounded());
    }

    @Test
    void endOfTurnAppliesItsEffect() {
        Character character = create("Cobaye", 40, 5, 1, new NoProtection(), new Regeneration(5), new NoDrop());
        character.takeDamage(10);
        character.endOfTurn();
        assertEquals(35, character.getHealth());
    }

    @Test
    void absorbsDamageWithItsProtection() {
        Character character = create("Cobaye", 40, 5, 1, new ArmorProtection(2), new NoEffect(), new NoDrop());
        assertEquals(4, character.absorb(6));
    }

    @Test
    void changesProtectionDuringTheGame() {
        Character character = withHealth(40);
        character.changeProtection(new ArmorProtection(2));
        assertEquals(4, character.absorb(6));
    }

    @Test
    void refusesToLoseItsProtection() {
        assertThrows(IllegalArgumentException.class, () -> withHealth(40).changeProtection(null));
    }

    @Test
    void rejectsBlankName() {
        assertThrows(IllegalArgumentException.class, () -> create(" ", 40, 5, 1, new NoProtection(), new NoEffect(), new NoDrop()));
    }

    @Test
    void rejectsMissingName() {
        assertThrows(IllegalArgumentException.class, () -> create(null, 40, 5, 1, new NoProtection(), new NoEffect(), new NoDrop()));
    }

    @Test
    void rejectsZeroMaxHealth() {
        assertThrows(IllegalArgumentException.class, () -> withHealth(0));
    }

    @Test
    void rejectsNegativeAttack() {
        assertThrows(IllegalArgumentException.class, () -> create("Cobaye", 40, -1, 1, new NoProtection(), new NoEffect(), new NoDrop()));
    }

    @Test
    void rejectsNegativeDefense() {
        assertThrows(IllegalArgumentException.class, () -> create("Cobaye", 40, 5, -1, new NoProtection(), new NoEffect(), new NoDrop()));
    }

    @Test
    void rejectsMissingProtection() {
        assertThrows(IllegalArgumentException.class, () -> create("Cobaye", 40, 5, 1, null, new NoEffect(), new NoDrop()));
    }

    @Test
    void rejectsMissingTurnEffect() {
        assertThrows(IllegalArgumentException.class, () -> create("Cobaye", 40, 5, 1, new NoProtection(), null, new NoDrop()));
    }

    @Test
    void rejectsMissingDrop() {
        assertThrows(IllegalArgumentException.class, () -> create("Cobaye", 40, 5, 1, new NoProtection(), new NoEffect(), null));
    }
}
