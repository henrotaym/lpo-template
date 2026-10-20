package be.ifapme.dungeon.domain.loot;

import be.ifapme.dungeon.domain.behavior.Protection;

public interface Looter {
    void collect(Loot loot);

    void equip(Protection shield);
}
