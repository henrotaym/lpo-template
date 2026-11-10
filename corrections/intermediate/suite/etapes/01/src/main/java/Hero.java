public class Hero extends Character implements Looter {
    private int gold;

    public Hero() {
        super("Aria", 200, 20, 4, new NoProtection(), new NoTurnEffect(), new NoDrop());
    }

    @Override
    public String getBattleCry() {
        return "Pour la lumière !";
    }

    @Override
    public void collect(Loot loot) {
        this.gold += loot.getGoldValue();
    }

    @Override
    public void equip(Protection shield) {
        this.changeProtection(shield);
    }

    public int getGold() {
        return this.gold;
    }
}
