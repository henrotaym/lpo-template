public class Gobelin extends Character {
    public Gobelin() {
        super("Gobelin", 40, 7, 2, new NoProtection());
    }

    @Override
    public String getBattleCry() {
        return "Mon trésor, à moi !";
    }
}
