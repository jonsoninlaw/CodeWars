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
        int[] ROW_MOVES = {0, 1, 0, -1};
        int[] COL_MOVES = {1, 0, -1, 0};
        while (true){
            coord.clear();
            boolean found = false;
            for (int row = 0; row < house.length; row++) {
                for (int col = 0; col < house[row].length(); col++) {
                    if (house[row].charAt(col) == '+' || house[row].charAt(col) == 'O') {
                        coord.add(new int[] {row, col, 1});
                        //house[row] = house[row].substring(0, col) + " " + house[row].substring(col + 1, house[row].length());
                        found = true;
                        break;
                    }
                }
                if (found) {
                    break;
                }
            }
            if (!found) {
                break;
            }
            found = false;
            int direction = 0;
            int row = coord.getLast()[0];
            int col = coord.getLast()[1];
            int maxRow = 0;
            int minRow = Integer.MAX_VALUE;
            int maxCol = 0;
            int minCol = Integer.MAX_VALUE;
            boolean loop = false;
            while (!loop) {
                row += ROW_MOVES[direction];
                col += COL_MOVES[direction];
                if (house[row].charAt(col) == '+' || house[row].charAt(col) == 'o' || house[row].charAt(col) == 'O') {
                    if (coord.size() != 0 && row == coord.getFirst()[0] && col == coord.getFirst()[1]) {
                        loop = true;
                        house[row] = house[row].substring(0, col) + "o" + house[row].substring(col + 1, house[row].length());
                    } else {
                        int[] check = checkDirections(direction, house, row, col);

                        if (house[row].charAt(col) == 'O' && checkThrough(house, row, col, direction)) {
                            house[row] = house[row].substring(0, col) + "o" + house[row].substring(col + 1, house[row].length());
                        }
                        else if (check[0] != direction) {
                            if (check[1] == 2) {
                                if (check[2] == 1) {
                                    switch (direction) {
                                        case 0:
                                            house[row] = house[row].substring(0, col - 1) + " O" + house[row].substring(col + 1, house[row].length());
                                            break;
                                        case 1:
                                            house[row] = house[row].substring(0, col) + "O" + house[row].substring(col + 1, house[row].length());
                                            house[row - 1] = house[row - 1].substring(0, col) + " " + house[row - 1].substring(col + 1, house[row - 1].length());
                                            break;
                                        case 2:
                                            house[row] = house[row].substring(0, col) + "O " + house[row].substring(col + 2, house[row].length());
                                            break;
                                        case 3:
                                            house[row + 1] = house[row + 1].substring(0, col) + " " + house[row + 1].substring(col + 1, house[row + 1].length());
                                            house[row] = house[row].substring(0, col) + "O" + house[row].substring(col + 1, house[row].length());
                                            break;
                                    }
                                } else {
                                    switch (check[0]) {
                                        case 0:
                                            house[row] = house[row].substring(0, col) + "O " + house[row].substring(col + 2, house[row].length());
                                            break;
                                        case 1:
                                            house[row] = house[row].substring(0, col) + "O" + house[row].substring(col + 1, house[row].length());
                                            house[row + 1] = house[row + 1].substring(0, col) + " " + house[row + 1].substring(col + 1, house[row + 1].length());
                                            break;
                                        case 2:
                                            house[row] = house[row].substring(0, col - 1) + " O" + house[row].substring(col + 1, house[row].length());
                                            break;
                                        case 3:
                                            house[row - 1] = house[row - 1].substring(0, col) + " " + house[row - 1].substring(col + 1, house[row - 1].length());
                                            house[row] = house[row].substring(0, col) + "O" + house[row].substring(col + 1, house[row].length());
                                            break;
                                    }
                                }
                            }
                            direction = check[0];
                            coord.add(new int[]{row, col, check[1], check[2]});
                        }
                        if (check[1] == 1) {
                            house[row] = house[row].substring(0, col) + "o" + house[row].substring(col + 1, house[row].length());
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
            house = updateHouse(coord, house);
        }

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
        int[][] DIRECTIONS =    {{1, 0, 3},
                                {2, 1, 0},
                                {3, 2, 1},
                                {0, 3, 2}};
        int[][] ROW_MOVES =  {{1, 0, -1},
                            {0, 1, 0},
                            {-1, 0, 1},
                            {0, -1, 0}};
        int[][] COL_MOVES =  {{0, 1, 0},
                            {-1, 0, 1},
                            {0, -1, 0},
                            {1, 0, -1}};

        boolean found = false;
        int cross = 0;
        int nextDirection = 0;
        int straight = 0;

        for (int i = 0; i < 3; i++) {
            try {
                int currentRow = row + ROW_MOVES[direction][i];
                int currentCol = col + COL_MOVES[direction][i];
                if (house[currentRow].charAt(currentCol) == '|' || house[currentRow].charAt(currentCol) == '-') {
                    if (!found) {
                        nextDirection = DIRECTIONS[direction][i];
                        found = true;
                    }
                    if (direction == DIRECTIONS[direction][i]) {
                        straight = 1;
                    }
                    cross++;
                }
            } catch (Exception e) {}
        }

        return new int[] {nextDirection, cross, straight};
    }

    private static boolean checkThrough(String[] house, int row, int col, int direction) {
        boolean through = false;
        try {
            switch (direction) {
                case 0:
                    if (house[row].charAt(col + 2) == '-' || house[row].charAt(col + 2) == 'O' || house[row].charAt(col + 2) == '+') {
                        through = true;
                    }
                    break;
                case 1:
                    if (house[row + 2].charAt(col) == '-' || house[row + 2].charAt(col) == 'O' || house[row].charAt(col + 2) == '+') {
                        through = true;
                    }
                    break;
                case 2:
                    if (house[row].charAt(col - 2) == '-' || house[row].charAt(col - 2) == 'O' || house[row].charAt(col + 2) == '+') {
                        through = true;
                    }
                    break;
                case 3:
                    if (house[row - 2].charAt(col) == '-' || house[row - 2].charAt(col) == 'O' || house[row].charAt(col + 2) == '+') {
                        through = true;
                    }
                    break;
            }
        } catch (IndexOutOfBoundsException e) {}
        return through;
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
            String trimmed = String.join("", earlyShape[i]);
            shape[i] = rightTrim(trimmed);
        }
        return String.join("\n", shape);
    }

    private static String[] updateHouse(LinkedList<int[]> coord, String[] house) {

        boolean stop = false;
        for (int i = 0; i < coord.size() - 1; i++) {
            int[] prevCross = coord.get(i);
            int[] currentCross = coord.get(i + 1);

            if (currentCross[2] != 1) {
                if (currentCross[3] == 1) {
                    stop = true;
                } else {
                    stop = false;
                }
            }

            if (currentCross[2] == 1 && !stop) {
                if (currentCross[0] == prevCross[0]) {
                    if (currentCross[1] > prevCross[1]) {
                        for (int j = prevCross[1] + 1; j <= currentCross[1]; j++) {
                            house[currentCross[0]] = house[currentCross[0]].substring(0, j) + " " + house[currentCross[0]].substring(j + 1, house[currentCross[0]].length());
                        }
                    } else {
                        for (int j = prevCross[1] - 1; j >= currentCross[1]; j--) {
                            house[currentCross[0]] = house[currentCross[0]].substring(0, j) + " " + house[currentCross[0]].substring(j + 1, house[currentCross[0]].length());
                        }
                    }
                } else {
                    if (currentCross[0] > prevCross[0]) {
                        for (int j = prevCross[0] + 1; j <= currentCross[0]; j++) {
                            house[j] = house[j].substring(0, currentCross[1]) + " " + house[j].substring(currentCross[1] + 1, house[j].length());
                        }
                    } else {
                        for (int j = prevCross[0] - 1; j >= currentCross[0]; j--) {
                            house[j] = house[j].substring(0, currentCross[1]) + " " + house[j].substring(currentCross[1] + 1, house[j].length());
                        }
                    }
                }
            }
        }
        return house;
    }

    private static String rightTrim(String s) {
        int i = s.length()-1;
        while (i >= 0 && Character.isWhitespace(s.charAt(i))) {
            i--;
        }
        return s.substring(0,i+1);
    }
}