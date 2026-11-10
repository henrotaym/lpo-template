import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Character> monsters = List.of(new Gobelin(), new Skeleton(), new Troll(), new Golem());
        Hero aria = new Hero();
        Fight fight = new Fight();
        DamageCalculator damageCalculator = new DamageCalculator();
        Chest chest = new Chest("Coffre du donjon", 30, new LootDrop(new Loot("couronne ancienne", 250)));

        IO.println(aria + " entre dans le donjon.");

        for (Character monster : monsters) {
            if (!aria.isAlive()) {
                IO.println("Aria est morte. " + aria);
                break;
            }

            fight.fight(aria, monster, damageCalculator);
        }

        fight.open(aria, chest);

        IO.println("=== Fin de l'exploration ===");
        IO.println("Or récolté : " + aria.getGold() + " po");
    }
}