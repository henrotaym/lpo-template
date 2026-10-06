public class Item {
    private String name;
    private int price;
    private int weight;
    private int size;

    public Item(String name, int price, int weight, int size) {
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.size = size;
    }

    public String getName() {
        return this.name;
    }

    public int getPrice() {
        return this.price;
    }

    public int getWeight() {
        return this.weight;
    }

    public int getSize() {
        return this.size;
    }
}
