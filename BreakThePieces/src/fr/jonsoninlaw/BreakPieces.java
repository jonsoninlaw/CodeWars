package fr.jonsoninlaw;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BreakPieces {
    public static String[] process(String shape) {
        List<String> brokenPieces = new ArrayList<>();
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
        int maxRow = 0;
        int minRow = Integer.MAX_VALUE;
        int maxCol = 0;
        int minCol = Integer.MAX_VALUE;
        boolean loop = false;
        while (!loop) {
            row += rowMoves[direction];
            col += colMoves[direction];
            if (house[row].charAt(col) == '+') {
                if (coord.size() != 0 && row == coord.getFirst()[0] && col == coord.getFirst()[1]) {
                    loop = true;
                } else {
                    int[] check = checkDirections(direction, house, row, col);
                    if (check[0] != direction) {
                        direction = check[0];
                        coord.add(new int[]{row, col, check[1]});
                    }
                    if (row > maxRow) {
                        maxRow = row;
                    }
                    if (col > maxCol) {
                        maxCol = col;
                    }
                    if (row < minRow) {
                        minRow = row;
                    }
                    if (col < minCol) {
                        minCol = col;
                    }
                }
            }
        }
        coord.add(coord.getFirst());
        brokenPieces.add(createShape(coord, maxRow - minRow + 1, minRow, maxCol - minCol + 1, minCol));

        String[] broken = new String[brokenPieces.size()];
        broken = brokenPieces.toArray(broken);
        return broken;
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

    private static String createShape(LinkedList<int[]> coord, int rowSize, int rowOffset, int colSize, int colOffset) {

        String[][] earlyShape = new String[rowSize][colSize];
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                earlyShape[i][j] = " ";
            }
        }
        int prevRow = coord.get(0)[0] - rowOffset;
        int prevCol = coord.get(0)[1] - colOffset;
        earlyShape[prevRow][prevCol] = "+";
        for (int i = 1; i < coord.size(); i++) {
            int row = coord.get(i)[0] - rowOffset;
            int col = coord.get(i)[1] - colOffset;
            earlyShape[row][col] = "+";
            if (row == prevRow) {
                if (col > prevCol) {
                    for (int j = prevCol + 1; j < col; j++) {
                        earlyShape[row][j] = "-";
                    }
                } else {
                    for (int j = prevCol - 1; j > col; j--) {
                        earlyShape[row][j] = "-";
                    }
                }
            } else {
                if (row > prevRow) {
                    for (int j = prevRow + 1; j < row; j++) {
                        earlyShape[j][col] = "|";
                    }
                } else {
                    for (int j = prevRow - 1; j > row; j--) {
                        earlyShape[j][col] = "|";
                    }
                }
            }
            prevRow = row;
            prevCol = col;
        }

        String[] shape = new String[rowSize];
        for (int i = 0; i < rowSize; i++) {
            shape[i] = String.join("", earlyShape[i]);
        }
        return String.join("\n", shape);
    }
}