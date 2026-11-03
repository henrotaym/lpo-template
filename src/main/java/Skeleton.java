public class Skeleton extends Character {
    public Skeleton() {
        super("Skeleton", 55, 9, 3, new HavingProtection(), new NoTurnEffect());
    }

    @Override
    public String getBattleCry() {
        return "Clac, clac, clac...";
    }
}
