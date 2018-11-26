package fall2018.csc2017.game_centre.sudoku;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Randomly generate a sudoku.
 * Adapted from
 * https://blog.forret.com/2006/08/14/a-sudoku-challenge-generator/
 */
class SudokuGenerator {

    /**
     * A valid sudoku board.
     */
    private static final int[][] ROOT_GRID = {
            {1, 2, 3, 4, 5, 6, 7, 8, 9},
            {4, 5, 6, 7, 8, 9, 1, 2, 3},
            {7, 8, 9, 1, 2, 3, 4, 5, 6},
            {2, 3, 4, 5, 6, 7, 8, 9, 1},
            {5, 6, 7, 8, 9, 1, 2, 3, 4},
            {8, 9, 1, 2, 3, 4, 5, 6, 7},
            {3, 4, 5, 6, 7, 8, 9, 1, 2},
            {6, 7, 8, 9, 1, 2, 3, 4, 5},
            {9, 1, 2, 3, 4, 5, 6, 7, 8}
    };

    /**
     * Sudoku board size.
     */
    private static final int SIZE = 9;

    /**
     * Number of groups in each direction.
     */
    private static final int GROUP_NUM = 3;

    /**
     * Size of groups.
     */
    private static final int GROUP_SIZE = 3;

    /**
     * Maximum number of times single row or column can be swapped.
     */
    private static final int SINGLE_SWAP_RANDOMNESS = 2;

    /**
     * Maximum number of times group of rows or columns can be swapped.
     */
    private static final int GROUP_SWAP_RANDOMNESS = 2;

    /**
     * Maximum number of times the grid can be taken transpose.
     */
    private static final int TRANSPOSE_RANDOMNESS = 1;

    /**
     * The grid to be randomly generated.
     */
    private int[][] grid;

    /**
     * Generate a sudoku board with certain number of cells removed.
     * @param emptyCells number of empty cells, plz do not go over 50 for god's sake.
     * @return generated sudoku
     */
    int[][] generate(int emptyCells) {
        generate();
        boolean erased = eraseCells(emptyCells);
        while (!erased){
            generate();
            erased = eraseCells(emptyCells);
        }
        return grid;
    }

