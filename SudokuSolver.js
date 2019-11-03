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
    let empty = 81;

    // Get count of all numbers
    for (let row = 0; row < 9; row++) {
        for (let col = 0; col < 9; col++) {
            if (puzzle[row][col] != 0) {
                numbersCount[puzzle[row][col] - 1]++;
                empty--;
            }
        }
    }
    
    let maxNumbers = new Array();
    let found = false;

    while (empty > 0) {

        // Copie du sudoku pour le modèle de boîtes
        let boxPuzzle = new Array();
        for (let puz = 0; puz < 9; puz++) {
            boxPuzzle.push(puzzle[puz].slice());
        }

        // Check which number is most present
        let max = 0;
        let maxNumber = 0;
        for (let i = 0; i < 9; i++) {
            if (numbersCount[i] > max && !maxNumbers.includes(i + 1) && numbersCount[i] < 9) {
                max = numbersCount[i];
                maxNumber = i + 1;
            }
        }
        let isInRow = false;
        let isInCol = false;
        let isInBox = false;
        let numberCheck = false;
        let colCheck = 0;
        let rowCheck = 0;
        let cols = new Array();
        let rows = new Array();

// ##################################################################################################

        // Vérification des lignes
        for (let row = 0; row < 9; row++) {

            isInRow = false;
            isInCol = false;
            numberCheck === false;
            
            // On parcourt la ligne entière pour vérifier que le chiffre n'est pas présent
            for (let col = 0; col < 9; col++) {

                if (!isInRow && puzzle[row][col] === maxNumber) {
                    isInRow = true;

                    if (!cols.includes(col)) {
                        cols.push(col);
                    }

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


        // ##################################################################################################

        // Vérification des colonnes
        for (let col = 0; col < 9; col++) {

            // On initialise les booléens à faux
            isInRow = false;
            isInCol = false;
            numberCheck === false;

            
            // On parcourt la colonne entière pour vérifier que le chiffre n'est pas présent
            for (let row = 0; row < 9; row++) {
                
                // Si il est présent
                if (!isInCol && puzzle[row][col] === maxNumber) {
                    isInCol = true;

                    // On ajoute le numéro de la ligne à la liste
                    if (!rows.includes(row)) {
                        rows.push(row);
                    }
                    
                    // On remplit toute la colone dans le modèle de boîtes
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


        // Vérification box par box
        for (let box = 0; box < 9; box++) {

            numberCheck === false;
            let nextBox = false;

            let rowStart = 3 * Math.trunc(box / 3);
            let colStart = (box - rowStart) * 3;
            
            // On parcourt chaque ligne de la box
            for (let row = rowStart; row < rowStart + 3; row++) {
                if (nextBox) {break;};

                // Puis chaque colonne de la box pour vérifier si le chiffre est présent
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

            // A la fin du parcours, si le chiffre est validé, on l'insère dans la case
            if (numberCheck) {
                puzzle[rowCheck][colCheck] = maxNumber;
                numbersCount[maxNumber - 1]++;
                empty--;
                found = true;
            }
        }

        // ######################################################################################""

       
       if (!found) {
           maxNumbers.push(maxNumber);
        }
        else {
            maxNumbers = [];
            found = false;
        }
        console.log(empty);
    }

    return puzzle;

}