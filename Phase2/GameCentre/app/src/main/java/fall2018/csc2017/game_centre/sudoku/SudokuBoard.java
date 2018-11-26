package fall2018.csc2017.game_centre.sudoku;

/**
 * Overall sudoku grid.
 */
class SudokuBoard {

    /**
     * The length of the board.
     */
    private static final int SIDE_LEN = 9;

    /**
     * Entire 9*9 grid composed of 9 sub grids.
     */
    private Cell[][] grid;

    /**
     * Sudoku board constructor.
     * @param emptyCells the number of empty cells that the player wants. Relates to difficulty.
     */
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

    /**
     * Getter for a cell.
     * @param row the num of row
     * @param col the num of col
     * @return the cell of demand
     */
    Cell getCell(int row, int col){
        return grid[row][col];
    }

    /**
     * Getter for side length
     * @return the length of the board.
     */
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
