package fall2018.csc2017.game_centre.ghost_hunt;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Handle the game information board.
 */
class GameState {

    /**
     * Board of the game.
     */
    private Board board;

    /**
     * The counter that counts the moves made by player.
     */
    private int moveCounter;

    GameState() {
        this.board = new Board(1);
        this.moveCounter = 0;
    }

    /**
     * Getter of the board.
     * @return the board
     */
    public Board getBoard() {
        return this.board;
    }
}

