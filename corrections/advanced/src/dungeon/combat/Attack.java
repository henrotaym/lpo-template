package dungeon.combat;

public record Attack(Element element, int power, boolean critical) {
    public Attack {
        if (element == null) {
            throw new IllegalArgumentException("Une attaque doit avoir un élément.");
        }
        if (power < 0) {
            throw new IllegalArgumentException("La puissance d'une attaque ne peut pas être négative.");
        }
    }
}
