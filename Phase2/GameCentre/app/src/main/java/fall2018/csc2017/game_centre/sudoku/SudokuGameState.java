package fall2018.csc2017.game_centre.sudoku;

import java.io.Serializable;

import fall2018.csc2017.game_centre.GameTimer;

/**
 * Game state of sudoku. Contains sudoku board, hint counter, game time etc.
 */
class SudokuGameState implements Serializable {
    //TODO add sudoku board, hint counter, game time,
    /**
     * Board of sudoku.
     */
    private SudokuBoard board;

    /**
     * The number of remaining hints. Set to 3 initial.
     */
    private int hintCounter = 3;

    /**
     * The counter of moves. Uses for auto save.
     */
    private int moveCounter;

    /**
     * The timer for the game.
     */
    private GameTimer timer;

    SudokuGameState(int emptyCells){
        this.board = new SudokuBoard(emptyCells);
    }

    /**
     * getter for board.
     * @return the sudoku board.
     */
    SudokuBoard getBoard(){
        return this.board;
    }
}
