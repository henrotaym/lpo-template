public class Hero extends Character {
    public Hero() {
        super("Aria", 200, 20, 4, new NoProtection(), new NoTurnEffect());
    }

    @Override
    public String getBattleCry() {
        return "Pour la lumière !";
    }
}
