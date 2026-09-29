public class Skeleton extends Character {
    public Skeleton(String name, int maxHealth, int attack) {
        super(name, maxHealth, attack);
    }

    @Override
    public int attacked(int attack) {
        int realAttack = (int) Math.floor(attack / 2);

        return super.attacked(realAttack);
    }
}
