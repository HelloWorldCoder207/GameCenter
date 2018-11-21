package fall2018.csc2017.game_centre.ghost_hunt;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fall2018.csc2017.game_centre.CurrentStatus;

/**
 * Model
 *
 * Handler for file IO in Ghost Hunt. Made into singleton.
 */
class FileHandler {

    /**
     * Sole instance of file handler.
     */
    private static final FileHandler INSTANCE = new FileHandler();

    /**
     * Logging tag.
     */
    private final String LOG_TAG = "GhostHuntFileHandler";

    /**
     * Saving file suffix.
     * To be combined with user name to compose entire file name.
     */
    private static final String SAVE_SUFFIX = "_ghost_hunt.ser";

    /**
     * Board manager.
     */
    private BoardManager boardManager;

    /**
     * Private constructor for singleton.
     */
    private FileHandler() {}

    /**
     * Return the singleton instance.
     * @return sole instance of file handler
     */
    static FileHandler getInstance() {
        return INSTANCE;
    }

    /**
     * Getter for board manager.
     * @return board manager
     */
    BoardManager getBoardManager() {
        return this.boardManager;
    }

    /**
     * Setter for board manager.
     * @param boardManager board manager
     */
    void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    /**
     * Load data from current user's file.
     * @param context context
     */
    void loadGame(Context context) {
        String fileName = CurrentStatus.getCurrentUser().getUsername() + SAVE_SUFFIX;
        try {
            InputStream inputStream = context.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                boardManager = (BoardManager) input.readObject();
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
     * Save data to current user's file.
     * @param context context
     */
    void saveGame(Context context) {
        String fileName = CurrentStatus.getCurrentUser().getUsername() + SAVE_SUFFIX;
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStream.writeObject(boardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e(LOG_TAG, "File write failed: " + e.toString());
        }
    }
}
