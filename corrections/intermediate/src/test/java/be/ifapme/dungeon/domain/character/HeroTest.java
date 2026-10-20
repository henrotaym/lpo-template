package be.ifapme.dungeon.domain.character;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import be.ifapme.dungeon.domain.behavior.ArmorProtection;
import be.ifapme.dungeon.domain.loot.Loot;
import be.ifapme.dungeon.domain.loot.RecordingLooter;

class HeroTest {
    private final Hero aria = new Hero("Aria", 200, 20, 4);

    @Test
    void hasItsBattleCry() {
        assertEquals("Pour la lumière !", this.aria.getBattleCry());
        assertEquals("Aria [200/200 PV, ATK 20]", this.aria.toString());
    }

    @Test
    void takesDamageNormallyAtFirst() {
        assertEquals(6, this.aria.absorb(6));
    }

    @Test
    void countsTheGoldOfCollectedLoot() {
        this.aria.collect(new Loot("Dague ébréchée", 15));
        this.aria.collect(new Loot("Massue de troll", 80));
        assertEquals(95, this.aria.getGold());
    }

    @Test
    void absorbsWithTheShieldSheEquips() {
        this.aria.equip(new ArmorProtection(2));
        assertEquals(4, this.aria.absorb(6));
    }

    @Test
    void countsVictories() {
        this.aria.recordVictory();
        this.aria.recordVictory();
        assertEquals(2, this.aria.getVictories());
    }

    @Test
    void leavesNothing() {
        RecordingLooter looter = new RecordingLooter();
        this.aria.getDrop().giveTo(looter);
        assertTrue(looter.getCollected().isEmpty());
        assertTrue(looter.getEquipped().isEmpty());
    }
}
