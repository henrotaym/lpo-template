package be.ifapme.dungeon.domain.loot;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class NoDropTest {
    @Test
    void givesNothing() {
        RecordingLooter looter = new RecordingLooter();
        new NoDrop().giveTo(looter);
        assertTrue(looter.getCollected().isEmpty());
        assertTrue(looter.getEquipped().isEmpty());
    }
}
