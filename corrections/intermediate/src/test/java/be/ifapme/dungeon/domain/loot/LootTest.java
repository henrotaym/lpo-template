package be.ifapme.dungeon.domain.loot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class LootTest {
    @Test
    void displaysNameAndValue() {
        assertEquals("Dague ébréchée (15 po)", new Loot("Dague ébréchée", 15).toString());
    }

    @Test
    void acceptsZeroValue() {
        assertEquals(0, new Loot("Caillou", 0).getGoldValue());
    }

    @Test
    void rejectsBlankName() {
        assertThrows(IllegalArgumentException.class, () -> new Loot(" ", 15));
    }

    @Test
    void rejectsMissingName() {
        assertThrows(IllegalArgumentException.class, () -> new Loot(null, 15));
    }

    @Test
    void rejectsNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> new Loot("Dague", -1));
    }
}
