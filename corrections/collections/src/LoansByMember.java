import java.util.Map;
import java.util.TreeMap;

public class LoansByMember {
    private final Map<String, Integer> countsByName = new TreeMap<>();

    public void record(String memberName) {
        this.countsByName.put(memberName, this.countOf(memberName) + 1);
    }

    public int countOf(String memberName) {
        return this.countsByName.getOrDefault(memberName, 0);
    }

    @Override
    public String toString() {
        return this.countsByName.toString();
    }
}
