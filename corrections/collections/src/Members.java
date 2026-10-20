import java.util.HashMap;
import java.util.Map;

public class Members {
    private final Map<Integer, String> namesByCard = new HashMap<>();

    public void register(int card, String name) {
        this.namesByCard.put(card, name);
    }

    public String nameOf(int card) {
        return this.namesByCard.getOrDefault(card, "Inconnu");
    }
}
