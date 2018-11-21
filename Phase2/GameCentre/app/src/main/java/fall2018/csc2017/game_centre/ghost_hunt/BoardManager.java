package fall2018.csc2017.game_centre.ghost_hunt;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Handle the game information board.
 */
class BoardManager {

    /**
     * Board of the game.
     */
    private Board board;

    /**
     * Getter of the board.
     * @return the board
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * The counter that counts the moves made by player.
     */
    private int moveCounter = 0;


    /**
     * Return the moves that the player has played.
     * @return counter
     */
    int getMoveCounter() {
        return moveCounter;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param tile the tile to check
     * @param move the move to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    private Boolean isValidMove(Tile tile, int move) {
        return tile.isAvailable(move);
    }

    private int up=0;
    private int left=1;
    private int right=0;
    private int down=0;
    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     * @param move the move
     */
    void Move(int position, int move) {
        moveCounter++;
        int row = position / board.getNumRow();
        int col = position % board.getNumCol();
        if (isValidMove(board.getTile(row, col), move)) {
            if (move == up) {this.board.makeMove(row, col, row - 1, col);}
            else if (move==down) {this.board.makeMove(row, col, row + 1, col);}
            else if (move==left) {this.board.makeMove(row, col, row, col-1);}
            else if (move==right) {this.board.makeMove(row, col, row, col+1);}
        }
    }



}

