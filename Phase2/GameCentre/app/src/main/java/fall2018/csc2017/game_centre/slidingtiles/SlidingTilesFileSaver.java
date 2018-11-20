package fall2018.csc2017.game_centre.slidingtiles;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import fall2018.csc2017.game_centre.CurrentStatus;

import static android.content.Context.MODE_PRIVATE;

class SlidingTilesFileSaver {

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
     * Load the board manager from fileName.
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
        } catch (Exception e) {
            Log.e(LOG_TAG, "File Error." + e.toString());
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

    BoardManager getBoardManager(){
        return boardManager;
    }
}
