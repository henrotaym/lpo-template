import java.util.List;

public class Main {
    public static void main(String[] args) {
        // stock
        Item tShirt = new Item("t-shirt", 25, 100, 60);
        Item jeans = new Item("jeans", 80, 200, 100);

        // commande temporaire
        Order order = new Order();

        int jeansQuantity = Integer.parseInt(IO.readln("Combien de jeans? "));
        order.add(jeans, jeansQuantity);
        int tShirtQuantity = Integer.parseInt(IO.readln("Combien de tshirt? "));
        order.add(tShirt, tShirtQuantity);

        // Exo
        // transformer ceci en un choix multiple personnalisable et réutilisable sur d'autres
        // options
        // fournir une liste et un sujet (livraison, action, ...) et avoir en sortie l'option
        // choisie
        List<Shipping> shippings = List.of(new BpostStandardShipping(), new DPDShipping(),
                new BpostExpressShipping(), new HermesShipping());
        IO.println("Voici les différentes options de livraison: ");
        for (int i = 0; i < shippings.size(); i++) {
            IO.println(i + " - " + shippings.get(i).toString());
        }
        int choiceKey = Integer.parseInt(IO.readln("Quel est le choix du shipping?"));
        Shipping chosenShipping = shippings.get(choiceKey);
        // fin exo

        order.setShipping(chosenShipping);

        IO.println("Votre commande est enregistrée");
        IO.println("Montant total: " + order.amount() + "€");
    }
}
