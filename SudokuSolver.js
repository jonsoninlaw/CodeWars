var puzzle = [
    [5,3,0,0,7,0,0,0,0],
    [6,0,0,1,9,5,0,0,0],
    [0,9,8,0,0,0,0,6,0],
    [8,0,0,0,6,0,0,0,3],
    [4,0,0,8,0,3,0,0,1],
    [7,0,0,0,2,0,0,0,6],
    [0,6,0,0,0,0,2,8,0],
    [0,0,0,4,1,9,0,0,5],
    [0,0,0,0,8,0,0,7,9]];

sudokuSolver(puzzle);

function sudokuSolver(puzzle) {

    let numbersCount = [0, 0, 0, 0, 0, 0, 0, 0, 0];
    let emptySpace = [[9, 9, 9, 9, 9, 9, 9, 9, 9], [9, 9, 9, 9, 9, 9, 9, 9, 9], [9, 9, 9, 9, 9, 9, 9, 9, 9]];
    let empty = 81;

    // Get count of all numbers and empty spaces
    let boxCount = 0;
    for (let row = 0; row < 9; row++) {
        if (row > 0 && row % 3 === 0) {boxCount += 3};
        for (let col = 0; col < 9; col++) {
            if (puzzle[row][col] != 0) {
                numbersCount[puzzle[row][col] - 1]++;
                emptySpace[0][row]--;
                emptySpace[1][col]--;
                emptySpace[2][boxCount + Math.trunc(col / 3)]--;
                empty--;
            }
        }
    }
    
    let maxNumbers = new Array();
    let found = false;

    for (let t = 0; t < 100; t++) {

        // Check which number is most present
        let max = 0;
        let maxNumber = 0;
        for (let i = 0; i < 9; i++) {
            if (numbersCount[i] > max && !maxNumbers.includes(i + 1) && numbersCount[i] < 9) {
                max = numbersCount[i];
                maxNumber = i + 1;
            }
        }
        boxCount = 0;
        let isInRow = false;
        let isInCol = false;
        let isInBox = false;
        let numberCheck = false;
        let colCheck = 0;
        let rowCheck = 0;
        let cols = new Array();
        let rows = new Array();
        // Check if chosen number is in the row

// ##################################################################################################

        // Vérification ligne par ligne
        for (let row = 0; row < 9; row++) {

            // On initialise les booléens à faux
            isInRow = false;
            isInCol = false;
            numberCheck === false;

            if (row > 0 && row % 3 === 0) {boxCount += 3};
            
            // On parcourt la ligne entière pour vérifier que le chiffre n'est pas présent
            for (let col = 0; col < 9; col++) {

                // Si il est présent
                if (!isInRow && puzzle[row][col] === maxNumber) {
                    isInRow = true;

                    // On ajoute le numéro de la colonne à la liste
                    if (!cols.includes(col)) {
                        cols.push(col);
                    }
                    break;
                }
            }

            // Si il n'est pas présent
            if (!isInRow) {

                // On parcourt la ligne colone par colonne
                for (let testCol = 0; testCol < 9; testCol++) {
                    isInCol = false;

                    // Si la case est vide et que le nombre n'existe pas déjà dans la même colonne
                    if (puzzle[row][testCol] === 0 && !cols.includes(testCol)) {

                        // On parcourt toutes les cases sur la même colonne
                        for (let testRow = 0; testRow < 9; testRow++) {

                            // Si le nombre est présent dans la case ou a déjà été repéré
                            if (!isInCol && puzzle[testRow][testCol] === maxNumber) {

                                // On indique qu'il est présent
                                isInCol = true;

                                // On ajoute la colonne à la liste
                                cols.push(testCol);
                                break;
                            }
                        }

                        // Si le chiffre n'est pas présent sur la colonne
                        if (!isInCol) {

                            // Si il a déjà été validé
                            if (numberCheck) {
                            
                                // On indique qu'il n'est plus validé
                                numberCheck = false;

                                // Et on arrête de chercher
                                console.log("break");
                                break;
                            }

                            // Sinon
                            else {

                                // On valide le chiffre
                                numberCheck = true;
    
                                // Et on indique dans quelle case l'insérer
                                colCheck = testCol;
                                console.log("validé");
                            }
                        }
                    }
                }

                // A la fin du parcours, si le chiffre est validé, on l'insère dans la case
                if (numberCheck) {
                    puzzle[row][colCheck] = maxNumber;
                    numbersCount[maxNumber - 1]++;
                    emptySpace[0][row]--;
                    emptySpace[1][colCheck]--;
                    emptySpace[2][boxCount + Math.trunc(colCheck / 3)]--;
                    empty--;
                    found = true;
                }
            }
        }


        // ##################################################################################################

        // Vérification colonne par colonne
        for (let col = 0; col < 9; col++) {

            // On initialise les booléens à faux
            isInRow = false;
            isInCol = false;
            numberCheck === false;

            
            // On parcourt la colonne entière pour vérifier que le chiffre n'est pas présent
            for (let row = 0; row < 9; row++) {
                if (row > 0 && row % 3 === 0) {boxCount += 3};
                
                // Si il est présent
                if (!isInCol && puzzle[row][col] === maxNumber) {
                    isInCol = true;

                    // On ajoute le numéro de la ligne à la liste
                    if (!rows.includes(row)) {
                        rows.push(row);
                    }
                    break;
                }
            }

            // Si il n'est pas présent
            if (!isInCol) {

                // On parcourt la colonne ligne par ligne
                for (let testRow = 0; testRow < 9; testRow++) {
                    isInRow = false;

                    // Si la case est vide et que le nombre n'existe pas déjà dans la même ligne
                    if (puzzle[testRow][col] === 0 && !rows.includes(testRow)) {

                        // On parcourt toutes les cases sur la même ligne
                        for (let testCol = 0; testCol < 9; testCol++) {

                            // Si le nombre est présent dans la case ou a déjà été repéré
                            if (!isInRow && puzzle[testRow][testCol] === maxNumber) {

                                // On indique qu'il est présent
                                isInRow = true;

                                // On ajoute la ligne à la liste
                                rows.push(testRow);
                                break;
                            }
                        }

                        // Si le chiffre n'est pas présent sur la ligne
                        if (!isInRow) {

                            // Si il a déjà été validé
                            if (numberCheck) {
                            
                                // On indique qu'il n'est plus validé
                                numberCheck = false;

                                // Et on arrête de chercher
                                console.log("break");
                                break;
                            }

                            // Sinon
                            else {

                                // On valide le chiffre
                                numberCheck = true;
    
                                // Et on indique dans quelle case l'insérer
                                rowCheck = testRow;
                                console.log("validé");
                            }
                        }
                    }
                }

                // A la fin du parcours, si le chiffre est validé, on l'insère dans la case
                if (numberCheck) {
                    puzzle[rowCheck][col] = maxNumber;
                    numbersCount[maxNumber - 1]++;
                    emptySpace[0][rowCheck]--;
                    emptySpace[1][col]--;
                    emptySpace[2][boxCount + Math.trunc(col / 3)]--;
                    empty--;
                    found = true;
                }
            }
        }

        // ##################################################################################################


        if (!found) {
            maxNumbers.push(maxNumber);
        }
        else {
            maxNumbers = [];
            found = false;
        }
        //console.log("numbersCount : " + numbersCount);
        //console.log(emptySpace[0]);
        //console.log(emptySpace[1]);
        //console.log(emptySpace[2]);
        console.log("empty spaces : " + empty);
        //console.log("maxNumber : " + maxNumber);
        console.log("cols filled : " + cols);
        console.log("maxNumbers : " + maxNumbers);
    }


}