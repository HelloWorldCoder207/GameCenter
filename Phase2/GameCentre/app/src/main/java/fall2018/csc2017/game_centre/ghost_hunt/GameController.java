package fall2018.csc2017.game_centre.ghost_hunt;

import java.io.Serializable;
import java.util.Observable;

/**
 * Handle game events. (Controller)
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
    static final Integer GAME_OVER = 0;

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
     */
    GameController() {
        this.fileHandler = new FileHandler();
    }

    /**
     * Start new game.
     */
    void startGame() {
        // TODO: start new game
    }

    /**
     * Load existing game.
     * @param permanent if load from permanent saving
     * @return if there is saved game
     */
    boolean loadGame(boolean permanent) {
        String fileName;
        if (permanent) {
            fileName = FileHandler.SAVE_FILENAME;
        } else {
            fileName = FileHandler.TEMP_FILENAME;
        }
        this.boardManager = fileHandler.loadFrom(fileName);
        return boardManager != null;
    }

    /**
     * Save the game.
     */
    void saveGame(boolean permanent) {
        String fileName;
        if (permanent) {
            fileName = FileHandler.SAVE_FILENAME;
        } else {
            fileName = FileHandler.TEMP_FILENAME;
        }
        fileHandler.saveTo(fileName, this.boardManager);
    }

    /**
     * Process direction change
     * @param direction direction of going
     */
    void processEvent(DirectionIntent direction) {
        // TODO: process direction change
    }

    /**
     * Getter for board manager.
     * @return
     */
    BoardManager getBoardManager() {
        return this.boardManager;
    }
}
