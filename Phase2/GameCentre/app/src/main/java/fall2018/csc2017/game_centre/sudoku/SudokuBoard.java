package fall2018.csc2017.game_centre.sudoku;

/**
 * Overall sudoku grid.
 */
class SudokuBoard {

    private static final int SIDE_LEN = 9;

    /**
     * Entire 9*9 grid composed of 9 sub grids.
     */
    private Cell[][] grid;

    SudokuBoard(int emptyCells){
        grid = new Cell[SIDE_LEN][SIDE_LEN];
        SudokuGenerator sudokuGenerator = new SudokuGenerator();
        int[][] tempGrid = sudokuGenerator.generate(emptyCells);
        for (int i = 0; i < SIDE_LEN; i++) {
            for (int j = 0; j < SIDE_LEN; j++) {
                Cell newCell = new Cell(Math.abs(tempGrid[i][j]), tempGrid[i][j] > 0);
                grid[i][j] = newCell;
            }
        }
    }

    Cell getCell(int row, int col){
        return grid[row][col];
    }

    int getSideLen(){
        return SIDE_LEN;
    }

    public static void main(String[] args) {
        SudokuBoard s = new SudokuBoard(20);
        for (int i = 0; i < SIDE_LEN; i++) {
            for (int j = 0; j < SIDE_LEN; j++) {
                System.out.print(s.grid[i][j]);
            }
            System.out.println();
        }
    }
}
