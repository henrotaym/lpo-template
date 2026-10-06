import java.util.List;

public class Main {
    public static void main(String[] args) {
        Item tShirt = new Item("t-shirt", 25, 100, 60);
        Parcel parcel = new Parcel(tShirt.getWeight(), tShirt.getSize());
        BpostExpressShipping bpost = new BpostExpressShipping();
        DPDShipping dpd = new DPDShipping();
        BpostExpressShipping bpostExpress = new BpostExpressShipping();
        HermesShipping hermes = new HermesShipping();

        List<Shipping> shippings = List.of(bpost, dpd, bpostExpress, hermes);

        for (Shipping shipping : shippings) {
            shipping.cost(parcel);   
        }

        IO.println(bpost.cost(parcel));
        IO.println(dpd.cost(parcel));
    }
}
