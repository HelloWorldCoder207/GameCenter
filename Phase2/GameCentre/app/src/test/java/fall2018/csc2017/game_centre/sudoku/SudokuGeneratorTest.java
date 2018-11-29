package fall2018.csc2017.game_centre.sudoku;

import org.junit.Test;
import java.util.Set;

import java.util.HashSet;

import static org.junit.Assert.*;

public class SudokuGeneratorTest {

    private SudokuGenerator generator;

    private static final int[][] ROOT_GRID = {
            {9, 3, 6, 4, 2, 7, 5, 1, 8},
            {7, 8, 2, 1, 5, 3, 4, 6, 9},
            {1, 4, 5, 6, 9, 8, 7, 2, 3},
            {5, 7, 1, 3, 8, 2, 9, 4, 6},
            {2, 9, 3, 5, 6, 4, 8, 7, 1},
            {8, 6, 4, 7, 1, 9, 3, 5, 2},
            {3, 5, 9, 2, 7, 6, 1, 8, 4},
            {4, 2, 7, 8, 3, 1, 6, 9, 5},
            {6, 1, 8, 9, 4, 5, 2, 3, 7}};

    private int[][] grid;
    private int[][] grid2;

    @Test
    public void generate() {
        grid = generator.generate(0);
        grid2 = generator.generate(0);
        assertTrue(checkRows(ROOT_GRID, grid));
        assertTrue(checkColumn(ROOT_GRID,grid));
        assertTrue(checkSquare(ROOT_GRID,grid));
        assertTrue(checkRows(ROOT_GRID,grid2));
        assertTrue(checkColumn(ROOT_GRID,grid2));
        assertTrue(checkSquare(ROOT_GRID,grid2));

    }


    private Set<Integer> findSquare(int row, int col, int[][] a) {
        row -= row % 3;
        col -= col % 3;
        Set<Integer> result = new HashSet<>();
        for (int i = row; i <= row + 2; i++) {
            for (int j = col; j <= col + 2; j++) {
                result.add(a[i][j]);
            }
        }
        return result;
    }
    private boolean checkRows(int[][] a, int[][] b){
        for(int i = 0; i<= a.length; i++){
            if  (! a[i].equals(b[i])){
                return false;
            }
        }
        return true;
    }
    private boolean checkColumn(int[][] a, int[][] b){
        for(int i = 0; i<= a.length; i++){
            int[] c = new int[a.length];
            int[] d = new int[b.length];
            for (int j=0; j<=a[i].length;j++){
                c[i] = a[i][j];
                d[i] = b[i][j];
            }
            if (! c.equals(d)){
                return false;
            }
        }
        return true;
    }
    private boolean checkSquare(int[][] a, int[][] b){
        for (int i = 9; i>=0; i=i-3){
            Set<Integer> result1 = findSquare(i, i, a);
            Set<Integer> result2 = findSquare(i, i, b);
            if (! result1.equals(result2)){
                return false;
            }
        }
        return true;
    }
}