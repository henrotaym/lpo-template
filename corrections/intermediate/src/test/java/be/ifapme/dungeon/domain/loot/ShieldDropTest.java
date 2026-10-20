package be.ifapme.dungeon.domain.loot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import be.ifapme.dungeon.domain.behavior.ArmorProtection;

class ShieldDropTest {
    @Test
    void givesItsShield() {
        ArmorProtection shield = new ArmorProtection(2);
        RecordingLooter looter = new RecordingLooter();
        new ShieldDrop(shield).giveTo(looter);
        assertEquals(List.of(shield), looter.getEquipped());
        assertTrue(looter.getCollected().isEmpty());
    }

    @Test
    void rejectsMissingShield() {
        assertThrows(IllegalArgumentException.class, () -> new ShieldDrop(null));
    }
}
