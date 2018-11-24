package fall2018.csc2017.game_centre.slidingtiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
class BoardManager implements Serializable {

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
     * @param maxUndo the maximum number of undo this game can have
     * @param boardLength the length of the board
     */
    BoardManager(int maxUndo, int boardLength) {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = boardLength * boardLength;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        do {
            Collections.shuffle(tiles);
        } while (!isSolvable(tiles, boardLength));
        this.board = new Board(tiles, boardLength);
        this.MAX_UNDO = maxUndo;
    }

    /**
     * Constructor for testing usage.
     */
    BoardManager(int maxUndo, Board board) {
        this.board = board;
        this.MAX_UNDO = maxUndo;
    }

    /**
     * Check if the board is solvable. Adapted from
     * http://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html
     *
     * @param tiles array of tiles to check
     * @param length size of the grid
     * @return if the board is solvable
     */
    private boolean isSolvable(List<Tile> tiles, int length) {
        int inversion = countInversion(tiles);
        if (length % 2 == 1) {
            return inversion % 2 == 0;
        } else {
            int blankRow = countBlankRowFromBottom(tiles, length);
            return (blankRow % 2 == 1) == (inversion % 2 == 0);
        }
    }

    /**
     * Count the amount of inversions in the board.
     *
     * @param tiles array of tiles to check
     * @return amount of inversions
     */
    private int countInversion(List<Tile> tiles) {
        int counter = 0;
        int length = tiles.size();
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < i; j++) {
                int jId = tiles.get(j).id;
                int iId = tiles.get(i).id;
                if (jId > iId && jId != length && iId != length) {
                    counter++;
                }
            }
        }
        return counter;
    }

    /**
     * Get the blank tile's row counting from bottom.
     *
     * @param tiles array of tiles to check
     * @param length size of the grid
     * @return blank tile's row
     */
    private int countBlankRowFromBottom(List<Tile> tiles, int length) {
        int res = 0;
        boolean notFound = true;
        int i = tiles.size() - 1;
        while (notFound && i >= 0) {
            if (tiles.get(i).id == length * length) {
                res = (tiles.size() - i) / length + 1;
                notFound = false;
            }
            i--;
        }
        return res;
    }

    /**
     * Return the current board.
     * @return the board of game.
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

//    /**
//     * Return whether the tiles are in row-major order.
//     *
//     * @return whether the tiles are in row-major order
//     */
//    boolean puzzleSolved() {
//        Tile lastElement = board.getTile(board.getNumRow() - 1, board.getNumCol() - 1);
//        if (lastElement.getId() != board.numTiles()) {
//            return false;
//        }
//        Iterator<Tile> bIterator = this.board.iterator();
//        for (int i = 0; i < board.numTiles(); i++) {
//            Tile temp = bIterator.next();
//            if (temp.getId() != i + 1) {
//                return false;
//            }
//        }
//        return true;
//    }

//    /**
//     * Return whether any of the four surrounding tiles is the blank tile.
//     *
//     * @param position the tile to check
//     * @return whether the tile at position is surrounded by a blank tile
//     */
//    boolean isValidTap(int position) {
//        int row = position / board.getNumRow();
//        int col = position % board.getNumCol();
//        int blankId = board.numTiles();
//        Tile[] nearbyTiles = generateNearbyTile(row, col);
//        Tile above = nearbyTiles[0], below = nearbyTiles[1], left = nearbyTiles[2], right = nearbyTiles[3];
//        return (below != null && below.getId() == blankId)
//                || (above != null && above.getId() == blankId)
//                || (left != null && left.getId() == blankId)
//                || (right != null && right.getId() == blankId);
//    }

//    /**
//     * Process a touch at position in the board, swapping tiles as appropriate.
//     *
//     * @param position the position
//     */
//    void touchMove(int position) {
//        moveCounter++;
//        int row = position / board.getNumRow();
//        int col = position % board.getNumCol();
//        int blankId = board.numTiles();
//        Tile[] nearbyTiles = generateNearbyTile(row, col);
//        Tile above = nearbyTiles[0], below = nearbyTiles[1],
//                left = nearbyTiles[2], right = nearbyTiles[3];
//        if (above != null && above.getId() == blankId) {
//            this.board.swapTiles(row, col, row - 1, col); saveMove(row - 1, col); }
//        if (below != null && below.getId() == blankId) {
//            this.board.swapTiles(row, col, row + 1, col); saveMove(row + 1, col); }
//        if (left != null && left.getId() == blankId) {
//            this.board.swapTiles(row, col, row, col - 1); saveMove(row, col - 1); }
//        if (right != null && right.getId() == blankId) {
//            this.board.swapTiles(row, col, row, col + 1); saveMove(row, col + 1); }
//    }

//    /**
//     * Returns a array of tiles which are above, below, left and right tiles of
//     * the given tile at row, col
//     *
//     * @param row the # of row for the given tile
//     * @param col the # of col for the given tile
//     * @return an array of tiles
//     */
//    private Tile[] generateNearbyTile(int row, int col) {
//        Tile above = row == 0 ? null : board.getTile(row - 1, col);
//        Tile below = row == board.getNumRow() - 1 ? null : board.getTile(row + 1, col);
//        Tile left = col == 0 ? null : board.getTile(row, col - 1);
//        Tile right = col == board.getNumCol() - 1 ? null : board.getTile(row, col + 1);
//
//        return new Tile[]{above, below, left, right};
//    }

    /**
     * Increase the move counter by 1.
     */
    void increaseMoveCounter(){
        this.moveCounter++;
    }

    /**
     * Save the move into moveStack.
     * @param row the num of row of last blank tile, starts at 0
     * @param col the num of col of last blank tile, starts at 0
     */
    void saveMove(int row, int col){
        int[] move = {row, col};
        moveStack.push(move);
        if (moveStack.size() > MAX_UNDO){
            moveStack.remove(0);
        }
    }

    boolean isMoveStackEmpty(){
        return !moveStack.empty();
    }

    int[] getLastMove(){
        return moveStack.pop();
    }
//    /**
//     * Performs a move undo on the board.
//     */
//    @Override
//    public void undo() {
//        if (!moveStack.empty()){
//            int blankId = board.numTiles();
//            int[] move = moveStack.pop();
//            Tile[] nearbyTiles = generateNearbyTile(move[0], move[1]);
//            Tile above = nearbyTiles[0], below = nearbyTiles[1],
//                    left = nearbyTiles[2], right = nearbyTiles[3];
//
//            if (above != null && above.getId() == blankId)
//                this.board.swapTiles(move[0], move[1], move[0] - 1, move[1]);
//            if (below != null && below.getId() == blankId)
//                this.board.swapTiles(move[0], move[1], move[0] + 1, move[1]);
//            if (left != null && left.getId() == blankId)
//                this.board.swapTiles(move[0], move[1], move[0], move[1] - 1);
//            if (right != null && right.getId() == blankId)
//                this.board.swapTiles(move[0], move[1], move[0], move[1] + 1);
//        }
//    }
}
