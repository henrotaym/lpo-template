public class ArmorProtection implements Protection {
    private int shield;

    public ArmorProtection(int shield) {
        if (shield < 0) {
            throw new IllegalArgumentException("Shield value cannot be less than 0.");
        }

        this.shield = shield;
    }

    public int reduce(int damage) {
        return Math.max(1, damage - this.shield);
    }
}
