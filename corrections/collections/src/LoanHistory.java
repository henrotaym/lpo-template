import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class LoanHistory {
    private final List<String> titles = new ArrayList<>();

    public void add(String title) {
        this.titles.add(title);
    }

    public int size() {
        return this.titles.size();
    }

    public String first() {
        return this.titles.get(0);
    }

    public String last() {
        return this.titles.get(this.titles.size() - 1);
    }

    public int countOf(String title) {
        int count = 0;
        for (String borrowed : this.titles) {
            if (borrowed.equals(title)) {
                count++;
            }
        }
        return count;
    }

    public Map<String, Integer> countByTitle() {
        Map<String, Integer> counts = new TreeMap<>();
        for (String title : this.titles) {
            counts.put(title, counts.getOrDefault(title, 0) + 1);
        }
        return counts;
    }

    public List<String> getTitles() {
        return List.copyOf(this.titles);
    }
}
