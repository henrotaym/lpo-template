package dungeon.loot;

import java.util.ArrayList;
import java.util.List;

public class Bag<T extends Valuable> {
    private final List<T> items = new ArrayList<>();

    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("On ne range pas un objet absent.");
        }
        this.items.add(item);
    }

    public T takeFirst() {
        if (this.items.isEmpty()) {
            throw new IllegalStateException("Le sac est vide.");
        }
        return this.items.removeFirst();
    }

    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    public int size() {
        return this.items.size();
    }

    public int totalValue() {
        int total = 0;
        for (T item : this.items) {
            total += item.goldValue();
        }
        return total;
    }

    public List<T> getItems() {
        return List.copyOf(this.items);
    }
}
