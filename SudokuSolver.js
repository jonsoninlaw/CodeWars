let puzzle = [
    [5,3,0,0,7,0,0,0,0],
    [6,0,0,1,9,5,0,0,0],
    [0,9,8,0,0,0,0,6,0],
    [8,0,0,0,6,0,0,0,3],
    [4,0,0,8,0,3,0,0,1],
    [7,0,0,0,2,0,0,0,6],
    [0,6,0,0,0,0,2,8,0],
    [0,0,0,4,1,9,0,0,5],
    [0,0,0,0,8,0,0,7,9]];

sudoku(puzzle);

function sudoku(puzzle) {

    let numbersCount = [0, 0, 0, 0, 0, 0, 0, 0, 0];
    let maxNumbers = new Array();
    let empty = 81;
    let found = false;

    // Get count of all numbers
    for (let row = 0; row < 9; row++) {
        for (let col = 0; col < 9; col++) {
            if (puzzle[row][col] != 0) {
                numbersCount[puzzle[row][col] - 1]++;
                empty--;
            }
        }
    }

    while (empty > 0) {
        let boxPuzzle = new Array();
        for (let puz = 0; puz < 9; puz++) {
            boxPuzzle.push(puzzle[puz].slice());
        }

        // Check which number is most present and not blocking
        let max = 0;
        let maxNumber = 0;
        for (let i = 0; i < 9; i++) {
            if (numbersCount[i] > max && !maxNumbers.includes(i + 1) && numbersCount[i] < 9) {
                max = numbersCount[i];
                maxNumber = i + 1;
            }
        }
        let isInRow = false;
        let numberCheck = false;
        let colCheck = 0;
        let rowCheck = 0;

        // Fill non valid spaces with X in the puzzle clone
        for (let row = 0; row < 9; row++) {
            isInRow = false;
            numberCheck === false;
            
            for (let col = 0; col < 9; col++) {

                if (!isInRow && puzzle[row][col] === maxNumber) {
                    isInRow = true;
                    for (let p = 0; p < 9; p++) {
                        if (p != col) {
                            boxPuzzle[row][p] = "X";
                        }
                        if (p != row) {
                            boxPuzzle[p][col] = "X";
                        }
                    }
                    break;
                }
            }
        }

        // Check each mini-puzzle
        for (let box = 0; box < 9; box++) {
            numberCheck === false;
            let nextBox = false;
            let rowStart = 3 * Math.trunc(box / 3);
            let colStart = (box - rowStart) * 3;
            
            for (let row = rowStart; row < rowStart + 3; row++) {

                if (nextBox) {break;};

                for (let col = colStart; col < colStart + 3; col++) {

                    if (boxPuzzle[row][col] === 0) {
                        if (!numberCheck) {
                            numberCheck = true;
                            rowCheck = row;
                            colCheck = col;
                        }
                        else {
                            numberCheck = false;
                            nextBox = true;
                            break;
                        }
                    }
                    if (puzzle[row][col] === maxNumber) {
                        numberCheck = false;
                        nextBox = true;
                        break;
                    }
                }
            }

            if (numberCheck) {
                puzzle[rowCheck][colCheck] = maxNumber;
                numbersCount[maxNumber - 1]++;
                empty--;
                found = true;
            }
        }
       
        if (!found) {
           maxNumbers.push(maxNumber);
        }
        else {
            maxNumbers = [];
            found = false;
        }
    }
    return puzzle;
}