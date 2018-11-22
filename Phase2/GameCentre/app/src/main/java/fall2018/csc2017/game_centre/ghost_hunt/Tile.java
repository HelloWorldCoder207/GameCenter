package fall2018.csc2017.game_centre.ghost_hunt;

import java.util.ArrayList;
import java.util.List;

/**
 * A tile in the board.
 */
class Tile {

    /**
     * List of available moves in current tile.
     */
    private List<Direction> availableMoves;

    /**
     * Constructor taking in a list of available moves.
     * @param availableMoves available moves
     */
    Tile(ArrayList<Direction> availableMoves) {
        this.availableMoves = availableMoves;
    }

    /**
     * Return if move is valid under move
     * @return if possible
     */
    List<Direction> getAvailableMoves() {
        return availableMoves;
    }
}
