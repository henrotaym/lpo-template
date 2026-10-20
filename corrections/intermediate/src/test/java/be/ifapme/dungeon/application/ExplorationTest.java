package be.ifapme.dungeon.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import be.ifapme.dungeon.domain.character.Goblin;
import be.ifapme.dungeon.domain.character.Golem;
import be.ifapme.dungeon.domain.character.Hero;
import be.ifapme.dungeon.domain.character.Skeleton;
import be.ifapme.dungeon.domain.character.Troll;
import be.ifapme.dungeon.domain.combat.DamageCalculator;
import be.ifapme.dungeon.domain.loot.Chest;
import be.ifapme.dungeon.domain.loot.Loot;
import be.ifapme.dungeon.domain.loot.LootDrop;

class ExplorationTest {
    private final RecordingBattleLog log = new RecordingBattleLog();
    private final Exploration exploration = new Exploration(new DamageCalculator(), this.log);

    private static Chest chest() {
        return new Chest("Coffre du donjon", 30, new LootDrop(new Loot("Couronne ancienne", 250)));
    }

    @Test
    void fightsAGoblinUntilItFallsThenCollectsItsLoot() {
        Hero aria = new Hero("Aria", 200, 20, 4);
        this.exploration.explore(aria, List.of(new Goblin()), chest());
        assertEquals(List.of(
                "entered Aria",
                "appeared Gobelin",
                "struck Gobelin 18",
                "retaliated Gobelin 3",
                "struck Gobelin 18",
                "retaliated Gobelin 3",
                "struck Gobelin 18",
                "defeated Gobelin",
                "loot Dague ébréchée",
                "chest reached",
                "chest struck",
                "chest struck",
                "loot Couronne ancienne",
                "ended 1/1 265"), this.log.getEvents());
        assertEquals(194, aria.getHealth());
    }

    @Test
    void heroPicksUpTheSkeletonShieldAndUsesItAfterwards() {
        Hero aria = new Hero("Aria", 200, 20, 4);
        this.exploration.explore(aria, List.of(new Skeleton(), new Golem()), chest());
        assertTrue(this.log.getEvents().contains("shield from Squelette"));
        assertTrue(this.log.getEvents().contains("retaliated Golem 6"));
    }

    @Test
    void announcesRegenerationOnlyWhenItHeals() {
        Hero aria = new Hero("Aria", 200, 20, 4);
        this.exploration.explore(aria, List.of(new Troll()), chest());
        List<String> regenerations = this.log.getEvents().stream().filter(event -> event.startsWith("regenerated")).toList();
        assertEquals(7, regenerations.size());
        assertTrue(regenerations.stream().allMatch("regenerated Troll 5"::equals));
    }

    @Test
    void stopsWhenTheHeroFallsAndSkipsTheChest() {
        Hero weakling = new Hero("Faible", 10, 1, 0);
        Goblin goblin = new Goblin();
        Troll troll = new Troll();
        this.exploration.explore(weakling, List.of(goblin, troll), chest());
        assertFalse(weakling.isAlive());
        assertTrue(goblin.isAlive());
        assertFalse(this.log.getEvents().contains("appeared Troll"));
        assertFalse(this.log.getEvents().contains("chest reached"));
        assertEquals("ended 0/2 0", this.log.getEvents().getLast());
    }

    @Test
    void reportsTheWholeDungeon() {
        Hero aria = new Hero("Aria", 200, 20, 4);
        this.exploration.explore(aria, List.of(new Goblin(), new Skeleton(), new Troll(), new Golem()), chest());
        assertEquals("ended 4/4 345", this.log.getEvents().getLast());
        assertEquals(23, aria.getHealth());
    }
}
