import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Loot chest = new Loot("chest", 30);
        List<Character> monsters = List.of(new Gobelin(), new Skeleton(), new Troll(), new Golem());
        Hero aria = new Hero();
        Fight fight = new Fight();
        DamageCalculator damageCalculator = new DamageCalculator();

        IO.println(aria + " entre dans le donjon.");

        for (Character monster : monsters) {
            fight.fight(aria, monster, damageCalculator);

            if (!aria.isAlive()) {
                IO.println("Aria est morte. " + aria);
                break;
            }
        }
    }
}