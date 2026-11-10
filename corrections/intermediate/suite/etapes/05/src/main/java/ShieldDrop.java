public class ShieldDrop implements Drop {
    private Protection shield;

    public ShieldDrop(Protection shield) {
        if (shield == null) {
            throw new IllegalArgumentException("Shield value cannot be null.");
        }

        this.shield = shield;
    }

    public void giveTo(Looter looter) {
        looter.equip(this.shield);
    }
}
