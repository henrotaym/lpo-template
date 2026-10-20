package be.ifapme.dungeon.domain.behavior;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ArmorProtectionTest {
    @Test
    void removesItsPointsFromDamage() {
        assertEquals(4, new ArmorProtection(2).reduce(6));
    }

    @Test
    void letsAtLeastOneDamageThrough() {
        assertEquals(1, new ArmorProtection(2).reduce(1));
    }

    @Test
    void acceptsZeroArmor() {
        assertDoesNotThrow(() -> new ArmorProtection(0));
    }

    @Test
    void rejectsNegativeArmor() {
        assertThrows(IllegalArgumentException.class, () -> new ArmorProtection(-1));
    }
}
