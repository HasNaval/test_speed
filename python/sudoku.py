### print signature= print(*obect,sep=' ',end='\n',file=sys.stdout,flush=false)
import math
import copy
from datetime import datetime


sudoku3x3 = [
    [0,8,2, 0,1,0, 0,0,0],
    [7,0,0, 0,0,0, 0,3,0],
    [0,0,0, 0,0,6, 0,0,5],

    [0,0,0, 0,0,0, 0,8,0],
    [3,0,0, 7,0,0, 0,0,0],
    [0,0,0, 0,0,0, 1,0,4],

    [4,0,1, 0,0,0, 0,0,6],
    [0,0,0, 0,5,0, 0,0,0],
    [0,0,0, 8,0,0, 0,0,0]
]

def getEmptyCellsList(sudoku):
    emptyCellsList=[]
    gridSize = len(sudoku[0])
    for i in range(gridSize):
        for j in range(gridSize):
            if sudoku[i][j]==0:
                emptyCellsList.append([i,j])
    return emptyCellsList

def printSudoku(sudoku):
    gridSize = len(sudoku[0])
    for i in range(gridSize):
        for j in range(gridSize):
            if j!=gridSize-1:
                print(sudoku[i][j],end=',')
            else:
                print(sudoku[i][j])

def compareTwoSudoku(sudoku1,sudoku2):
    gridSize = len(sudoku1[0])
    for i in range(gridSize):
        for j in range(gridSize):
            if sudoku1[i][j] != sudoku2[i][j]:
                return False
    return True


def verify(value, index , sudoku):
    gridSize = len(sudoku[0])
    for i in range (gridSize):
            if sudoku [i][index [1]]==value or sudoku [index [0]][i]==value :#once a value in the column equals to the value passed in parameter, return False
                return False
    blockSize = int(math.sqrt(gridSize))
    x_index_min = index [0] - index [0]%blockSize
    x_index_max = x_index_min + blockSize-1
    y_index_min =index [1] - index [1]%blockSize
    y_index_max = y_index_min +blockSize -1
    for i in range(x_index_min, x_index_max+1):
        for j in range(y_index_min, y_index_max+1):
            if i!=index [0] and j!=index [1]:
                if sudoku[i][j]==value:
                    return False
    return True


def resolve_by_increasing_possible_value(sudoku):
    solution = copy.deepcopy(sudoku)
    gridSize = len(solution[0])
    emptyCellsList = getEmptyCellsList(solution)
    print('grid size = ', gridSize ,',empty cases = ' ,len(emptyCellsList))
    emptyCellsListIterator=0
    while emptyCellsListIterator < len(emptyCellsList):
        x_index = emptyCellsList [emptyCellsListIterator] [0]
        y_index = emptyCellsList [emptyCellsListIterator] [1]
        present_value = solution[x_index][y_index]
        #go back one case because there is no possible value anymore, reset value to zero before going back
        if present_value==gridSize:
            solution[x_index][y_index]=0
            emptyCellsListIterator=emptyCellsListIterator-1
        else:
            for possible_value in range(present_value+1,gridSize+1):
                if verify(possible_value,emptyCellsList[emptyCellsListIterator],solution):
                    solution[x_index][y_index]=possible_value
                    emptyCellsListIterator = emptyCellsListIterator + 1
                    break
                if (possible_value==gridSize and verify(possible_value,emptyCellsList[emptyCellsListIterator],solution)==False):
                    solution[x_index][y_index]=0
                    emptyCellsListIterator = emptyCellsListIterator -1
    return solution

def resolve_by_decreasing_possible_value(sudoku):
    solution = copy.deepcopy(sudoku)
    gridSize = len(solution[0])
    emptyCellsList = getEmptyCellsList(solution)
    print('grid size = ', gridSize ,',empty cases = ' ,len(emptyCellsList))
    emptyCellsListIterator=0
    while emptyCellsListIterator < len(emptyCellsList):
        x_index = emptyCellsList [emptyCellsListIterator] [0]
        y_index = emptyCellsList [emptyCellsListIterator] [1]
        present_value = solution[x_index][y_index]
        #go back one case because there is no possible value anymore, reset value to zero before going back
        if present_value==1:
            solution[x_index][y_index]=0
            emptyCellsListIterator=emptyCellsListIterator-1
        else:
            if solution[x_index][y_index]==0:
                a = gridSize
            else:
                a = present_value-1
            for possible_value in range(a,0,-1):
                if verify(possible_value,emptyCellsList[emptyCellsListIterator],solution):
                    solution[x_index][y_index]=possible_value
                    emptyCellsListIterator = emptyCellsListIterator + 1
                    break
                if (possible_value==1 and verify(possible_value,emptyCellsList[emptyCellsListIterator],solution)==False):
                    solution[x_index][y_index]=0
                    emptyCellsListIterator = emptyCellsListIterator -1
    return solution


start = datetime.now()
solution1 = resolve_by_increasing_possible_value(sudoku3x3)
middle = datetime.now()
printSudoku(solution1)

print()

solution2 = resolve_by_decreasing_possible_value(sudoku3x3)
end = datetime.now()
printSudoku(solution2)

first = (middle-start).total_seconds()
second = (end-middle).total_seconds()

print(first,'     ',second)