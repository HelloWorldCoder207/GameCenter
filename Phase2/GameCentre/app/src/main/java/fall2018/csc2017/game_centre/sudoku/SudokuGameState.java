package fall2018.csc2017.game_centre.sudoku;

import fall2018.csc2017.game_centre.GameTimer;

/**
 * Game state of sudoku. Contains sudoku board, hint counter, game time etc.
 */
public class SudokuGameState {
    //TODO add sudoku board, hint counter, game time,
    /**
     * Board of sudoku.
     */
    private SudokuBoard board;

    /**
     * The number of remaining hints. Set to 3 initial.
     */
    private int hintCounter;

    /**
     * The timer for the game.
     */
    private GameTimer timer;


}
