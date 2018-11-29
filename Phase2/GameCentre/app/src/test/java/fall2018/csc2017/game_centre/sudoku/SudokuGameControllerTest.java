package fall2018.csc2017.game_centre.sudoku;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SudokuGameControllerTest {

    private SudokuGameController  testGameController;
    private SudokuGameState gameState;
    private Context context;

    @Before
    public void setUp() throws Exception {
        testGameController = new SudokuGameController();
        SudokuBoard board = new SudokuBoard(1);
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



    }

    @Test
    public void processTapMovement() {
    }

    @Test
    public void hint() {
    }
}