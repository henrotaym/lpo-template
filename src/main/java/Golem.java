public class Golem extends Character {
    public Golem() {
        super("Golem", 70, 12, 4, new HavingProtection(), new Regeneration(3));
    }

    @Override
    public String getBattleCry() {
        return "Grrrooonnnk";
    }
}
