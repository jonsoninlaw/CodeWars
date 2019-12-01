package fr.jonsoninlaw;

import java.util.LinkedList;

public class BreakPieces {
    public static String[] process(String shape) {
        String[] house = shape.split("\n");
        LinkedList<int[]> coord = new LinkedList<>();
        // directions : "right", "down", "left", "up"
        int[] rowMoves = {0, 1, 0, -1};
        int[] colMoves = {1, 0, -1, 0};
        boolean found = false;
        for (int i = 0; i < house.length; i++) {
            for (int j = 0; j < house[i].length(); j++) {
                if (house[i].charAt(j) == '+') {
                    coord.add(new int[] {i, j, 1});
                    found = true;
                    break;
                }
            }
            if (found) {
                break;
            }
        }
        int direction = 0;
        int row = coord.getLast()[0];
        int col = coord.getLast()[1];
        boolean loop = false;
        while (!loop) {
            row += rowMoves[direction];
            col += colMoves[direction];
            if (house[row].charAt(col) == '+') {
                if (coord.size() != 0 && row == coord.getFirst()[0] && col == coord.getFirst()[1]) {
                    loop = true;
                } else {
                    int[] check = checkDirections(direction, house, row, col);
                    direction = check[0];
                    coord.add(new int[]{row, col, check[1]});
                }
            }
        }


        return new String[] {"No problemo"};
    }

    public static int[] checkDirections(int direction, String[] house, int row, int col) {
        // directions : "right", "down", "left", "up"
        /*String[][] directions =   {{"down", "right", "up"},
                                    {"left", "down", "right"},
                                    {"up", "left", "down"},
                                    {"right", "up", "left"}};*/
        int[][] directions =    {{1, 0, 3},
                                {2, 1, 0},
                                {3, 2, 1},
                                {0, 3, 2}};
        int[][] rowMoves =  {{1, 0, -1},
                            {0, 1, 0},
                            {-1, 0, 1},
                            {0, -1, 0}};
        int[][] colMoves =  {{0, 1, 0},
                            {-1, 0, 1},
                            {0, -1, 0},
                            {1, 0, -1}};
        int delete = 1;

        boolean found = false;
        int directionCount = 0;

        for (int i = 0; i < 3; i++) {
            try {
                int currentRow = row + rowMoves[direction][i];
                int currentCol = col + colMoves[direction][i];
                if (house[currentRow].charAt(currentCol) == '|' || house[currentRow].charAt(currentCol) == '-') {
                    if (!found) {
                        direction = directions[direction][i];
                        found = true;
                    }
                    directionCount++;
                }
            } catch (Exception e) {}
        }

        if (directionCount > 1) {
            delete = 0;
        }
        return new int[] {direction, delete};
    }
}