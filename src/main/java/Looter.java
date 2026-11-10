public interface Looter {
    void collect(Loot loot);

    void equip(Protection protection);

    String getName();
}
