package be.ifapme.dungeon.domain.behavior;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class RegenerationTest {
    @Test
    void healsAWoundedTarget() {
        FakeHealable target = new FakeHealable(true, true);
        new Regeneration(5).apply(target);
        assertEquals(5, target.getHealed());
    }

    @Test
    void doesNotHealAnUnhurtTarget() {
        FakeHealable target = new FakeHealable(true, false);
        new Regeneration(5).apply(target);
        assertEquals(0, target.getHealed());
    }

    @Test
    void doesNotHealADeadTarget() {
        FakeHealable target = new FakeHealable(false, true);
        new Regeneration(5).apply(target);
        assertEquals(0, target.getHealed());
    }

    @Test
    void rejectsZeroAmount() {
        assertThrows(IllegalArgumentException.class, () -> new Regeneration(0));
    }
}
