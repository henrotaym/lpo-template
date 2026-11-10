public class Skeleton extends Character {
    public Skeleton() {
        super("Squelette", 55, 9, 3, new HalvingProtection(), new NoTurnEffect(),
                new ShieldDrop(new ArmorProtection(2)));
    }

    @Override
    public String getBattleCry() {
        return "Clac, clac, clac...";
    }
}
