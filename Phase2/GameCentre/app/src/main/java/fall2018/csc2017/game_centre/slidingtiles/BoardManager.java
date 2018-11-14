package fall2018.csc2017.game_centre.slidingtiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import fall2018.csc2017.game_centre.Undoable;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
class BoardManager implements Serializable, Undoable {

    /**
     * The board being managed.
     */
    private Board board;

    /**
     * The counter that counts the moves made by player.
     */
    private int moveCounter = 0;

    /**
     * The maximum undo times that the user choose.
     */
    private final int MAX_UNDO;

    /**
     * The stack that is used for undo. Every move inside is made by an array of {row, col}
     * row and col both starts from 0.
     */
    private Stack<int[]> moveStack = new Stack<>();

    /**
     * Manage a new shuffled board.
     */
    BoardManager(int maxUndo, int boardLength) {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = boardLength * boardLength;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            if (boardLength == 3) {
                tiles.add(new EasyTile(tileNum));
            }
            else if (boardLength == 4){
                tiles.add(new MediumTile(tileNum));
            }
            else{
                tiles.add(new HardTile(tileNum));
            }
        }
        Collections.shuffle(tiles);
        this.board = new Board(tiles, boardLength);
        this.MAX_UNDO = maxUndo;
    }

    /**
     * Return the current board.
     */
    Board getBoard() {
        return board;
    }

    /**
     * Return the moves that the player has played.
     * @return counter
     */
    int getMoveCounter() {
        return moveCounter;
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    boolean puzzleSolved() {
        Tile lastElement = board.getTile(board.getNumRow() - 1, board.getNumCol() - 1);
        if (lastElement.getId() != board.numTiles()) {
            return false;
        }
        Iterator<Tile> bIterator = this.board.iterator();
        for (int i = 0; i < board.numTiles(); i++) {
            Tile temp = bIterator.next();
            if (temp.getId() != i + 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    boolean isValidTap(int position) {
        int row = position / board.getNumRow();
        int col = position % board.getNumCol();
        int blankId = board.numTiles();
        Tile[] nearbyTiles = generateNearbyTile(row, col);
        Tile above = nearbyTiles[0], below = nearbyTiles[1], left = nearbyTiles[2], right = nearbyTiles[3];
        return (below != null && below.getId() == blankId)
                || (above != null && above.getId() == blankId)
                || (left != null && left.getId() == blankId)
                || (right != null && right.getId() == blankId);
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    void touchMove(int position) {
        moveCounter++;
        int row = position / board.getNumRow();
        int col = position % board.getNumCol();
        int blankId = board.numTiles();
        Tile[] nearbyTiles = generateNearbyTile(row, col);
        Tile above = nearbyTiles[0], below = nearbyTiles[1],
                left = nearbyTiles[2], right = nearbyTiles[3];
        if (above != null && above.getId() == blankId) {
            this.board.swapTiles(row, col, row - 1, col); saveMove(row - 1, col); }
        if (below != null && below.getId() == blankId) {
            this.board.swapTiles(row, col, row + 1, col); saveMove(row + 1, col); }
        if (left != null && left.getId() == blankId) {
            this.board.swapTiles(row, col, row, col - 1); saveMove(row, col - 1); }
        if (right != null && right.getId() == blankId) {
            this.board.swapTiles(row, col, row, col + 1); saveMove(row, col + 1); }
    }

    /**
     * Returns a array of tiles which are above, below, left and right tiles of
     * the given tile at row, col
     *
     * @param row the # of row for the given tile
     * @param col the # of col for the given tile
     * @return an array of tiles
     */
    private Tile[] generateNearbyTile(int row, int col) {
        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == board.getNumRow() - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == board.getNumCol() - 1 ? null : board.getTile(row, col + 1);

        return new Tile[]{above, below, left, right};
    }

    /**
     * Save the move into moveStack.
     * @param row the num of row of last blank tile, starts at 0
     * @param col the num of col of last blank tile, starts at 0
     */
    private void saveMove(int row, int col){
        int[] move = {row, col};
        moveStack.push(move);
        if (moveStack.size() > MAX_UNDO){
            moveStack.remove(0);
        }
    }

    /**
     * Performs a move undo on the board.
     */
    @Override
    public void undo() {
        if (!moveStack.empty()){
            int blankId = board.numTiles();
            int[] move = moveStack.pop();
            Tile[] nearbyTiles = generateNearbyTile(move[0], move[1]);
            Tile above = nearbyTiles[0], below = nearbyTiles[1],
                    left = nearbyTiles[2], right = nearbyTiles[3];

            if (above != null && above.getId() == blankId)
                this.board.swapTiles(move[0], move[1], move[0] - 1, move[1]);
            if (below != null && below.getId() == blankId)
                this.board.swapTiles(move[0], move[1], move[0] + 1, move[1]);
            if (left != null && left.getId() == blankId)
                this.board.swapTiles(move[0], move[1], move[0], move[1] - 1);
            if (right != null && right.getId() == blankId)
                this.board.swapTiles(move[0], move[1], move[0], move[1] + 1);
        }
    }
}
