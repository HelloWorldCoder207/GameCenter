package fall2018.csc2017.game_centre.slidingtiles;

import android.content.Context;
import android.widget.Toast;

import java.util.Iterator;
import java.util.Observable;

import fall2018.csc2017.game_centre.Undoable;

class GameController extends Observable implements Undoable {

    /**
     * Current board manager.
     */
    private BoardManager boardManager = null;

    /**
     * Current board of the game.
     */
    private Board board;

    /**
     * The file saver for game.
     */
    private SlidingTilesFileSaver fileSaver;

    /**
     * Constructor.
     */
    GameController() {
        fileSaver = SlidingTilesFileSaver.getInstance();
    }

    /**
     * Setter for boardManager
     *
     * @param boardManager the boardManager to be set
     */
    void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
        this.board = boardManager.getBoard();
    }

    /**
     * Check if the move is valid, if yes, process the move. Also checks whether the game has ended
     * or not. Also uses the counter to auto save the game.
     *
     * @param context  context, suppose to be the gameActivity, used for file io.
     * @param position the position that the player chooses
     */
    void processTapMovement(Context context, int position) {
        if (isValidTap(position)) {
            touchMove(position);

            if (boardManager.getMoveCounter() % 5 == 0) {
                fileSaver.saveToFile(context, SlidingTilesFileSaver.SAVE_FILENAME);
            }

            if (puzzleSolved()) {
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
                setChanged();
                notifyObservers(boardManager.getMoveCounter());
            }
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    private boolean puzzleSolved() {
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
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    private void touchMove(int position) {
        boardManager.increaseMoveCounter();
        int row = position / board.getNumRow();
        int col = position % board.getNumCol();
        int blankId = board.numTiles();
        Tile[] nearbyTiles = generateNearbyTile(row, col);
        Tile above = nearbyTiles[0], below = nearbyTiles[1],
                left = nearbyTiles[2], right = nearbyTiles[3];
        if (above != null && above.getId() == blankId) {
            this.board.swapTiles(row, col, row - 1, col);
            boardManager.saveMove(row - 1, col);
        }
        if (below != null && below.getId() == blankId) {
            this.board.swapTiles(row, col, row + 1, col);
            boardManager.saveMove(row + 1, col);
        }
        if (left != null && left.getId() == blankId) {
            this.board.swapTiles(row, col, row, col - 1);
            boardManager.saveMove(row, col - 1);
        }
        if (right != null && right.getId() == blankId) {
            this.board.swapTiles(row, col, row, col + 1);
            boardManager.saveMove(row, col + 1);
        }
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    private boolean isValidTap(int position) {
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
     * Performs a move undo on the board.
     */
    @Override
    public void undo() {
        if (boardManager.isMoveStackEmpty()) {
            int blankId = board.numTiles();
            int[] move = boardManager.getLastMove();
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