package be.ifapme.dungeon.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.ifapme.dungeon.domain.character.Goblin;
import be.ifapme.dungeon.domain.character.Hero;
import be.ifapme.dungeon.domain.character.Troll;
import be.ifapme.dungeon.domain.loot.Chest;
import be.ifapme.dungeon.domain.loot.Loot;
import be.ifapme.dungeon.domain.loot.NoDrop;

class ConsoleBattleLogTest {
    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream captured = new ByteArrayOutputStream();
    private final ConsoleBattleLog log = new ConsoleBattleLog();
    private final Hero aria = new Hero("Aria", 200, 20, 4);

    @BeforeEach
    void captureConsole() {
        System.setOut(new PrintStream(this.captured, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    void restoreConsole() {
        System.setOut(this.originalOut);
    }

    private String printed() {
        return this.captured.toString(StandardCharsets.UTF_8);
    }

    @Test
    void heroEntered() {
        this.log.heroEntered(this.aria);
        assertEquals("Aria [200/200 PV, ATK 20] entre dans le donjon.\n", this.printed());
    }

    @Test
    void monsterAppeared() {
        this.log.monsterAppeared(new Goblin());
        assertEquals("\n--- Gobelin [40/40 PV, ATK 7] apparaît ---\nGobelin : Mon trésor, à moi !\n", this.printed());
    }

    @Test
    void heroStruck() {
        Goblin goblin = new Goblin();
        goblin.takeDamage(18);
        this.log.heroStruck(this.aria, goblin, 18);
        assertEquals("Aria frappe Gobelin pour 18 dégâts. Gobelin [22/40 PV, ATK 7]\n", this.printed());
    }

    @Test
    void monsterRetaliated() {
        this.aria.takeDamage(3);
        this.log.monsterRetaliated(new Goblin(), this.aria, 3);
        assertEquals("Gobelin riposte pour 3 dégâts. Aria [197/200 PV, ATK 20]\n", this.printed());
    }

    @Test
    void regenerated() {
        Troll troll = new Troll();
        troll.takeDamage(10);
        this.log.regenerated(troll, 5);
        assertEquals("Troll régénère 5 PV. Troll [70/80 PV, ATK 15]\n", this.printed());
    }

    @Test
    void monsterDefeated() {
        this.log.monsterDefeated(new Goblin());
        assertEquals("Gobelin est vaincu !\n", this.printed());
    }

    @Test
    void lootCollected() {
        this.log.lootCollected(new Loot("Dague ébréchée", 15));
        assertEquals("Butin : Dague ébréchée (15 po)\n", this.printed());
    }

    @Test
    void shieldPickedUp() {
        this.log.shieldPickedUp(this.aria, "Squelette");
        assertEquals("Aria ramasse le bouclier de Squelette.\n", this.printed());
    }

    @Test
    void chestReached() {
        this.log.chestReached(new Chest("Coffre du donjon", 30, new NoDrop()));
        assertEquals("\n--- Coffre du donjon [30 de solidité] ---\n", this.printed());
    }

    @Test
    void chestStruck() {
        Chest chest = new Chest("Coffre du donjon", 30, new NoDrop());
        chest.takeDamage(20);
        this.log.chestStruck(this.aria, chest);
        assertEquals("Aria frappe le coffre. Coffre du donjon [10 de solidité]\n", this.printed());
    }

    @Test
    void explorationEnded() {
        this.log.explorationEnded(4, 4, 345);
        assertEquals("\n=== Fin de l'exploration ===\nMonstres vaincus : 4 / 4\nOr récolté : 345 po\n", this.printed());
    }
}
