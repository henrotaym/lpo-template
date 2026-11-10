import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Character> monsters = List.of(new Gobelin(), new Skeleton(), new Troll(), new Golem());
        Chest chest = new Chest("Coffre du donjon", 30, new LootDrop(new Loot("Couronne ancienne", 250)));
        Hero aria = new Hero();
        BattleLog log = new ConsoleBattleLog();
        Fight fight = new Fight(log);
        DamageCalculator damageCalculator = new DamageCalculator();

        log.heroEntered(aria);

        for (Character monster : monsters) {
            fight.fight(aria, monster, damageCalculator);

            if (!aria.isAlive()) {
                log.heroFell(aria);
                break;
            }
        }

        if (aria.isAlive()) {
            fight.open(aria, chest);
        }

        log.explorationEnded(aria.getVictories(), monsters.size(), aria.getGold());
    }
}