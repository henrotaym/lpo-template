public class AnnouncedLooter implements Looter {
    private Looter looter;
    private BattleLog log;
    private Lootable source;

    public AnnouncedLooter(Looter looter, BattleLog log, Lootable source) {
        this.looter = looter;
        this.log = log;
        this.source = source;
    }

    public String getName() {
        return this.looter.getName();
    }

    public void collect(Loot loot) {
        this.looter.collect(loot);
        this.log.lootCollected(loot);
    }

    public void equip(Protection shield) {
        this.looter.equip(shield);
        this.log.shieldPickedUp(this.looter, this.source);
    }
}
