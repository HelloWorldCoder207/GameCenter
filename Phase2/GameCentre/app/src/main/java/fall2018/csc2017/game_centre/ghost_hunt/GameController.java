package fall2018.csc2017.game_centre.ghost_hunt;

import android.content.Context;

import java.io.Serializable;
import java.util.Observable;

/**
 * Controller
 *
 * Handle game events.
 */
class GameController extends Observable implements Serializable {

    /**
     * Intent extra value key for passing.
     */
    static final String INTENT_NAME = "GhostHuntController";

    /**
     * Argument indicating change of board.
     */
    static final Integer BOARD_CHANGE = 0;

    /**
     * Argument indicating end of game.
     */
    static final Integer GAME_OVER = 1;

    /**
     * Context of the activity.
     */
    private Context context;

    /**
     * File handler for the controller.
     */
    private FileHandler fileHandler;

    /**
     * Board manager.
     */
    private BoardManager boardManager;

    /**
     * Constructor for controller.
     * @param context context of activity
     */
    GameController(Context context) {
        this.context = context;
        this.fileHandler = FileHandler.getInstance();
        this.fileHandler.setBoardManager(boardManager);
    }

    /**
     * Setter for context
     * @param context context
     */
    void setContext(Context context) {
        this.context = context;
    }

    /**
     * Getter for board manager.
     * @return board manager
     */
    BoardManager getBoardManager() {
        return this.boardManager;
    }

    /**
     * Start new game.
     */
    void startGame() {
        this.boardManager = new BoardManager();
        this.fileHandler.setBoardManager(boardManager);
    }

    /**
     * Load existing game.
     * @return if there is saved game
     */
    boolean loadGame() {
        fileHandler.loadGame(context);
        this.boardManager = fileHandler.getBoardManager();
        return boardManager != null;
    }

    /**
     * Save the game.
     */
    void saveGame() {
        fileHandler.saveGame(context);
    }

    /**
     * Process direction change.
     * @param direction direction of going
     */
    void processEvent(DirectionIntent direction) {
        // TODO: process direction change
    }
}
