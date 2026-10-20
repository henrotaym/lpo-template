public class HavingProtection implements Protection {
    public int reduce(int damage) {
        return Math.max(1, damage / 2);
    }
}
