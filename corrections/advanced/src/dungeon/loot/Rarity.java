package dungeon.loot;

public enum Rarity {
    COMMON("Commun", 1.0, "*"),
    RARE("Rare", 1.5, "**"),
    EPIC("Épique", 2.5, "***"),
    LEGENDARY("Légendaire", 5.0, "****");

    private final String label;
    private final double valueMultiplier;
    private final String symbol;

    Rarity(String label, double valueMultiplier, String symbol) {
        this.label = label;
        this.valueMultiplier = valueMultiplier;
        this.symbol = symbol;
    }

    public String getLabel() {
        return this.label;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public int applyTo(int baseValue) {
        return (int) Math.round(baseValue * this.valueMultiplier);
    }

    public String describe() {
        return switch (this) {
            case COMMON -> "Un objet banal.";
            case RARE -> "Une belle trouvaille.";
            case EPIC, LEGENDARY -> "Une pièce exceptionnelle !";
        };
    }
}
