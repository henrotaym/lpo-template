public class Character {
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

    public int getHealth() {
        return this.health;
    }

    public int getAttack() {
        return this.attack;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public String toString() {
        return this.getName() + " [" + this.getHealth() + "/" + this.getMaxHealth() + " PV, ATK "
                + this.getAttack() + "]";
    }
}
