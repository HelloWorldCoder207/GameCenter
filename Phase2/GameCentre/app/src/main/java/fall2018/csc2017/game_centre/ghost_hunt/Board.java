package fall2018.csc2017.game_centre.ghost_hunt;

import java.util.Observable;

/**
 * Board for ghost hunt. Contains information of the game.
 */
class Board extends Observable {

    /**
     * The number of rows.
     */
    private int numRow;

    /**
     * The number of rows.
     */
    private int numCol;

     /**
     * Move the Player from (row1, col1) to (row2, col2)
     *
     * @param row1 the player tile row
     * @param col1 the player tile col
     * @param row2 the destination tile row
     * @param col2 the destination tile col
     */
    void makeMove(int row1, int col1, int row2, int col2) {
        Tile player = getTile(row1, col1);
        this.tiles[row1][col1].who="";
        this.tiles[row2][col2].who=player.who;

        setChanged();
        notifyObservers();
    }
    /**
     * The tiles on the board in row-major order.
     */

    private Tile[][] tiles;

    /**
     * Get the tile in the board located at row, col.
     * @param row row number
     * @param col column number
     * @return tile at row, col
     */
    Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Getter for number of rows.
     * @return number of rows
     */
    int getNumRow() {
        return numRow;
    }

    /**
     * Getter for number of columns.
     * @return number of columns
     */
    int getNumCol() {
        return numCol;
    }

}
