import java.util.List;

public class Main {
    public static void main(String[] args) {
        showHistory();
        showVisitors();
        showStock();
    }

    private static void showHistory() {
        LoanHistory history = new LoanHistory();
        for (String title : List.of("Dune", "Le Hobbit", "Dune", "Fondation")) {
            history.add(title);
        }

        IO.println("=== Historique d'Alice ===");
        IO.println("Emprunts : " + history.getTitles());
        IO.println("Nombre d'emprunts : " + history.size());
        IO.println("Premier : " + history.first());
        IO.println("Dernier : " + history.last());
        IO.println("Dune emprunté " + history.countOf("Dune") + " fois");
        IO.println("Par titre : " + history.countByTitle());
    }

    private static void showVisitors() {
        Members members = new Members();
        members.register(1042, "Alice");
        members.register(2077, "Bruno");
        members.register(3150, "Chloé");

        DailyVisitors visitors = new DailyVisitors();

        IO.println("");
        IO.println("=== Visiteurs du jour ===");
        for (int card : List.of(1042, 2077, 1042, 3150, 2077, 1042)) {
            if (visitors.scan(card)) {
                IO.println("Badge " + card + " : entrée comptée");
            } else {
                IO.println("Badge " + card + " : déjà compté");
            }
        }
        IO.println("Passages scannés : " + visitors.scanCount());
        IO.println("Visiteurs différents : " + visitors.visitorCount());
        IO.println("Chloé est venue : " + visitors.hasVisited(3150));
        IO.println("Le badge 9999 est venu : " + visitors.hasVisited(9999));
        IO.println("Ordre d'arrivée : " + visitors.namesInArrivalOrder(members));
        IO.println("Badge 4000 : " + members.nameOf(4000));
    }

    private static void showStock() {
        Stock stock = new Stock();
        stock.add("Le Hobbit", 1);
        stock.add("Dune", 3);
        stock.add("Fondation", 2);

        IO.println("");
        IO.println("=== Stock ===");
        IO.println("Stock : " + stock);
        borrow(stock, "Dune");
        borrow(stock, "Le Hobbit");
        borrow(stock, "Le Hobbit");
        stock.giveBack("Fondation");
        IO.println("Retour de Fondation");
        IO.println("Exemplaires de Alien : " + stock.available("Alien"));
        IO.println("Stock : " + stock);
    }

    private static void borrow(Stock stock, String title) {
        if (stock.borrow(title)) {
            IO.println("Emprunt de " + title + " : accepté");
            return;
        }
        IO.println("Emprunt de " + title + " : refusé, plus d'exemplaire");
    }
}
