import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import dungeon.loot.Loot;
import dungeon.loot.Rarity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LootTest {

    @Test
    @DisplayName("La rareté multiplie la valeur de base")
    void rarityMultipliesBaseValue() {
        assertEquals(250, new Loot("Couronne", 100, Rarity.EPIC).goldValue());
    }

    @Test
    @DisplayName("Deux butins identiques sont égaux")
    void sameValuesAreEqual() {
        assertEquals(new Loot("Potion", 10, Rarity.COMMON), new Loot("Potion", 10, Rarity.COMMON));
    }

    @Test
    @DisplayName("Une rareté différente distingue deux butins")
    void differentRarityMakesDifferentLoot() {
        assertNotEquals(new Loot("Potion", 10, Rarity.COMMON), new Loot("Potion", 10, Rarity.RARE));
    }

    @Test
    @DisplayName("Une valeur négative est refusée à la création")
    void negativeValueIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Loot("Potion", -1, Rarity.COMMON));
    }
}
