package be.ifapme.dungeon.domain.combat;

import be.ifapme.dungeon.domain.character.Character;

public class DamageCalculator {
    private static final int MINIMUM_DAMAGE = 1;

    public int computeDamage(Character attacker, Character defender) {
        int damage = Math.max(MINIMUM_DAMAGE, attacker.getAttackPower() - defender.getDefense());
        return defender.absorb(damage);
    }
}
