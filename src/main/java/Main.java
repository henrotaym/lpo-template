public class Main {
    public static void main(String[] args) {
        // Loot chest = new Loot("chest", 30);
        Golem golem = new Golem();
        Hero aria = new Hero();
        Fight fight = new Fight();
        DamageCalculator damageCalculator = new DamageCalculator();

        IO.println(aria + " entre dans le donjon.");

        fight.fight(aria, golem, damageCalculator);
    }
}