    /**
     * Generate a shuffled sudoku board.
     */
    private void generate() {
        grid = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            grid[i] = ROOT_GRID[i].clone();
        }
        randomShuffle();
    }

    /**
     * Shuffle the board randomly.
     */
    private void randomShuffle() {
        Random random = new Random();
        for (int i = 0; i < random.nextInt(SINGLE_SWAP_RANDOMNESS + 1); i++) {
            swapRow();
        }
        for (int i = 0; i < random.nextInt(SINGLE_SWAP_RANDOMNESS + 1); i++) {
            swapColumn();
        }
        for (int i = 0; i < random.nextInt(GROUP_SWAP_RANDOMNESS + 1); i++) {
            swapRowGroup();
        }
        for (int i = 0; i < random.nextInt(GROUP_SWAP_RANDOMNESS + 1); i++) {
            swapColumnGroup();
        }
        for (int i = 0; i < random.nextInt(TRANSPOSE_RANDOMNESS + 1); i++) {
            transpose();
        }
    }

    /**
     * Swap two rows randomly.
     */
    private void swapRow() {
        Random random = new Random();
        int row1, row2;
        do {
            row1 = random.nextInt(SIZE);
            row2 = random.nextInt(SIZE);
        } while (row1 == row2);
        int[] temp = grid[row1];
        grid[row1] = grid[row2];
        grid[row2] = temp;
    }

    /**
     * Swap two rows.
     * @param row1 first row
     * @param row2 first row
     */
    private void swapRow(int row1, int row2) {
        int[] temp = grid[row1];
        grid[row1] = grid[row2];
        grid[row2] = temp;
    }

    /**
     * Swap two columns randomly.
     */
    private void swapColumn() {
        Random random = new Random();
        int col1, col2;
        do {
            col1 = random.nextInt(SIZE);
            col2 = random.nextInt(SIZE);
        } while (col1 == col2);
        for (int[] row : grid) {
            int temp = row[col1];
            row[col1] = row[col2];
            row[col2] = temp;
        }
    }

    /**
     * Swap two columns.
     * @param col1 first column
     * @param col2 second column
     */
    private void swapColumn(int col1, int col2) {
        for (int[] row : grid) {
            int temp = row[col1];
            row[col1] = row[col2];
            row[col2] = temp;
        }
    }

    /**
     * Swap two row groups randomly.
     */
    private void swapRowGroup() {
        Random random = new Random();
        int rowGroup1, rowGroup2;
        do {
            rowGroup1 = random.nextInt(GROUP_NUM);
            rowGroup2 = random.nextInt(GROUP_NUM);
        } while (rowGroup1 == rowGroup2);
        int row1 = rowGroup1 * GROUP_SIZE;
        int row2 = rowGroup2 * GROUP_SIZE;
        for (int i = 0; i < GROUP_SIZE; i++) {
            swapRow(row1 + i, row2 + i);
        }
    }

    /**
     * Swap two column groups randomly.
     */
    private void swapColumnGroup() {
        Random random = new Random();
        int colGroup1, colGroup2;
        do {
            colGroup1 = random.nextInt(GROUP_NUM);
            colGroup2 = random.nextInt(GROUP_NUM);
        } while (colGroup1 == colGroup2);
        int col1 = colGroup1 * GROUP_SIZE;
        int col2 = colGroup2 * GROUP_SIZE;
        for (int i = 0; i < GROUP_SIZE; i++) {
            swapColumn(col1 + i, col2 + i);
        }
    }

    /**
     * Take the transpose of the grid.
     */
    private void transpose() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < r; c++) {
                int temp = grid[r][c];
                grid[r][c] = grid[c][r];
                grid[c][r] = temp;
            }
        }
    }

    /**
     * Erase certain amount of cells.
     * @param  emptyCells number of empty cells.
     * @return a boolean value determine whether we successfully erased "emptyCells" number of cells
     */
    private boolean eraseCells(int emptyCells) {
        // TODO: level-1 strategy
        Random random = new Random();
        int loopCounter = 0;
        while (emptyCells != 0){
            int position = random.nextInt(SudokuGenerator.SIZE * SudokuGenerator.SIZE);
            if (eraseOneCell(position)){
                emptyCells -= 1;
            }
            // TODO delete these test usage
            loopCounter += 1;
//            System.out.println(loopCounter);
            if (loopCounter > 1000){
                return false;
            }
        }
        return true;
    }

    /**
     * Erase one cell of the position.
     *
     * Algorithm: Construct a HashSet of int from 1 to 9.
     *            after the erase removal, strike out all numbers that already appear in
     *            that row, that column and that square.
     *            adapt from https://blog.forret.com/2006/08/14/a-sudoku-challenge-generator/.
     *
     * @param position the position of the selected cell to be erased.
     * @return true if erased, false otherwise.
     */
    private boolean eraseOneCell(int position){
        int row = position / SIZE; int col = position % SIZE;
        int numToBeErased = grid[row][col];
        if (numToBeErased == 0){
            return false;
        }
        grid[row][col] = 0;
        Set<Integer> possibleValues = new HashSet<>();
        for (int i = 1; i <= 9; i++){
            possibleValues.add(i);
        }
        for (int i = 0; i <= 8; i++){
            possibleValues.remove(grid[row][i]);
            possibleValues.remove(grid[i][col]);
        }
        Set<Integer> square = findSquare(row, col);
        for (int item : square){
            possibleValues.remove(item);
        }
        if (possibleValues.size() == 1){
            return true;
        }
        else {
            grid[row][col] = numToBeErased;
            return false;
        }
    }

    /**
     * Find the square that contains the cell in row, col
     * @param row the row number of the cell.
     * @param col the col number of the cell.
     * @return a set of numbers in the square that the cell is in.
     */
    private Set<Integer> findSquare(int row, int col){
        row -= row % 3;
        col -= col % 3;
        Set<Integer> result = new HashSet<>();
        for (int i=row; i<=row+2; i++){
            for (int j=col; j<=col+2; j++){
                result.add(grid[i][j]);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        SudokuGenerator s = new SudokuGenerator();
//        s.generate();
//        int[][] grid = s.grid;
//        for (int[] row : grid) {
//            for (int cell : row) {
//                System.out.print(cell);
//                System.out.print("|");
//            }
//            System.out.print("\n");
//        }

        s.generate(50);
        int[][] grid = s.grid;

        System.out.println("After erase:");
        for (int[] row : grid) {
            for (int cell : row) {
                System.out.print(cell);
                System.out.print("|");
            }
            System.out.print("\n");
        }
    }
}
