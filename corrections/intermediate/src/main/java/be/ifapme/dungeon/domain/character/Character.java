package be.ifapme.dungeon.domain.character;

import be.ifapme.dungeon.domain.behavior.Healable;
import be.ifapme.dungeon.domain.behavior.Protection;
import be.ifapme.dungeon.domain.behavior.TurnEffect;
import be.ifapme.dungeon.domain.loot.Drop;
import be.ifapme.dungeon.domain.loot.Lootable;

public abstract class Character implements Healable, Lootable {
    private final String name;
    private final int maxHealth;
    private final int attackPower;
    private final int defense;
    private final TurnEffect turnEffect;
    private final Drop drop;
    private Protection protection;
    private int health;

    protected Character(String name, int maxHealth, int attackPower, int defense, Protection protection, TurnEffect turnEffect, Drop drop) {
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
        if (turnEffect == null) {
            throw new IllegalArgumentException("Un personnage doit avoir un effet de fin de tour.");
        }
        if (drop == null) {
            throw new IllegalArgumentException("Un personnage doit savoir ce qu'il laisse.");
        }
        this.name = name;
        this.maxHealth = maxHealth;
        this.attackPower = attackPower;
        this.defense = defense;
        this.protection = protection;
        this.turnEffect = turnEffect;
        this.drop = drop;
        this.health = maxHealth;
    }

    public abstract String getBattleCry();

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Drop getDrop() {
        return this.drop;
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

    public int absorb(int damage) {
        return this.protection.reduce(damage);
    }

    public void changeProtection(Protection protection) {
        if (protection == null) {
            throw new IllegalArgumentException("Un personnage doit avoir une protection.");
        }
        this.protection = protection;
    }

    @Override
    public boolean isAlive() {
        return this.health > 0;
    }

    @Override
    public boolean isWounded() {
        return this.health < this.maxHealth;
    }

    public void takeDamage(int amount) {
        if (amount <= 0) {
            return;
        }
        this.health = Math.max(0, this.health - amount);
    }

    @Override
    public void heal(int amount) {
        if (amount <= 0) {
            return;
        }
        this.health = Math.min(this.maxHealth, this.health + amount);
    }

    public void endOfTurn() {
        this.turnEffect.apply(this);
    }

    @Override
    public String toString() {
        return this.name + " [" + this.health + "/" + this.maxHealth + " PV, ATK " + this.attackPower + "]";
    }
}
