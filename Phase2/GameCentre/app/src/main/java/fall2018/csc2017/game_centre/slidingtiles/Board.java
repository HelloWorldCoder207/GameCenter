package fall2018.csc2017.game_centre.slidingtiles;

import android.support.annotation.NonNull;

import java.util.NoSuchElementException;
import java.util.Observable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * The sliding tiles board.
 */
public class Board extends Observable implements Serializable, Iterable<Tile> {

    /**
     * The number of rows.
     */
    private int numRow;

    /**
     * The number of rows.
     */
    private int numCol;

    /**
     * The tiles on the board in row-major order.
     */
    private Tile[][] tiles;

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param newTiles the tiles for the board
     * @param length length of the board
     */
    Board(List<Tile> newTiles, int length) {
        numRow = numCol = length;
        tiles = new Tile[numRow][numCol];
        Iterator<Tile> iter = newTiles.iterator();

        for (int row = 0; row != numRow; row++) {
            for (int col = 0; col != numCol; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
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

    /**
     * Return the number of tiles on the board.
     *
     * @return the number of tiles on the board
     */
    int numTiles() {
        return numRow * numCol;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    void swapTiles(int row1, int col1, int row2, int col2) {
        Tile first = getTile(row1, col1);
        Tile second = getTile(row2, col2);

        this.tiles[row1][col1] = second;
        this.tiles[row2][col2] = first;

        setChanged();
        notifyObservers();
    }

    /**
     * The toString method for Board
     *
     * @return a String representation of Board
     */
    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }

    /**
     * The Iterator method that iterates the board
     *
     * @return an iterator of Board.
     */
    @NonNull
    @Override
    public Iterator<Tile> iterator() {
        return new BoardIterator();
    }

    /**
     * The Iterator of SlidingTiles Board, implements Iterator interface
     */
    class BoardIterator implements Iterator<Tile> {

        /**
         * The iteration point for rows
         */
        private int i = 0;

        /**
         * The iteration point for columns
         */
        private int j = 0;

        /**
         * Determine if the iterator has a next element
         *
         * @return a boolean value
         */
        @Override
        public boolean hasNext() {
            return i < numRow && j < numCol;
        }

        /**
         * returns the value of the current Tile
         * and updates the iteration to one place further.
         *
         * @return the Tile at i, j
         */
        @Override
        public Tile next() {
            if (this.hasNext()) {
                Tile result = tiles[i][j];
                j += 1;
                if (j == numCol) {
                    i += 1;
                    j = 0;
                }
                return result;
            }
            throw new NoSuchElementException();
        }
    }
}
