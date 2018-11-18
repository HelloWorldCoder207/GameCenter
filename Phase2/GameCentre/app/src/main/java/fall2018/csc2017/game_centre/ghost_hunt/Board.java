package fall2018.csc2017.game_centre.ghost_hunt;

import java.util.Observable;

/**
 * Board for ghost hunt. Contains information of the game.
 */
class Board extends Observable {

    /**
     * Get the tile in the board located at row, col.
     * @param row row number
     * @param col column number
     * @return tile at row, col
     */
    Tile getTile(int row, int col) {
        // TODO: get tile
        return new Tile();
    }

    int getNumRow() {
        return 0;
    }

    int getNumCol() {
        return 0;
    }
}
