package fall2018.csc2017.game_centre.slidingtiles;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import fall2018.csc2017.game_centre.CurrentStatus;

import static android.content.Context.MODE_PRIVATE;

/**
 * File saver for slidingTile game. Implemented as a singleton class.
 */
class SlidingTilesFileSaver {

    /**
     * The main save file.
     */
    static final String SAVE_FILENAME = "save_file.ser";

    /**
     * Mapping from username to corresponding board manager.
     */
    private Map<String, BoardManager> boardManagers;

    /**
     * The board manager.
     */
    private BoardManager boardManager;

    /**
     * Tag for logging.
     */
    private static final String LOG_TAG = "SlidingTilesFileSaver";

    /**
     * The fileSaver instance.
     */
    private static SlidingTilesFileSaver fileSaver;

    /**
     * Private constructor for singleton.
     */
    private SlidingTilesFileSaver() {
    }

    /**
     * The getter for Singleton File Saver.
     *
     * @return the file saver as needed
     */
    static SlidingTilesFileSaver getInstance() {
        if (fileSaver == null) {
            fileSaver = new SlidingTilesFileSaver();
        }
        return fileSaver;
    }

    /**
     * Load the board manager from fileName.
     * @param context  The context that got adapted from activity
     * @param fileName The name of the file
     */
    void loadFromFile(Context context, String fileName) {
        try {
            InputStream inputStream = context.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                boardManagers = (HashMap<String, BoardManager>) input.readObject();
                boardManager = boardManagers.get(CurrentStatus.getCurrentUser().getUsername());
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e(LOG_TAG, "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e(LOG_TAG, "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e(LOG_TAG, "File contained unexpected data type: " + e.toString());
        } catch (NullPointerException e) {
            Log.e(LOG_TAG, "Calling on null reference: " + e.toString());
        }
    }

    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     * @param context  the activity
     */
    void saveToFile(Context context, String fileName) {
        try {
            if (boardManagers == null) {
                boardManagers = new HashMap<>();
            }
            boardManagers.put(CurrentStatus.getCurrentUser().getUsername(), boardManager);
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    context.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(boardManagers);
            outputStream.close();
        } catch (IOException e) {
            Log.e(LOG_TAG, "File write failed: " + e.toString());
        }
    }

    /**
     * The getter for BoardManager.
     *
     * @return The instance of boardManager.
     */
    BoardManager getBoardManager() {
        return boardManager;
    }

    /**
     * The setter for BoardManager.
     *
     * @param obj the boardManager that's to be set.
     */
    void setBoardManager(BoardManager obj) {
        boardManager = obj;
    }
}
