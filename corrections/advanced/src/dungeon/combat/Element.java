package dungeon.combat;

public enum Element {
    PHYSICAL("Physique"),
    FIRE("Feu"),
    ICE("Glace"),
    POISON("Poison");

    private static final double ADVANTAGE_MULTIPLIER = 1.5;

    private final String label;

    Element(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public double multiplierAgainst(Element other) {
        boolean isStrong = switch (this) {
            case FIRE -> other == ICE;
            case ICE -> other == POISON;
            case POISON -> other == FIRE;
            case PHYSICAL -> false;
        };
        if (isStrong) {
            return ADVANTAGE_MULTIPLIER;
        }

        return 1.0;
    }
}
