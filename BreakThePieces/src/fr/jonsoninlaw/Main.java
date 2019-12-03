package fr.jonsoninlaw;

public class Main {

    public static void main(String[] args) {
        String shape = String.join("\n", new String[] {
                "           +-+             ",
                "           | |             ",
                "         +-+-+-+           ",
                "         |     |           ",
                "      +--+-----+--+        ",
                "      |           |        ",
                "   +--+-----------+--+     ",
                "   |                 |     ",
                "   +-----------------+     "});

        String shape2 = String.join("\n", new String[] {
                "+-------------------+--+",
                "|                   |  |",
                "|                   |  |",
                "|  +----------------+  |",
                "|  |                   |",
                "|  |                   |",
                "+--+-------------------+"});

        String shape3 = String.join("\n", new String[] {
                "+------------+",
                "|            |",
                "|            |",
                "|            |",
                "+------+-----+",
                "|      |     |",
                "|      |     |",
                "+------+-----+"});

        String[] result = BreakPieces.process(shape3);
        for (String elt : result) {
            System.out.println(elt);
        }
    }
}
