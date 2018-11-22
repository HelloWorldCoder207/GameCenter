package fall2018.csc2017.game_centre.ghost_hunt;

import java.util.ArrayList;
import java.util.List;

class Tile {

    /**
     * Return the background of the tile.
     * @return background id
     */
    int getBackground() {
        // TODO: get background
        return 0;
    }

    public int up=0;
    public int left=1;
    public int right=0;
    public int down=0;

    protected List<Boolean> availableMoves;

    /**
     * The tiles on the board in row-major order.
     */

    String who="";

    /**
     * Get ID for the tile.
     * @return ID
     */
    int getID() {
        // TODO: get id
        return 0;
    }

    Boolean isAvailable(int move) {
        return availableMoves.get(move);
    }
}
