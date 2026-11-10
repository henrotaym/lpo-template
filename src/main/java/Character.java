public abstract class Character implements Healable, Lootable {
    private String name;
    private int health;
    private int maxHealth;
    private int attackPower;
    private int defense;
    private Protection protection;
    private TurnEffect turnEffect;
    private Drop drop;

    public Character(String name, int maxHealth, int attackPower, int defense, Protection protection,
            TurnEffect turnEffect, Drop drop) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }

        if (maxHealth <= 0) {
            throw new IllegalArgumentException("MaxHealth value cannot be less than 0.");
        }

        if (attackPower < 0) {
            throw new IllegalArgumentException("AttackPower value cannot be less than 0.");
        }

        if (defense < 0) {
            throw new IllegalArgumentException("Defense value cannot be less than 0.");
        }

        if (protection == null) {
            throw new IllegalArgumentException("Protection value cannot be null.");
        }

        if (turnEffect == null) {
            throw new IllegalArgumentException("Turn effect value cannot be null.");
        }

        if (drop == null) {
            throw new IllegalArgumentException("Drop value cannot be null.");
        }

        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.attackPower = attackPower;
        this.defense = defense;
        this.protection = protection;
        this.turnEffect = turnEffect;
        this.drop = drop;
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public boolean isWounded() {
        return this.health < this.maxHealth;
    }

    public void heal(int amount) {
        if (amount <= 0) {
            return;
        }

        this.health = Math.min(this.maxHealth, this.health + amount);
    }

    public int getDefense() {
        return this.defense;
    }

    public void takeDamage(int amount) {
        if (amount <= 0) {
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

    public int getHealth() {
        return this.health;
    }

    public TurnEffect getTurnEffect() {
        return this.turnEffect;
    }

    public void endOfTurn() {
        this.turnEffect.apply(this);
    }

    public Drop getDrop() {
        return this.drop;
    }

    public void changeProtection(Protection protection) {
        this.protection = protection;
    }

    public abstract String getBattleCry();

    @Override
    public String toString() {
        return this.name + " [" + this.health + "/" + this.maxHealth + " PV, ATK " + this.attackPower + "]";
    }
}