public abstract class Character {
    private String name;
    private int health;
    private int maxHealth;
    private int attack;

    public Character(String name, int maxHealth, int attack) {
        this.name = name;
        this.health = maxHealth;
        this.maxHealth = maxHealth;
        this.attack = attack;
    }

    public String getName() {
        return this.name;
    }

    protected void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return this.health;
    }

    public int getAttack() {
        return this.attack;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    protected int getHeal() {
        return 0;
    }

    public int healed() {
        this.health = this.health + this.getHeal();

        if (this.health > this.maxHealth) {
            this.health = this.maxHealth;
        }

        return this.health;
    }

    public int attacked(int attack) {
        this.health = this.health - attack;

        if (health < 0) {
            this.health = 0;
        }

        return this.health;
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public boolean isHurted() {
        return this.health < this.maxHealth;
    }

    public String toString() {
        return this.getName() + " [" + this.getHealth() + "/" + this.getMaxHealth() + " PV, ATK "
                + this.getAttack() + "]";
    }
}
