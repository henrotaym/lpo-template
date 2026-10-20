package dungeon.character;

import dungeon.combat.Element;
import dungeon.combat.HalvingProtection;

public class Skeleton extends Character {
    public Skeleton() {
        super("Squelette", 55, 9, 3, Element.PHYSICAL, new HalvingProtection());
    }

    @Override
    public String getBattleCry() {
        return "Clac clac clac.";
    }
}
