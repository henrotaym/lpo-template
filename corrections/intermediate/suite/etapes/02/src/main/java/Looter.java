public interface Looter {
    String getName();

    void collect(Loot loot);

    void equip(Protection shield);
}
