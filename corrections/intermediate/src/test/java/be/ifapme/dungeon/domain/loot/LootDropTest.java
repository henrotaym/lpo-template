package be.ifapme.dungeon.domain.loot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class LootDropTest {
    @Test
    void givesItsLoot() {
        Loot dagger = new Loot("Dague ébréchée", 15);
        RecordingLooter looter = new RecordingLooter();
        new LootDrop(dagger).giveTo(looter);
        assertEquals(List.of(dagger), looter.getCollected());
        assertTrue(looter.getEquipped().isEmpty());
    }

    @Test
    void rejectsMissingLoot() {
        assertThrows(IllegalArgumentException.class, () -> new LootDrop(null));
    }
}
