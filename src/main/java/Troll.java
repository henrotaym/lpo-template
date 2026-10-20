public class Troll extends Character {
    public Troll() {
        super("Troll", 80, 15, 5, new NoProtection());
    }

    @Override
    public String getBattleCry() {
        return "Troll écraser !";
    }
}
