public class Fight {
    private BattleLog log;

    public Fight(BattleLog log) {
        this.log = log;
    }

    public void fight(Hero hero, Character monster, DamageCalculator damageCalculator) {
        this.log.monsterAppeared(monster);

        while (hero.isAlive() && monster.isAlive()) {
            int heroDamage = damageCalculator.computeDamage(hero, monster);
            monster.takeDamage(heroDamage);
            this.log.heroStruck(hero, monster, heroDamage);
            if (monster.isAlive()) {
                int monsterDamage = damageCalculator.computeDamage(monster, hero);
                hero.takeDamage(monsterDamage);
                this.log.monsterStruckBack(monster, hero, monsterDamage);

                int beforeEndOfTurnHealth = monster.getHealth();
                monster.endOfTurn();
                int regeneratedHealth = monster.getHealth() - beforeEndOfTurnHealth;
                if (regeneratedHealth > 0) {
                    this.log.regenerated(monster, regeneratedHealth);
                }
            } else {
                this.log.monsterDefeated(monster);
                hero.recordVictory();
                this.loot(hero, monster);
            }
        }
    }

    public void open(Hero hero, Chest chest) {
        this.log.chestReached(chest);

        while (!chest.isOpen()) {
            chest.takeDamage(hero.getAttackPower());
            this.log.heroHitChest(hero, chest);
        }

        this.loot(hero, chest);
    }

    private void loot(Looter looter, Lootable source) {
        source.getDrop().giveTo(new AnnouncedLooter(looter, this.log, source));
    }
}
