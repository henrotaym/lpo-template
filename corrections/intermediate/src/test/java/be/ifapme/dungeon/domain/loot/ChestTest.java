package be.ifapme.dungeon.domain.loot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ChestTest {
    private final Drop crown = new LootDrop(new Loot("Couronne ancienne", 250));
    private final Chest chest = new Chest("Coffre du donjon", 30, this.crown);

    @Test
    void startsClosed() {
        assertFalse(this.chest.isOpen());
        assertEquals("Coffre du donjon [30 de solidité]", this.chest.toString());
    }

    @Test
    void losesDurabilityWhenHit() {
        this.chest.takeDamage(20);
        assertEquals("Coffre du donjon [10 de solidité]", this.chest.toString());
        assertFalse(this.chest.isOpen());
    }

    @Test
    void opensAtZeroWithoutGoingBelow() {
        this.chest.takeDamage(20);
        this.chest.takeDamage(20);
        assertTrue(this.chest.isOpen());
        assertEquals("Coffre du donjon [0 de solidité]", this.chest.toString());
    }

    @Test
    void ignoresNegativeDamage() {
        this.chest.takeDamage(-5);
        assertEquals("Coffre du donjon [30 de solidité]", this.chest.toString());
    }

    @Test
    void isLootedThroughItsDrop() {
        assertSame(this.crown, this.chest.getDrop());
        assertEquals("Coffre du donjon", this.chest.getName());
    }
}
