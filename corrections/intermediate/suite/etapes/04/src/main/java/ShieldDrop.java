public class ShieldDrop implements Drop {
    private Protection shield;

    public ShieldDrop(Protection shield) {
        if (shield == null) {
            throw new IllegalArgumentException("Shield value cannot be null.");
        }

        this.shield = shield;
    }

    public void giveTo(Looter looter, Lootable source) {
        looter.equip(this.shield);
        IO.println(looter.getName() + " ramasse le bouclier de " + source.getName() + ".");
    }
}
