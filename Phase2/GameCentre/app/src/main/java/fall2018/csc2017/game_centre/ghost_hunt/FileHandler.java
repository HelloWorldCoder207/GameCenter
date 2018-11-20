package fall2018.csc2017.game_centre.ghost_hunt;

import java.util.HashMap;
import java.util.Map;

/**
 * Handler for file IO in Ghost Hunt.
 */
class FileHandler {

    static final String TEMP_FILENAME = "ghost_hunt_temp.ser";

    static final String SAVE_FILENAME = "ghost_hunt_save.ser";

    /**
     * Mapping from user name to corresponding board manager.
     */
    private Map<String, BoardManager> boardManagerMap;

    /**
     * Load data from file.
     * @param fileName file name
     * @return current user's board manager
     */
    BoardManager loadFrom(String fileName) {
        // TODO: load from file
        return null;
    }

    void saveTo(String fileName, BoardManager boardManager) {
        // TODO: save to file

    }
}
