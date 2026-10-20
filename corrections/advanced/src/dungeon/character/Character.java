package dungeon.character;

import dungeon.combat.Attack;
import dungeon.combat.Damageable;
import dungeon.combat.Element;
import dungeon.combat.Protection;
import dungeon.loot.Potion;

public abstract class Character implements Damageable, Comparable<Character> {
    private final String name;
    private final int maxHealth;
    private final int attackPower;
    private final int defense;
    private final Element element;
    private final Protection protection;
    private int health;

    protected Character(String name, int maxHealth, int attackPower, int defense, Element element, Protection protection) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Le nom d'un personnage ne peut pas être vide.");
        }
        if (maxHealth <= 0) {
            throw new IllegalArgumentException("Les points de vie maximum doivent être positifs.");
        }
        if (attackPower < 0) {
            throw new IllegalArgumentException("La puissance d'attaque ne peut pas être négative.");
        }
        if (defense < 0) {
            throw new IllegalArgumentException("La défense ne peut pas être négative.");
        }
        if (protection == null) {
            throw new IllegalArgumentException("Un personnage doit avoir une protection.");
        }
        this.name = name;
        this.maxHealth = maxHealth;
        this.attackPower = attackPower;
        this.defense = defense;
        this.element = element == null ? Element.PHYSICAL : element;
        this.protection = protection;
        this.health = maxHealth;
    }

    public abstract String getBattleCry();

    public Attack attack(boolean critical) {
        return new Attack(this.element, this.attackPower, critical);
    }

    @Override
    public String getName() {
        return this.name;
    }

    public int getHealth() {
        return this.health;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public int getAttackPower() {
        return this.attackPower;
    }

    public int getDefense() {
        return this.defense;
    }

    public Element getElement() {
        return this.element;
    }

    public int absorb(int damage) {
        return this.protection.reduce(damage);
    }

    @Override
    public boolean isAlive() {
        return this.health > 0;
    }

    @Override
    public void takeDamage(int amount) {
        if (amount <= 0) {
            return;
        }
        this.health = Math.max(0, this.health - amount);
    }

    public void heal(int amount) {
        if (amount <= 0) {
            return;
        }
        this.health = Math.min(this.maxHealth, this.health + amount);
    }

    public void heal(Potion potion) {
        this.heal(potion.healing());
    }

    public void endOfTurn() {
    }

    @Override
    public int compareTo(Character other) {
        return Integer.compare(this.health, other.health);
    }

    @Override
    public String toString() {
        return this.name + " [" + this.health + "/" + this.maxHealth + " PV, ATK " + this.attackPower + "] ~ " + this.element.getLabel();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Character character)) {
            return false;
        }
        return this.name.equals(character.name);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}
