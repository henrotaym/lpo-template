package be.ifapme.dungeon.domain.loot;

import java.util.ArrayList;
import java.util.List;

import be.ifapme.dungeon.domain.behavior.Protection;

public class RecordingLooter implements Looter {
    private final List<Loot> collected = new ArrayList<>();
    private final List<Protection> equipped = new ArrayList<>();

    @Override
    public void collect(Loot loot) {
        this.collected.add(loot);
    }

    @Override
    public void equip(Protection shield) {
        this.equipped.add(shield);
    }

    public List<Loot> getCollected() {
        return this.collected;
    }

    public List<Protection> getEquipped() {
        return this.equipped;
    }
}
