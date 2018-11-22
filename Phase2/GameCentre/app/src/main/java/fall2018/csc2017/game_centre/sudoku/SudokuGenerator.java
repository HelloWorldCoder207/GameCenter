package fall2018.csc2017.game_centre.sudoku;

import java.util.Random;

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
    private static int[][] grid;

    /**
     * Generate a sudoku board with certain number of cells removed.
     * @param emptyCells number of empty cells
     * @return generated sudoku
     */
    static int[][] generate(int emptyCells) {
        generate();
        eraseCells(emptyCells);
        return grid;
    }

    /**
     * Generate a shuffled sudoku board.
     */
    private static void generate() {
        grid = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            grid[i] = ROOT_GRID[i].clone();
        }
        randomShuffle();
    }

    /**
     * Shuffle the board randomly.
     */
    private static void randomShuffle() {
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
    private static void swapRow() {
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
    private static void swapRow(int row1, int row2) {
        int[] temp = grid[row1];
        grid[row1] = grid[row2];
        grid[row2] = temp;
    }

    /**
     * Swap two columns randomly.
     */
    private static void swapColumn() {
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
    private static void swapColumn(int col1, int col2) {
        for (int[] row : grid) {
            int temp = row[col1];
            row[col1] = row[col2];
            row[col2] = temp;
        }
    }

    /**
     * Swap two row groups randomly.
     */
    private static void swapRowGroup() {
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
    private static void swapColumnGroup() {
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
    private static void transpose() {
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
     * @param emptyCells number of empty cells.
     */
    private static void eraseCells(int emptyCells) {
        // TODO: level-1 strategy
    }

    public static void main(String[] args) {
        SudokuGenerator.generate();
        int[][] grid = SudokuGenerator.grid;
        for (int[] row : grid) {
            for (int cell : row) {
                System.out.print(cell);
            }
            System.out.print("\n");
        }
    }
}
