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
    private Cell cell;
    private int value;
    private int background;

    @Before
    public void setUp() throws Exception {
        gameState = new SudokuGameState(40,"test difficulty");
        testGameController = new SudokuGameController();
        testGameController.setGameState(gameState);
        board = gameState.getBoard();

//        for(int i = 0; i<=8; i++){
//            for (int j= 0; j<=8;j++){
//                Cell cell = gameState.getBoard().getCell(i, j);
//                if (! cell.isVisible){
//                    position = cell.getPosition();
//                    blankSelected = cell;
//                    value = blankSelected.getValue();
//                }
//            }
//        }

    }

    @Test
    public void setGameState() {
    }

    @Test
    public void TestAnswerButtonWrongAnswer() {
        outerLoop:
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!board.getCell(i, j).isVisible){
                    cell = board.getCell(i, j);
                    position = cell.getPosition();
                    value = cell.getValue();
                    break outerLoop;
                }
            }
        }
        testGameController.processTapMovement(position);
        value++;
        if (value == 10) { value = 1; }
        testGameController.answerButtonClicked(context, value);
        assertFalse(testGameController.puzzleSolved());
        assertFalse(cell.isVisible);

    }

    @Test
    public void testProcessTapMovementVisibleCell() {
        outerLoop:
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.getCell(i, j).isVisible){
                    cell = board.getCell(i, j);
                    cell.setColoredBackground(1);
                    cell.setNumberBackground(2);
                    position = cell.getPosition();
                    background = cell.getBackground();
                    break outerLoop;
                }
            }
        }
        testGameController.processTapMovement(position);
        assertNotEquals(background, cell.getBackground());
    }

    @Test
    public void testProcessTapMovementInvisibleCell() {
        outerLoop:
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!board.getCell(i, j).isVisible){
                    cell = board.getCell(i, j);
                    position = cell.getPosition();
                    background = cell.getBackground();
                    break outerLoop;
                }
            }
        }
        testGameController.processTapMovement(position);
        assertNotEquals(background, cell.getBackground());
    }

    @Test
    public void hint() {
    }
}