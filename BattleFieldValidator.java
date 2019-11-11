import java.lang.IndexOutOfBoundsException;
import java.util.Arrays;

public class BattleFieldValidator {

    public static void main(String[] args) {

        int[][] battleField = {{1, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                               {1, 0, 1, 0, 0, 0, 0, 0, 1, 0},
                               {1, 0, 1, 0, 1, 1, 1, 0, 1, 0},
                               {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                               {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                               {0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                               {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                               {0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                               {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                               {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

        int[][] battle2 = {{1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                           {1, 0, 1, 0, 0, 0, 0, 0, 0, 1},
                           {1, 0, 1, 0, 0, 0, 0, 1, 0, 0},
                           {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                           {0, 0, 1, 0, 0, 1, 0, 0, 0, 0},
                           {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                           {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                           {0, 1, 1, 0, 0, 0, 1, 1, 1, 0},
                           {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                           {0, 1, 1, 1, 0, 1, 0, 0, 0, 0}};

        System.out.println(fieldValidator(battle2));
        for (int[] elt : battleField) {
            System.out.println(Arrays.toString(elt));
        }
    }
    
    public static boolean fieldValidator(int[][] field) {

        int[] shipsCount = new int[4];
        int[] validShips = {4, 3, 2, 1};
        int actualCount = 0;

        // Check row by row
        for (int i = 0; i < 10; i++) {
            actualCount = 0;
            for(int j = 0; j < 10; j++) {
                if (field[i][j] == 1) {
                    actualCount++;
                    if (j == 9) {
                        if (actualCount == 1) {
                            if (checkAround(field, 0, i, j + 1, actualCount)) {
                                shipsCount[0]++;
                            }
                        }
                        else if (actualCount > 1) {
                            if (!checkAround(field, 0, i, j + 1, actualCount)) {
                                return false;
                            }
                            shipsCount[actualCount - 1]++;
                        }
                        actualCount = 0;
                    }
                }
                else {
                    if (actualCount > 4) {
                        return false;
                    }
                    if (actualCount == 1) {
                        if (checkAround(field, 0, i, j, actualCount)) {
                            shipsCount[0]++;
                        }
                    }
                    else if (actualCount > 1) {
                        if (!checkAround(field, 0, i, j, actualCount)) {
                            return false;
                        }
                        shipsCount[actualCount - 1]++;
                    }
                    actualCount = 0;
                }
            }
        }

        // Check column by column
        for (int j = 0; j < 10; j++) {
            actualCount = 0;
            for(int i = 0; i < 10; i++) {
                if (field[i][j] == 1) {
                    actualCount++;
                    if (i == 9) {
                        if (actualCount > 1) {
                            if (!checkAround(field, 1, i + 1, j, actualCount)) {
                                return false;
                            }
                            shipsCount[actualCount - 1]++;
                        }
                        actualCount = 0;
                    }
                }
                else {
                    if (actualCount > 4) {
                        return false;
                    }
                    if (actualCount > 1) {
                        if (!checkAround(field, 1, i, j, actualCount)) {
                            return false;
                        }
                        shipsCount[actualCount - 1]++;
                    }
                    actualCount = 0;
                }
            }
        }

        if (Arrays.equals(shipsCount, validShips)){
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkAround(int[][] field, int direction, int i, int j, int count) {
        if (direction == 1) {
            for (int row = i - count - 1; row <= i; row++) {
                for (int col = j - 1; col <= j + 1; col++) {
                    try {
                        if (!(row >= i - count && row < i && col == j) && field[row][col] == 1) {
                            return false;
                        }
                    } catch (IndexOutOfBoundsException e) {}
                }
            }
        }
        else {
            for (int row = i - 1; row <= i + 1; row++) {
                for (int col = j - count - 1; col <= j; col++) {
                    try {
                        if (!(col >= j - count && col < j && row == i) && field[row][col] == 1) {
                            return false;
                        }
                    } catch (IndexOutOfBoundsException e) {}
                }
            }
        }
        return true;
    }
}