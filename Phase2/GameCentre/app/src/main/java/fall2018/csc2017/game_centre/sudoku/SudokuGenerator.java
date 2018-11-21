package fall2018.csc2017.game_centre.sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Randomly generate a sudoku.
 * Adapted from
 * https://blog.forret.com/2006/08/14/a-sudoku-challenge-generator/
 */
class SudokuGenerator {

    private static final int[][] ROOT_GRID = {
            {1, 2, 3, 4, 5, 6, 7, 8, 9},
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

    private static int[][] grid;

    static int[][] generate(int emptyCells) {
        generate();
        eraseCells(emptyCells);
        return grid;
    }

    private static void generate() {
        grid = ROOT_GRID;
    }

    private void swapRow() {
        Random random = new Random();
        int row1 = random.nextInt(9);
        int row2 = random.nextInt(9);
        int[] temp = grid[row1];
        grid[row1] = grid[row2];
        grid[row2] = temp;
    }

    private void swapColumn() {
        Random random = new Random();
        int col1 = random.nextInt(9);
        int col2 = random.nextInt(9);
        for (int[] row : grid) {
            int temp = row[col1];
            row[col1] = row[col2];
            row[col2] = temp;
        }

    }

    private void swapRowGroup() {

    }

    private void swapColumnGroup() {

    }

    private void transpose() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < r; c++) {
                int temp = grid[r][c];
                grid[r][c] = grid[c][r];
                grid[c][r] = temp;
            }
        }

    }

    private static void eraseCells(int emptyCells) {

    }
}
