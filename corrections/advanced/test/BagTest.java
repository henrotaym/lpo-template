import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dungeon.loot.Bag;
import dungeon.loot.Loot;
import dungeon.loot.Potion;
import dungeon.loot.Rarity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BagTest {

    @Test
    @DisplayName("Le sac additionne la valeur de son contenu")
    void totalValueSumsItems() {
        Bag<Loot> bag = new Bag<>();
        bag.add(new Loot("Dague", 15, Rarity.COMMON));
        bag.add(new Loot("Massue", 80, Rarity.RARE));

        assertEquals(135, bag.totalValue());
    }

    @Test
    @DisplayName("Un objet pris quitte le sac, dans l'ordre de rangement")
    void takeFirstRemovesOldestItem() {
        Bag<Potion> bag = new Bag<>();
        Potion first = new Potion("Petite", 10, 5);
        bag.add(first);
        bag.add(Potion.small());

        assertEquals(first, bag.takeFirst());
        assertEquals(1, bag.size());
    }

    @Test
    @DisplayName("Un sac vide vaut 0 et refuse qu'on y prenne quelque chose")
    void emptyBagIsWorthNothing() {
        Bag<Potion> bag = new Bag<>();

        assertTrue(bag.isEmpty());
        assertEquals(0, bag.totalValue());
        assertThrows(IllegalStateException.class, bag::takeFirst);
    }
}
