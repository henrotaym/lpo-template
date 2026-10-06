public class BpostExpressShipping extends BpostStandardShipping {
    @Override
    public int cost(Parcel parcel) {
        return super.cost(parcel) + 5;
    }
}
