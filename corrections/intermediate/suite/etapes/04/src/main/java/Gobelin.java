public class Gobelin extends Character {
    public Gobelin() {
        super("Gobelin", 40, 7, 2, new NoProtection(), new NoTurnEffect(), new LootDrop(new Loot("Dague ébréchée", 15)));
    }

    @Override
    public String getBattleCry() {
        return "Mon trésor, à moi !";
    }
}
