package be.ifapme.dungeon.domain.behavior;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class HalvingProtectionTest {
    private final HalvingProtection protection = new HalvingProtection();

    @Test
    void halvesDamage() {
        assertEquals(8, this.protection.reduce(16));
    }

    @Test
    void roundsDown() {
        assertEquals(8, this.protection.reduce(17));
    }

    @Test
    void letsAtLeastOneDamageThrough() {
        assertEquals(1, this.protection.reduce(1));
    }
}
