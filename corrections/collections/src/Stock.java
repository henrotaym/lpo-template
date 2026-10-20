import java.util.Map;
import java.util.TreeMap;

public class Stock {
    private final Map<String, Integer> copiesByTitle = new TreeMap<>();

    public void add(String title, int copies) {
        this.copiesByTitle.put(title, this.available(title) + copies);
    }

    public int available(String title) {
        return this.copiesByTitle.getOrDefault(title, 0);
    }

    public boolean borrow(String title) {
        if (this.available(title) == 0) {
            return false;
        }
        this.copiesByTitle.put(title, this.available(title) - 1);
        return true;
    }

    public void giveBack(String title) {
        this.add(title, 1);
    }

    @Override
    public String toString() {
        return this.copiesByTitle.toString();
    }
}
