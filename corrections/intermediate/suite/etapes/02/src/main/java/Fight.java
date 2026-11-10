public class Fight {
    public void fight(Hero hero, Character monster, DamageCalculator damageCalculator) {
        IO.println("");
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

                int beforeEndOfTurnHealth = monster.getHealth();
                monster.endOfTurn();
                int regeneratedHealth = monster.getHealth() - beforeEndOfTurnHealth;
                if (regeneratedHealth > 0) {
                    IO.println(monster.getName() + " régénère " + regeneratedHealth + " PV. " + monster);
                }
            } else {
                IO.println(monster.getName() + " est vaincu !");
                this.loot(hero, monster);
            }
        }
    }

    public void open(Hero hero, Chest chest) {
        IO.println("");
        IO.println("--- " + chest + " ---");

        while (!chest.isOpen()) {
            chest.takeDamage(hero.getAttackPower());
            IO.println(hero.getName() + " frappe le coffre. " + chest);
        }

        this.loot(hero, chest);
    }

    private void loot(Looter looter, Lootable source) {
        source.getDrop().giveTo(looter, source);
    }
}
