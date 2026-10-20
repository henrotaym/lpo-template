package be.ifapme.dungeon.domain.behavior;

public class Regeneration implements TurnEffect {
    private final int amount;

    public Regeneration(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Une régénération rend au moins 1 PV.");
        }
        this.amount = amount;
    }

    @Override
    public void apply(Healable target) {
        if (!target.isAlive() || !target.isWounded()) {
            return;
        }
        target.heal(this.amount);
    }
}
