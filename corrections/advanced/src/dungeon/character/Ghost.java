package dungeon.character;

import dungeon.combat.Element;
import dungeon.combat.NoProtection;

public class Ghost extends Character implements Flying {
    private boolean intangible;

    public Ghost() {
        super("Fantôme", 45, 10, 1, Element.ICE, new NoProtection());
    }

    @Override
    public String getBattleCry() {
        return "Hoooou...";
    }

    @Override
    public void takeDamage(int amount) {
        this.intangible = !this.intangible;
        if (this.intangible) {
            IO.println("Le coup traverse le Fantôme !");
            return;
        }
        super.takeDamage(amount);
    }
}
