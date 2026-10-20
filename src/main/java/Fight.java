public class Fight {
    public void fight(Hero hero, Character monster, DamageCalculator damageCalculator) {
        IO.println("--- " + monster + " apparaît ---");
        IO.println(monster.getName() + " : " + monster.getBattleCry());

        while (hero.isAlive() && monster.isAlive()) {
            int heroDamage = damageCalculator.computeDamage(hero, monster);
            monster.takeDamage(heroDamage);
            IO.println(hero.getName() + " frappe " + monster.getName() + " pour " + heroDamage + " dégâts. "
                    + monster);
            if (monster.isAlive()) {
                int monsterDamage = damageCalculator.computeDamage(monster, hero);
                hero.takeDamage(monsterDamage);
                IO.println(monster.getName() + " riposte pour " + monsterDamage + " dégâts. " + hero);
            } else {
                IO.println(monster.getName() + " est vaincu !");
            }
        }
    }
}
