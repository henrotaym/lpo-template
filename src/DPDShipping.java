public class DPDShipping implements Shipping {
    public int cost(Parcel parcel) {
        if (parcel.getSize() <= 2000) {
            return 7;
        }

        if (parcel.getSize() <= 5000) {
            return 12;
        }

        return 17;
    }
}
