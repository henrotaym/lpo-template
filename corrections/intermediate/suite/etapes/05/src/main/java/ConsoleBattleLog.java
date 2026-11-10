public class ConsoleBattleLog implements BattleLog {
    public void heroEntered(Hero hero) {
        IO.println(hero + " entre dans le donjon.");
    }

    public void monsterAppeared(Character monster) {
        IO.println("");
        IO.println("--- " + monster + " apparaît ---");
        IO.println(monster.getName() + " : " + monster.getBattleCry());
    }

    public void heroStruck(Hero hero, Character monster, int damage) {
        IO.println(hero.getName() + " frappe " + monster.getName() + " pour " + damage + " dégâts. " + monster);
    }

    public void monsterRetaliated(Character monster, Hero hero, int damage) {
        IO.println(monster.getName() + " riposte pour " + damage + " dégâts. " + hero);
    }

    public void regenerated(Character character, int amount) {
        IO.println(character.getName() + " régénère " + amount + " PV. " + character);
    }

    public void monsterDefeated(Character monster) {
        IO.println(monster.getName() + " est vaincu !");
    }

    public void heroFell(Hero hero) {
        IO.println(hero.getName() + " est morte. " + hero);
    }

    public void lootCollected(Loot loot) {
        IO.println("Butin : " + loot);
    }

    public void shieldPickedUp(Looter looter, Lootable source) {
        IO.println(looter.getName() + " ramasse le bouclier de " + source.getName() + ".");
    }

    public void chestReached(Chest chest) {
        IO.println("");
        IO.println("--- " + chest + " ---");
    }

    public void chestStruck(Hero hero, Chest chest) {
        IO.println(hero.getName() + " frappe le coffre. " + chest);
    }

    public void explorationEnded(int victories, int monsterCount, int gold) {
        IO.println("");
        IO.println("=== Fin de l'exploration ===");
        IO.println("Monstres vaincus : " + victories + " / " + monsterCount);
        IO.println("Or récolté : " + gold + " po");
    }
}
