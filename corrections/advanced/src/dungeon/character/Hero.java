package dungeon.character;

import dungeon.combat.Element;
import dungeon.combat.NoProtection;
import dungeon.loot.Bag;
import dungeon.loot.Loot;
import dungeon.loot.Potion;

import java.util.ArrayList;
import java.util.List;

public class Hero extends Character {
    private static final int WOUNDED_PERCENT = 30;

    private final Bag<Loot> loot = new Bag<>();
    private final Bag<Potion> potions = new Bag<>();
    private final List<Character> defeated = new ArrayList<>();

    public Hero(String name, int maxHealth, int attackPower, int defense, Element element) {
        super(name, maxHealth, attackPower, defense, element, new NoProtection());
    }

    @Override
    public String getBattleCry() {
        return "En avant !";
    }

    public void store(Loot item) {
        this.loot.add(item);
    }

    public void carry(Potion potion) {
        this.potions.add(potion);
    }

    public void recordVictory(Character monster) {
        this.defeated.add(monster);
    }

    public Bag<Loot> getLoot() {
        return this.loot;
    }

    public Bag<Potion> getPotions() {
        return this.potions;
    }

    public List<Character> getDefeated() {
        return new ArrayList<>(this.defeated);
    }

    @Override
    public void endOfTurn() {
        if (!this.isAlive() || this.potions.isEmpty() || !this.isWounded()) {
            return;
        }
        Potion potion = this.potions.takeFirst();
        this.heal(potion);
        IO.println(this.getName() + " boit " + potion + ". " + this);
    }

    private boolean isWounded() {
        return this.getHealth() * 100 < this.getMaxHealth() * WOUNDED_PERCENT;
    }
}
