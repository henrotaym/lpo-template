public class DamageCalculator {
    public int computeDamage(Character attacker, Character defenser) {
        return defenser.absorb(Math.max(1, attacker.getAttackPower() - defenser.getDefense()));
    }
}
