public class ShieldDrop implements Drop {
    private Protection protection;

    public ShieldDrop(Protection protection) {
        if (protection == null) {
            throw new IllegalArgumentException("Protection value cannot be null.");
        }

        this.protection = protection;
    }

    public void giveTo(Looter hero, Lootable monster) {
        hero.equip(this.protection);
        IO.println(hero.getName() + "ramasse le bouclier de " + monster.getName());
    }
}
