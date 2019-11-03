validSolution([
    [5, 3, 4, 6, 7, 8, 9, 1, 2],
    [6, 7, 2, 1, 9, 5, 3, 4, 8],
    [1, 9, 8, 3, 4, 2, 5, 6, 7],
    [8, 5, 9, 7, 6, 1, 4, 2, 3],
    [4, 2, 6, 8, 5, 3, 7, 9, 1],
    [7, 1, 3, 9, 2, 4, 8, 5, 6],
    [9, 6, 1, 5, 3, 7, 2, 8, 4],
    [2, 8, 7, 4, 1, 9, 6, 3, 5],
    [3, 4, 5, 2, 8, 6, 1, 7, 9]
  ]);

function validSolution(puzzle) {

    // Check each mini-puzzle
    for (let box = 0; box < 9; box++) {
        let rowStart = 3 * Math.trunc(box / 3);
        let colStart = (box - rowStart) * 3;
        
        for (let number = 1; number < 10; number++) {
            let isInBox = false;
            for (let row = rowStart; row < rowStart + 3; row++) {
                for (let col = colStart; col < colStart + 3; col++) {
                    if (puzzle[row][col] === number) {
                        if (!isInBox) {
                            isInBox = true;
                        }
                        else {
                            return false;
                        }
                    }
                }
            }
        }
    }
    for (let number = 1; number < 10; number++) {
        for (let row = 0; row < 9; row++) {
            let isInRow = false;
            for (let col = 0; col < 9; col++) {
                if (puzzle[row][col] === number) {
                    if (!isInRow) {
                        isInRow = true;
                    }
                    else {
                        return false;
                    }
                }
            }
        }
        for (let col = 0; col < 9; col++) {
            let isInCol = false;
            for (let row = 0; row < 9; row++) {
                if (puzzle[row][col] === number) {
                    if (!isInCol) {
                        isInCol = true;
                    }
                    else {
                        return false;
                    }
                }
            }
        }
    }
    return true;
}