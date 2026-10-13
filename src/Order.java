import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<Item> items;
    private Shipping shipping;

    public Order() {
        this.items = new ArrayList<>();
    }

    public void add(Item item, int quantity) {
        for (int i = 0; i < quantity; i++) {
            this.items.add(item);
        }
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    public int total() {
        int total = 0;
        for (Item item : this.items) {
            total = total + item.getPrice();
        }

        return total;
    }

    public Parcel parcel() {
        int totalWeight = 0;
        int totalSize = 0;

        for (Item item : items) {
            totalWeight = totalWeight + item.getWeight();
            totalSize = totalSize + item.getSize();
        }

        return new Parcel(totalWeight, totalSize);
    }

    public int amount() {
        return this.total() + this.shipping.cost(this.parcel());
    }
}
