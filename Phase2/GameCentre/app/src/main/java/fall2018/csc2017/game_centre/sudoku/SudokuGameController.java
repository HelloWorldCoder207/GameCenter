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

    /**
     * A Cell that denotes a selected cell. Value of null if no cell is selected.
     */
    private Cell selected;

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

    void answerButtonClicked(Context context, int buttonNum) {
        if (selected != null && !selected.isVisible) {
            if (selected.getValue() != buttonNum) {
                Toast.makeText(context,
                        "Wrong Answer", Toast.LENGTH_SHORT).show();
                gameState.increaseWrongCounter();
            } else {
                selected.changeToVisible();
                // Display
                setChanged();
                notifyObservers();
                if (puzzleSolved()) {
                    // Game Finished
                    setChanged();
                    notifyObservers(new int[]{gameState.getTotalTime(),
                            gameState.getHintCounter(), gameState.getWrongCounter()});
                }
            }
        }
    }

    private boolean puzzleSolved(){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!board.getCell(i, j).isVisible){
                    return false;
                }
            }
        }
        return true;
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
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board.getCell(i, j).decolorCell();
            }
        }

        touchCell(position);
    }

    private void touchCell(int position){
        int row = position / SudokuBoard.SIDE_LEN;
        int col = position % SudokuBoard.SIDE_LEN;
        Cell cell = board.getCell(row, col);
        if (cell.isVisible){
            selected = null;
            // If a cell is visible and value equals to the clicked cell, then color it.
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (board.getCell(i, j).isVisible && board.getCell(i, j).getValue() == cell.getValue()){
                        board.getCell(i, j).colorCell();
                    }
                }
            }
        }
        else {
            selected = cell;
            cell.colorCell();
        }
        // Display
        setChanged();
        notifyObservers();
    }
}
