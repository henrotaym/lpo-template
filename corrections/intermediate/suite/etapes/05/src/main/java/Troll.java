public class Troll extends Character {
    public Troll() {
        super("Troll", 80, 15, 5, new NoProtection(), new Regeneration(5), new LootDrop(new Loot("Massue de troll", 80)));
    }

    @Override
    public String getBattleCry() {
        return "Troll écraser !";
    }
}
