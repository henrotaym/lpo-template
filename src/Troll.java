public class Troll extends Character {
    public Troll(String name, int maxHealth, int attack) {
        super(name, maxHealth, attack);
    }

    @Override
    public int getHeal() {
        return 5;
    }

    public void endOfTurn() {
        if (this.isAlive() && this.isHurted()) {
            this.healed();
        }
    }
}
