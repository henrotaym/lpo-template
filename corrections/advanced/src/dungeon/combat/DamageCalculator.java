package dungeon.combat;

import dungeon.character.Character;

public class DamageCalculator {
    private static final int MINIMUM_DAMAGE = 1;
    private static final int CRITICAL_MULTIPLIER = 2;

    public int computeDamage(Attack attack, Character defender) {
        int base = attack.power();
        if (attack.critical()) {
            base = base * CRITICAL_MULTIPLIER;
        }

        double multiplier = attack.element().multiplierAgainst(defender.getElement());
        int afterElement = (int) Math.round(base * multiplier);
        int damage = Math.max(MINIMUM_DAMAGE, afterElement - defender.getDefense());

        return defender.absorb(damage);
    }
}
