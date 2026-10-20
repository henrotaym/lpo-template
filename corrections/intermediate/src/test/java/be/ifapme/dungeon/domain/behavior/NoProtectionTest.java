package be.ifapme.dungeon.domain.behavior;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class NoProtectionTest {
    @Test
    void letsAllDamageThrough() {
        assertEquals(18, new NoProtection().reduce(18));
    }
}
