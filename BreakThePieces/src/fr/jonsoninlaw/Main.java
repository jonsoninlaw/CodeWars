package fr.jonsoninlaw;

public class Main {

    public static void main(String[] args) {
        String shape = String.join("\n", new String[] {
                "..+----------+",
                "..|..........|",
                "+-+..........|",
                "|............|",
                "+------+-----+",
                "|......|.....|",
                "|......|.....|",
                "+------+-----+"});
        String[] result = BreakPieces.process(shape);
        for (String elt : result) {
            System.out.println(elt);
        }
    }
}
