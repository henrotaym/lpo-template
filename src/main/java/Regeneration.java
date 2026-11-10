public class Regeneration implements TurnEffect {
    private int amount;

    public Regeneration(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount value cannot be less than 0.");
        }

        this.amount = amount;
    }

    public int getAmount() {
        return this.amount;
    }

    public void apply(Healable target) {
        if (!target.isAlive() || !target.isWounded()) {
            return;
        }

        target.heal(this.amount);
    }
}
