package be.ifapme.dungeon.domain.behavior;

class FakeHealable implements Healable {
    private final boolean alive;
    private final boolean wounded;
    private int healed;

    FakeHealable(boolean alive, boolean wounded) {
        this.alive = alive;
        this.wounded = wounded;
    }

    @Override
    public boolean isAlive() {
        return this.alive;
    }

    @Override
    public boolean isWounded() {
        return this.wounded;
    }

    @Override
    public void heal(int amount) {
        this.healed += amount;
    }

    int getHealed() {
        return this.healed;
    }
}
