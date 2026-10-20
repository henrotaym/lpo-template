import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class DailyVisitors {
    private final List<Integer> scans = new ArrayList<>();
    private final Set<Integer> visitors = new LinkedHashSet<>();

    public boolean scan(int card) {
        this.scans.add(card);
        return this.visitors.add(card);
    }

    public int scanCount() {
        return this.scans.size();
    }

    public int visitorCount() {
        return this.visitors.size();
    }

    public boolean hasVisited(int card) {
        return this.visitors.contains(card);
    }

    public List<String> namesInArrivalOrder(Members members) {
        List<String> names = new ArrayList<>();
        for (int card : this.visitors) {
            names.add(members.nameOf(card));
        }
        return names;
    }
}
