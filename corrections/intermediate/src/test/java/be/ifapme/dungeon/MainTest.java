package be.ifapme.dungeon;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MainTest {
    private static final String EXPECTED_OUTPUT = """
            Aria [200/200 PV, ATK 20] entre dans le donjon.

            --- Gobelin [40/40 PV, ATK 7] apparaît ---
            Gobelin : Mon trésor, à moi !
            Aria frappe Gobelin pour 18 dégâts. Gobelin [22/40 PV, ATK 7]
            Gobelin riposte pour 3 dégâts. Aria [197/200 PV, ATK 20]
            Aria frappe Gobelin pour 18 dégâts. Gobelin [4/40 PV, ATK 7]
            Gobelin riposte pour 3 dégâts. Aria [194/200 PV, ATK 20]
            Aria frappe Gobelin pour 18 dégâts. Gobelin [0/40 PV, ATK 7]
            Gobelin est vaincu !
            Butin : Dague ébréchée (15 po)

            --- Squelette [55/55 PV, ATK 9] apparaît ---
            Squelette : Clac, clac, clac...
            Aria frappe Squelette pour 8 dégâts. Squelette [47/55 PV, ATK 9]
            Squelette riposte pour 5 dégâts. Aria [189/200 PV, ATK 20]
            Aria frappe Squelette pour 8 dégâts. Squelette [39/55 PV, ATK 9]
            Squelette riposte pour 5 dégâts. Aria [184/200 PV, ATK 20]
            Aria frappe Squelette pour 8 dégâts. Squelette [31/55 PV, ATK 9]
            Squelette riposte pour 5 dégâts. Aria [179/200 PV, ATK 20]
            Aria frappe Squelette pour 8 dégâts. Squelette [23/55 PV, ATK 9]
            Squelette riposte pour 5 dégâts. Aria [174/200 PV, ATK 20]
            Aria frappe Squelette pour 8 dégâts. Squelette [15/55 PV, ATK 9]
            Squelette riposte pour 5 dégâts. Aria [169/200 PV, ATK 20]
            Aria frappe Squelette pour 8 dégâts. Squelette [7/55 PV, ATK 9]
            Squelette riposte pour 5 dégâts. Aria [164/200 PV, ATK 20]
            Aria frappe Squelette pour 8 dégâts. Squelette [0/55 PV, ATK 9]
            Squelette est vaincu !
            Aria ramasse le bouclier de Squelette.

            --- Troll [80/80 PV, ATK 15] apparaît ---
            Troll : Troll écraser !
            Aria frappe Troll pour 15 dégâts. Troll [65/80 PV, ATK 15]
            Troll riposte pour 9 dégâts. Aria [155/200 PV, ATK 20]
            Troll régénère 5 PV. Troll [70/80 PV, ATK 15]
            Aria frappe Troll pour 15 dégâts. Troll [55/80 PV, ATK 15]
            Troll riposte pour 9 dégâts. Aria [146/200 PV, ATK 20]
            Troll régénère 5 PV. Troll [60/80 PV, ATK 15]
            Aria frappe Troll pour 15 dégâts. Troll [45/80 PV, ATK 15]
            Troll riposte pour 9 dégâts. Aria [137/200 PV, ATK 20]
            Troll régénère 5 PV. Troll [50/80 PV, ATK 15]
            Aria frappe Troll pour 15 dégâts. Troll [35/80 PV, ATK 15]
            Troll riposte pour 9 dégâts. Aria [128/200 PV, ATK 20]
            Troll régénère 5 PV. Troll [40/80 PV, ATK 15]
            Aria frappe Troll pour 15 dégâts. Troll [25/80 PV, ATK 15]
            Troll riposte pour 9 dégâts. Aria [119/200 PV, ATK 20]
            Troll régénère 5 PV. Troll [30/80 PV, ATK 15]
            Aria frappe Troll pour 15 dégâts. Troll [15/80 PV, ATK 15]
            Troll riposte pour 9 dégâts. Aria [110/200 PV, ATK 20]
            Troll régénère 5 PV. Troll [20/80 PV, ATK 15]
            Aria frappe Troll pour 15 dégâts. Troll [5/80 PV, ATK 15]
            Troll riposte pour 9 dégâts. Aria [101/200 PV, ATK 20]
            Troll régénère 5 PV. Troll [10/80 PV, ATK 15]
            Aria frappe Troll pour 15 dégâts. Troll [0/80 PV, ATK 15]
            Troll est vaincu !
            Butin : Massue de troll (80 po)

            --- Golem [70/70 PV, ATK 12] apparaît ---
            Golem : Grrrooonnnk.
            Aria frappe Golem pour 8 dégâts. Golem [62/70 PV, ATK 12]
            Golem riposte pour 6 dégâts. Aria [95/200 PV, ATK 20]
            Golem régénère 3 PV. Golem [65/70 PV, ATK 12]
            Aria frappe Golem pour 8 dégâts. Golem [57/70 PV, ATK 12]
            Golem riposte pour 6 dégâts. Aria [89/200 PV, ATK 20]
            Golem régénère 3 PV. Golem [60/70 PV, ATK 12]
            Aria frappe Golem pour 8 dégâts. Golem [52/70 PV, ATK 12]
            Golem riposte pour 6 dégâts. Aria [83/200 PV, ATK 20]
            Golem régénère 3 PV. Golem [55/70 PV, ATK 12]
            Aria frappe Golem pour 8 dégâts. Golem [47/70 PV, ATK 12]
            Golem riposte pour 6 dégâts. Aria [77/200 PV, ATK 20]
            Golem régénère 3 PV. Golem [50/70 PV, ATK 12]
            Aria frappe Golem pour 8 dégâts. Golem [42/70 PV, ATK 12]
            Golem riposte pour 6 dégâts. Aria [71/200 PV, ATK 20]
            Golem régénère 3 PV. Golem [45/70 PV, ATK 12]
            Aria frappe Golem pour 8 dégâts. Golem [37/70 PV, ATK 12]
            Golem riposte pour 6 dégâts. Aria [65/200 PV, ATK 20]
            Golem régénère 3 PV. Golem [40/70 PV, ATK 12]
            Aria frappe Golem pour 8 dégâts. Golem [32/70 PV, ATK 12]
            Golem riposte pour 6 dégâts. Aria [59/200 PV, ATK 20]
            Golem régénère 3 PV. Golem [35/70 PV, ATK 12]
            Aria frappe Golem pour 8 dégâts. Golem [27/70 PV, ATK 12]
            Golem riposte pour 6 dégâts. Aria [53/200 PV, ATK 20]
            Golem régénère 3 PV. Golem [30/70 PV, ATK 12]
            Aria frappe Golem pour 8 dégâts. Golem [22/70 PV, ATK 12]
            Golem riposte pour 6 dégâts. Aria [47/200 PV, ATK 20]
            Golem régénère 3 PV. Golem [25/70 PV, ATK 12]
            Aria frappe Golem pour 8 dégâts. Golem [17/70 PV, ATK 12]
            Golem riposte pour 6 dégâts. Aria [41/200 PV, ATK 20]
            Golem régénère 3 PV. Golem [20/70 PV, ATK 12]
            Aria frappe Golem pour 8 dégâts. Golem [12/70 PV, ATK 12]
            Golem riposte pour 6 dégâts. Aria [35/200 PV, ATK 20]
            Golem régénère 3 PV. Golem [15/70 PV, ATK 12]
            Aria frappe Golem pour 8 dégâts. Golem [7/70 PV, ATK 12]
            Golem riposte pour 6 dégâts. Aria [29/200 PV, ATK 20]
            Golem régénère 3 PV. Golem [10/70 PV, ATK 12]
            Aria frappe Golem pour 8 dégâts. Golem [2/70 PV, ATK 12]
            Golem riposte pour 6 dégâts. Aria [23/200 PV, ATK 20]
            Golem régénère 3 PV. Golem [5/70 PV, ATK 12]
            Aria frappe Golem pour 8 dégâts. Golem [0/70 PV, ATK 12]
            Golem est vaincu !

            --- Coffre du donjon [30 de solidité] ---
            Aria frappe le coffre. Coffre du donjon [10 de solidité]
            Aria frappe le coffre. Coffre du donjon [0 de solidité]
            Butin : Couronne ancienne (250 po)

            === Fin de l'exploration ===
            Monstres vaincus : 4 / 4
            Or récolté : 345 po
            """;

    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream captured = new ByteArrayOutputStream();

    @BeforeEach
    void captureConsole() {
        System.setOut(new PrintStream(this.captured, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    void restoreConsole() {
        System.setOut(this.originalOut);
    }

    @Test
    void printsTheExpectedExploration() {
        Main.main(new String[0]);
        assertEquals(EXPECTED_OUTPUT, this.captured.toString(StandardCharsets.UTF_8));
    }
}
