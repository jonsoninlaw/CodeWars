import math
import copy

def sudoku(puzzle) :

    numbersCount = [0, 0, 0, 0, 0, 0, 0, 0, 0]
    maxNumbers = []
    empty = 81
    found = False

    # Get count of all numbers
    for row  in range(9) :
        for col in range(9) :
            if puzzle[row][col] != 0 :
                numbersCount[puzzle[row][col] - 1] += 1
                empty -= 1

    while empty > 0 :
        boxPuzzle = []
        for puz in range(9) :
            boxPuzzle.append(puzzle[puz][:])

        # Check which number is most present and not blocking
        max = 0
        maxNumber = 0
        for i in range(9) :
            if numbersCount[i] > max and (i + 1) not in maxNumbers and numbersCount[i] < 9 :
                max = numbersCount[i]
                maxNumber = i + 1
                
        isInRow = False
        numberCheck = False
        colCheck = 0
        rowCheck = 0

        # Fill non valid spaces with X in the puzzle clone
        for row in range (9) :
            isInRow = False;
            
            for col in range(9) :

                if not isInRow and puzzle[row][col] == maxNumber :
                    isInRow = True
                    for p in range(9) :
                        if p != col :
                            boxPuzzle[row][p] = "X"
                        if p != row :
                            boxPuzzle[p][col] = "X"
                    break

        # Check each mini-puzzle
        for box in range(9) :
            nextBox = False
            rowStart = 3 * math.floor(box / 3)
            colStart = (box - rowStart) * 3
            
            for row in range(rowStart, rowStart + 3) :

                if nextBox :
                    break

                for col in range(colStart, colStart + 3) :

                    if boxPuzzle[row][col] == 0 :
                        if not numberCheck :
                            numberCheck = True
                            rowCheck = row
                            colCheck = col
                        else :
                            numberCheck = False
                            nextBox = True
                            break
                    if puzzle[row][col] == maxNumber :
                        numberCheck = False
                        nextBox = True
                        break

            if numberCheck :
                print("Correct")
                puzzle[rowCheck][colCheck] = maxNumber
                numbersCount[maxNumber - 1] += 1
                empty -= 1
                found = True
                
        if not found :
           maxNumbers.append(maxNumber)
        else :
            maxNumbers = []
            found = False
    return puzzle