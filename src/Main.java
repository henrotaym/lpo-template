public class Main {
    public static void main(String[] args) {
        Skeleton john = new Skeleton("John", 150, 100);
        Troll troll = new Troll("troll", 200, 30);
        Hero aria = new Hero("Aria", 120, 10);

        troll.attacked(10);
        troll.endOfTurn();
        IO.println(troll.getHealth());
    }
}
