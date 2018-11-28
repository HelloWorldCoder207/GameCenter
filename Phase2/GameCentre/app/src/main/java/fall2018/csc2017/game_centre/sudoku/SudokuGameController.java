package fall2018.csc2017.game_centre.sudoku;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
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

    private SudokuFileHandler fileHandler = SudokuFileHandler.getInstance();

    /**
     * A Cell that denotes a selected BLANK cell. Value of null if no cell is selected.
     */
    private Cell selected;

    private List<Cell> coloredCells = new ArrayList<>();

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

                fileHandler.saveToFile(context);
                // Display
                coloredCells = new ArrayList<>();
                coloredCells.add(selected);
                setChanged();
                notifyObservers(coloredCells);
                selected = null;
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
        ArrayList<Cell> cellsNeedToChange = new ArrayList<>(coloredCells);
        if (cell.isVisible){
            selected = null;
            // If a cell is visible and value equals to the clicked cell, then color it.
//            for (int i = 0; i < 9; i++) {
//                for (int j = 0; j < 9; j++) {
//                    if (board.getCell(i, j).isVisible && board.getCell(i, j).getValue() == cell.getValue()){
//                        board.getCell(i, j).colorCell();
//                    }
//                }
//            }
            for (Cell sameValueCell : cell.getSameCells()) {
                if (sameValueCell.isVisible) {
                    sameValueCell.colorCell();
                }
            }
            coloredCells = cell.getSameCells();
            cellsNeedToChange.addAll(cell.getSameCells());
        } else {
            selected = cell;
            cell.colorCell();
            coloredCells = new ArrayList<>(); coloredCells.add(cell);
            cellsNeedToChange.add(cell);
        }
        // Display
        setChanged();
        notifyObservers(cellsNeedToChange);
    }

    void hint(Context context){
        if (gameState.getHintCounter() <= 0){
            makeToastText(context, "No Hint Remains");
        }
        else {
            if (selected == null){
                makeToastText(context, "Please Select A Empty Cell");
            }
            else {
                selected.changeToVisible();
                selected = null;
                gameState.decreaseHintCounter();
                setChanged();
                notifyObservers();
            }
        }
    }

    /**
     * Display message in sliding tiles starting activity.
     * @param context the context to be used by Toast.
     * @param msg the message to display
     */
    private void makeToastText(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
