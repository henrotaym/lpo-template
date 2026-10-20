public abstract class Character {
    private String name;
    private int health;
    private int maxHealth;
    private int attackPower;
    private int defense;
    private Protection protection;

    public Character(String name, int maxHealth, int attackPower, int defense, Protection protection) {
        if (name.isEmpty() || name == null) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }

        if (maxHealth <= 0) {
            throw new IllegalArgumentException("MaxHealth value cannot be less than 0.");
        }

        if (attackPower <= 0) {
            throw new IllegalArgumentException("AttackPower value cannot be less than 0.");
        }

        if (defense <= 0) {
            throw new IllegalArgumentException("Defense value cannot be less than 0.");
        }

        if (protection == null) {
            throw new IllegalArgumentException("Protection value cannot be null.");
        }

        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.attackPower = attackPower;
        this.defense = defense;
        this.protection = protection;
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public int getDefense() {
        return this.defense;
    }

    public void takeDamage(int amount) {
        if (amount == 0) {
            return;
        }

        this.health = Math.max(0, this.health - amount);
    }

    public int absorb(int damage) {
        return this.protection.reduce(damage);
    }

    public int getAttackPower() {
        return this.attackPower;
    }

    public String getName() {
        return this.name;
    }

    public abstract String getBattleCry();

    @Override
    public String toString() {
        return this.name + " [" + this.health + "/" + this.maxHealth + " PV, ATK " + this.attackPower + "]";
    }
}