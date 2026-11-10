public class Hero extends Character implements Looter {
    private int gold;

    public Hero() {
        super("Aria", 200, 20, 4, new NoProtection(), new NoTurnEffect(), new NoDrop());
        this.gold = 0;
    }

    public void collect(Loot loot) {
        this.gold = this.gold + loot.getGoldValue();
    }

    public void equip(Protection protection) {
        this.changeProtection(protection);
    }

    public int getGold() {
        return this.gold;
    }

    @Override
    public String getBattleCry() {
        return "Pour la lumière !";
    }
}
