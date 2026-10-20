public class DamageCalculator {
    public int computeDamage(Character attacker, Character defenser) {
        return Math.max(1, defenser.absorb(attacker.getAttackPower() - defenser.getDefense()));
    }
}
