public class gameOfLife {

    public static void main(String[] args) {

        int[][] cells = {
            {1,0,0},
            {0,1,1},
            {1,1,0}};
        int[][] result = {
            {0,1,0},
            {0,0,1},
            {1,1,1}};
        int generations = 1;

        getGeneration(cells, generations);
    }

    public static int[][] getGeneration(int[][] cells, int generations) {

        int count = 0;
        int[][] nextGen = new int[cells.length + 2][cells[0].length + 2];
        int[][] cellsCopy = new int[nextGen.length][nextGen[0].length];

        for (int r = 0; r < nextGen.length - 2; r++) {
            for (int c = 0; c < nextGen[0].length - 2; c++) {
                nextGen[r + 1][c + 1] = cells[r][c];
            }
        }
        for (int r = 0; r < cellsCopy.length; r++) {
            for (int c = 0; c < cellsCopy[0].length; c++) {
                cellsCopy[r][c] = nextGen[r][c];
            }
        }

        for (int r = 0; r < cellsCopy.length; r++) {
            for (int c = 0; c < cellsCopy[0].length; c++) {
                count = 0;
                if (cellsCopy[r][c] == 0) {
                    for (int row = r - 1; row < r + 2; row++) {
                        for (int col = c - 1; col < c + 2; col++) {
                            try {
                                if (cellsCopy[row][col] == 1 && !(row == r && col == c)) {
                                    count++;
                                }
                            } catch (IndexOutOfBoundsException e) {}
                        }
                    }
                    if (count == 3) {
                        nextGen[r][c] = 1;
                    }
                }
                count = 0;
                if (cellsCopy[r][c] == 1) {
                    for (int row = r - 1; row < r + 2; row++) {
                        for (int col = c - 1; col < c + 2; col++) {
                            try {
                                if (cellsCopy[row][col] == 1 && !(row == r && col == c)) {
                                    count++;
                                }
                            } catch (IndexOutOfBoundsException e) {}
                        }
                    }
                    if (!(count == 2 || count == 3)) {
                        nextGen[r][c] = 0;
                    }
                }
            }
        }

        int minR = -1;
        int maxR = -1;
        int minC = -1;
        int maxC = -1;
        for (int r = 0; r < nextGen.length; r++) {
            for (int c = 0; c < nextGen[0].length; c++) {
                if (nextGen[r][c] == 1 && minR == -1) {
                    minR = r;
                }
            }
        }
        for (int r = nextGen.length - 1; r >= 0; r--) {
            for (int c = 0; c < nextGen[0].length; c++) {
                if (nextGen[r][c] == 1 && maxR == -1) {
                    maxR = r;
                }
            }
        }
        for (int c = 0; c < nextGen[0].length; c++) {
            for (int r = 0; r < nextGen.length; r++) {
                if (nextGen[r][c] == 1 && minC == -1) {
                    minC = c;
                }
            }
        }
        for (int c = nextGen[0].length - 1; c >= 0; c--) {
            for (int r = 0; r < nextGen.length; r++) {
                if (nextGen[r][c] == 1 && maxC == -1) {
                    maxC = c;
                }
            }
        }


        int[][] lastGen = new int[maxR - minR + 1][maxC - minC + 1];
        for (int r = 0; r < lastGen.length; r++) {
            for (int c = 0; c < lastGen[0].length; c++) {
                lastGen[r][c] = nextGen[r + minR][c + minC];
            }
        }

        return lastGen;
    }
    
  }