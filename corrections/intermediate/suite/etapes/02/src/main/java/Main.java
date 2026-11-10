import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Character> monsters = List.of(new Gobelin(), new Skeleton(), new Troll(), new Golem());
        Chest chest = new Chest("Coffre du donjon", 30, new LootDrop(new Loot("Couronne ancienne", 250)));
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

        if (aria.isAlive()) {
            fight.open(aria, chest);
        }

        IO.println("Or récolté : " + aria.getGold() + " po");
    }
}