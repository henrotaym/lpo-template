package be.ifapme.dungeon.domain.behavior;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class NoEffectTest {
    @Test
    void doesNothing() {
        FakeHealable target = new FakeHealable(true, true);
        new NoEffect().apply(target);
        assertEquals(0, target.getHealed());
    }
}
