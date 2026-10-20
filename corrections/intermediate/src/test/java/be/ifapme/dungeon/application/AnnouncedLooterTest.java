package be.ifapme.dungeon.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import be.ifapme.dungeon.domain.behavior.ArmorProtection;
import be.ifapme.dungeon.domain.character.Hero;
import be.ifapme.dungeon.domain.loot.Loot;

class AnnouncedLooterTest {
    private final Hero aria = new Hero("Aria", 200, 20, 4);
    private final RecordingBattleLog log = new RecordingBattleLog();
    private final AnnouncedLooter looter = new AnnouncedLooter(this.aria, this.log, "Squelette");

    @Test
    void givesLootToTheHeroAndAnnouncesIt() {
        this.looter.collect(new Loot("Dague ébréchée", 15));
        assertEquals(15, this.aria.getGold());
        assertEquals(List.of("loot Dague ébréchée"), this.log.getEvents());
    }

    @Test
    void equipsTheHeroAndAnnouncesWhereTheShieldComesFrom() {
        this.looter.equip(new ArmorProtection(2));
        assertEquals(4, this.aria.absorb(6));
        assertEquals(List.of("shield from Squelette"), this.log.getEvents());
    }
}
