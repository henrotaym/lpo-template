import java.util.List;

public class Choices {
    public static void main(String[] args) {
        BookGenres genres = new BookGenres();
        for (String genre : List.of("roman", "policier", "roman", "jeunesse")) {
            IO.println("Genre " + genre + " : " + (genres.add(genre) ? "ajouté" : "déjà présent"));
        }
        IO.println("Genres : " + genres);

        LoansByMember loans = new LoansByMember();
        for (String member : List.of("Chloé", "Alice", "Bruno", "Alice", "Chloé", "Alice")) {
            loans.record(member);
        }
        IO.println("Emprunts par membre : " + loans);
    }
}
