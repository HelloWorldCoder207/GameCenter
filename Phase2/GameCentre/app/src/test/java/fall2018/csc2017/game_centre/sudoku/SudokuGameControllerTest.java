package fall2018.csc2017.game_centre.sudoku;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SudokuGameControllerTest {

    private SudokuGameController  testGameController;
    private SudokuGameState gameState;
    private SudokuBoard board;
    private Context context;
    private Cell[][] grid;
    private int position;
    private Cell blankSelected;
    private int value;

    @Before
    public void setUp() throws Exception {
        gameState = new SudokuGameState(1,"difficulty");
        testGameController = new SudokuGameController();
        board = new SudokuBoard(1);
        for(int i = 0; i<=8; i++){
            for (int j= 0; j<=8;j++){
                Cell cell = board.getCell(i, j);
                if (! cell.isVisible){
                    position = cell.getPosition();
                    blankSelected = cell;
                    value = blankSelected.getValue();
                }
            }
        }

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void setGameState() {
    }

    @Test
    public void answerButtonClicked() {
        testGameController.setGameState(gameState);
        testGameController.processTapMovement(position);
        testGameController.answerButtonClicked(context,value);
        assertTrue(testGameController.puzzleSolved());

    }

    @Test
    public void processTapMovement() {
    }

    @Test
    public void hint() {
    }
}