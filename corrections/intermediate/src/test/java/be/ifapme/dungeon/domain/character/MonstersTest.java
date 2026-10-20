package be.ifapme.dungeon.domain.character;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import be.ifapme.dungeon.domain.loot.Loot;
import be.ifapme.dungeon.domain.loot.RecordingLooter;

class MonstersTest {
    private static RecordingLooter lootOf(Character monster) {
        RecordingLooter looter = new RecordingLooter();
        monster.getDrop().giveTo(looter);
        return looter;
    }

    private static List<String> collectedNames(RecordingLooter looter) {
        return looter.getCollected().stream().map(Loot::toString).toList();
    }

    @Test
    void goblin() {
        Goblin goblin = new Goblin();
        assertEquals("Gobelin [40/40 PV, ATK 7]", goblin.toString());
        assertEquals(2, goblin.getDefense());
        assertEquals("Mon trésor, à moi !", goblin.getBattleCry());
        assertEquals(10, goblin.absorb(10));
        assertEquals(List.of("Dague ébréchée (15 po)"), collectedNames(lootOf(goblin)));
    }

    @Test
    void skeleton() {
        Skeleton skeleton = new Skeleton();
        assertEquals("Squelette [55/55 PV, ATK 9]", skeleton.toString());
        assertEquals(3, skeleton.getDefense());
        assertEquals("Clac, clac, clac...", skeleton.getBattleCry());
        assertEquals(5, skeleton.absorb(10));
        RecordingLooter looter = lootOf(skeleton);
        assertTrue(looter.getCollected().isEmpty());
        assertEquals(4, looter.getEquipped().getFirst().reduce(6));
    }

    @Test
    void troll() {
        Troll troll = new Troll();
        assertEquals("Troll [80/80 PV, ATK 15]", troll.toString());
        assertEquals(5, troll.getDefense());
        assertEquals("Troll écraser !", troll.getBattleCry());
        assertEquals(10, troll.absorb(10));
        assertEquals(List.of("Massue de troll (80 po)"), collectedNames(lootOf(troll)));
    }

    @Test
    void trollRegeneratesFive() {
        Troll troll = new Troll();
        troll.takeDamage(15);
        troll.endOfTurn();
        assertEquals(70, troll.getHealth());
    }

    @Test
    void golem() {
        Golem golem = new Golem();
        assertEquals("Golem [70/70 PV, ATK 12]", golem.toString());
        assertEquals(4, golem.getDefense());
        assertEquals("Grrrooonnnk.", golem.getBattleCry());
        assertEquals(5, golem.absorb(10));
        RecordingLooter looter = lootOf(golem);
        assertTrue(looter.getCollected().isEmpty());
        assertTrue(looter.getEquipped().isEmpty());
    }

    @Test
    void golemRegeneratesThree() {
        Golem golem = new Golem();
        golem.takeDamage(10);
        golem.endOfTurn();
        assertEquals(63, golem.getHealth());
    }
}
