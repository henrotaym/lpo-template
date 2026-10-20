import java.util.Set;
import java.util.TreeSet;

public class BookGenres {
    private final Set<String> genres = new TreeSet<>();

    public boolean add(String genre) {
        return this.genres.add(genre);
    }

    public boolean has(String genre) {
        return this.genres.contains(genre);
    }

    public Set<String> getGenres() {
        return Set.copyOf(this.genres);
    }

    @Override
    public String toString() {
        return this.genres.toString();
    }
}
