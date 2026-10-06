public abstract class BpostStandardShipping implements Shipping {
    public int cost(Parcel parcel) {
        if (parcel.getWeight() <= 2000) {
            return 5;
        }

        if (parcel.getWeight() <= 5000) {
            return 10;
        }

        return 15;
    }
}
