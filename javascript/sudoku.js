let sudoku3x3 = [
    [0, 8, 2, 0, 1, 0, 0, 0, 0],
    [7, 0, 0, 0, 0, 0, 0, 3, 0],
    [0, 0, 0, 0, 0, 6, 0, 0, 5],

    [0, 0, 0, 0, 0, 0, 0, 8, 0],
    [3, 0, 0, 7, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 1, 0, 4],

    [4, 0, 1, 0, 0, 0, 0, 0, 6],
    [0, 0, 0, 0, 5, 0, 0, 0, 0],
    [0, 0, 0, 8, 0, 0, 0, 0, 0]
]

function getEmptyCellsList(sudoku) {
    let emptyCellsList = []
    let gridSize = sudoku[0].length
    for (let i = 0; i < gridSize; i++) {
        for (let j = 0; j < gridSize; j++) {
            if (sudoku[i][j] == 0) {
                emptyCellsList.push([i, j])
            }

        }
    }
    return emptyCellsList
}

function printSudoku(sudoku) {
    let gridSize = sudoku[0].length
    for (let i = 0; i < gridSize; i++) {
        for (let j = 0; j < gridSize; j++) {
            if (j != gridSize - 1) {
                process.stdout.write(sudoku[i][j] + ' ')
            }
            else {
                console.log(sudoku[i][j],)
            }
        }

    }

}


function compareTwoSudoku(sudoku1, sudoku2) {
    let gridSize = sudoku1[0].length
    for (let i = 0; i < gridSize; i++) {
        for (let j = 0; j < gridSize; j++) {
            if (sudoku1[i][j] != sudoku2[i][j]) {
                return false
            }

        }

    }
    return true
}



function verify(value, index, sudoku) {
    gridSize = sudoku[0].length
    for (let i=0;i<gridSize;i++){
        if (sudoku[i][index[1]] == value || sudoku[index[0]][i] == value) {
            return false
        } //once a value in the column equals to the value passed in parameter, return False
            
    }

    let blockSize = parseInt(Math.sqrt(gridSize))
    let x_index = index[0]
    let y_index = index[1]
    let x_index_min = x_index - x_index % blockSize
    let x_index_max = x_index_min + blockSize - 1
    let y_index_min = y_index - y_index % blockSize
    let y_index_max = y_index_min + blockSize - 1
    for (let i = x_index_min; i < x_index_max + 1; i++) {
        for (let j = y_index_min; j < y_index_max + 1; j++) {
            if (i != x_index && j != y_index) {
                if (sudoku[i][j] == value) {
                    return false
                }
            }
        }
    }
    return true
}

function resolve_by_increasing_possible_value(sudoku) {
    let solution = JSON.parse(JSON.stringify(sudoku))
    let gridSize = solution[0].length
    let emptyCellsList = getEmptyCellsList(solution)
    console.log('grid size = ', gridSize, ',empty cases = ', emptyCellsList.length)
    let emptyCellsListIterator = 0
    while (emptyCellsListIterator < emptyCellsList.length) {
        let x_index = emptyCellsList[emptyCellsListIterator][0]
        let y_index = emptyCellsList[emptyCellsListIterator][1]
        let present_value = solution[x_index][y_index]
        //go back one case because there is no possible value anymore, reset value to zero before going back
        if (present_value == gridSize) {
            solution[x_index][y_index] = 0
            emptyCellsListIterator = emptyCellsListIterator - 1
        }
        else {
            for (let possible_value = present_value + 1; possible_value < gridSize + 1; possible_value++) {
                if (verify(possible_value, emptyCellsList[emptyCellsListIterator], solution)) {
                    solution[x_index][y_index] = possible_value
                    emptyCellsListIterator = emptyCellsListIterator + 1
                    break
                }
                if (possible_value == gridSize && verify(possible_value, emptyCellsList[emptyCellsListIterator], solution) == false) {
                    solution[x_index][y_index] = 0
                    emptyCellsListIterator = emptyCellsListIterator - 1
                }
            }
        }

    }
    return solution
}


function resolve_by_decreasing_possible_value(sudoku) {
    let solution = JSON.parse(JSON.stringify(sudoku))
    let gridSize = solution[0].length
    let emptyCellsList = getEmptyCellsList(solution)
    console.log('grid size = ', gridSize, ',empty cases = ', emptyCellsList.length)
    let emptyCellsListIterator = 0
    while (emptyCellsListIterator < emptyCellsList.length) {
        let x_index = emptyCellsList[emptyCellsListIterator][0]
        let y_index = emptyCellsList[emptyCellsListIterator][1]
        present_value = solution[x_index][y_index]
        //go back one case because there is no possible value anymore, reset value to zero before going back
        if (present_value == 1) {
            solution[x_index][y_index] = 0
            emptyCellsListIterator = emptyCellsListIterator - 1
        }
        else {
            if (solution[x_index][y_index] == 0) {
                a = gridSize
            }
            else {
                a = present_value - 1
            }
            for (let possible_value = a; possible_value > 0; possible_value--) {
                if (verify(possible_value, emptyCellsList[emptyCellsListIterator], solution)) {
                    solution[x_index][y_index] = possible_value
                    emptyCellsListIterator = emptyCellsListIterator + 1
                    break
                }
                if (possible_value == 1 && verify(possible_value, emptyCellsList[emptyCellsListIterator], solution) == false) {
                    solution[x_index][y_index] = 0
                    emptyCellsListIterator = emptyCellsListIterator - 1
                }
            }
        }
    }
    return solution
}


let start = performance.now()
solution1 = resolve_by_increasing_possible_value(sudoku3x3)
let middle = performance.now()
solution2 = resolve_by_decreasing_possible_value(sudoku3x3)
let end = performance.now()


printSudoku(solution1)
console.log()
printSudoku(solution2)

console.log(compareTwoSudoku(solution1, solution2))
console.log((middle-start),'     ',(end-middle))