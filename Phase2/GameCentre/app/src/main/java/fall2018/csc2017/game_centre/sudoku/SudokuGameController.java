package fall2018.csc2017.game_centre.sudoku;

import android.content.Context;
import android.widget.Toast;

import java.util.Observable;

class SudokuGameController extends Observable {

    /**
     * The sudokuBoard board of the game.
     */
    private SudokuBoard board;

    /**
     * The Game state of sudoku.
     */
    private SudokuGameState gameState;

    SudokuGameController(){}

    /**
     * Setter for game state.
     *
     * @param gameState the boardManager to be set
     */
    void setGameState(SudokuGameState gameState) {
        this.gameState = gameState;
        this.board = gameState.getBoard();
    }

    /**
     * process tap on the board. update the color of background.
     *
     * @param context  context, suppose to be the gameActivity, used for file io.
     * @param position the position that the player chooses
     */
    void processTapMovement(Context context, int position) {
//        if (isValidTap(position)) {
//            touchMove(position);
//
//            if (boardManager.getMoveCounter() % 5 == 0) {
//                fileSaver.saveToFile(context, SlidingTilesFileHandler.SAVE_FILENAME);
//            }
//
//            if (puzzleSolved()) {
//                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
//                setChanged();
//                notifyObservers(boardManager.getMoveCounter());
//            }
//        } else {
//            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
//        }

        touchCell(position);
    }

    private void touchCell(int position){
        int row = position / SudokuBoard.SIDE_LEN;
        int col = position % SudokuBoard.SIDE_LEN;
        Cell cell = board.getCell(row, col);
        if (cell.isVisible){

        }
        else {

        }
    }
}
