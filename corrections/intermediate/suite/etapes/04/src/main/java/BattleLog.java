public interface BattleLog {
    void heroEntered(Hero hero);

    void monsterAppeared(Character monster);

    void heroStruck(Hero hero, Character monster, int damage);

    void monsterRetaliated(Character monster, Hero hero, int damage);

    void regenerated(Character character, int amount);

    void monsterDefeated(Character monster);

    void heroFell(Hero hero);

    void chestReached(Chest chest);

    void chestStruck(Hero hero, Chest chest);

    void explorationEnded(int victories, int monsterCount, int gold);
}
