import java.lang.IndexOutOfBoundsException;

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

        System.out.println(fieldValidator(battleField));
    }
    
    public static boolean fieldValidator(int[][] field) {

        int[] shipsCount = new int[4];
        int actualCount = 0;
        int collisionCount = 0;

        // Check row by row
        for (int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                if (field[i][j] == 1) {
                    actualCount++;
                }
                else {
                    if (actualCount > 1) {
                        if (!checkAround(field, 0, i, j, actualCount)) {
                            return false;
                        }
                    }
                    actualCount = 0;
                }
            }
        }
        
        return true;
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