package fall2018.csc2017.game_centre.sudoku;

import java.io.Serializable;
import java.util.Observable;

import fall2018.csc2017.game_centre.GameTimer;

/**
 * Game state of sudoku. Contains sudoku board, hint counter, game time etc.
 */
class SudokuGameState extends Observable implements Serializable {
    /**
     * Board of sudoku.
     */
    private SudokuBoard board;

    /**
     * The counter of remaining hints. Set to 3 initial.
     */
    private int hintCounter = 3;

    /**
     * The counter of wrongAttempts. If wrong answer reaches 4 than game is over.
     */
    private int wrongCounter;

    /**
     * The timer for the game.
     */
    private GameTimer timer;

    SudokuGameState(int emptyCells){
        this.board = new SudokuBoard(emptyCells);
        timer = new GameTimer();
    }

    GameTimer getTimer() {
        return timer;
    }

    /**
     * getter for board.
     * @return the sudoku board.
     */
    SudokuBoard getBoard(){
        return this.board;
    }

    int getWrongCounter() {
        return wrongCounter;
    }

    void increaseWrongCounter(){
        wrongCounter++;
        if (wrongCounter == 4){
            setChanged();
            notifyObservers();
        }
    }

    void decreaseHintCounter(){
        hintCounter--;
    }

    int getTotalTime(){
        return timer.getTotalTime();
    }

    int getHintCounter() {
        return hintCounter;
    }
}
