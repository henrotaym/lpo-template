public class Loot {
    private String name;
    private int goldValue;

    public Loot(String name, int goldValue) {
        if (name.isEmpty() || name == null) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }

        if (goldValue <= 0) {
            throw new IllegalArgumentException("Gold value cannot be less than 0.");
        }

        this.name = name;
        this.goldValue = goldValue;
    }

    public String getName() {
        return this.name;
    }

    public int getGoldValue() {
        return this.goldValue;
    }

    @Override
    public String toString() {
        return this.name + " (" + this.goldValue + " po)";
    }
}