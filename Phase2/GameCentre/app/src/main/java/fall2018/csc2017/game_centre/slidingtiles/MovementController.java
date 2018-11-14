package fall2018.csc2017.game_centre.slidingtiles;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import fall2018.csc2017.game_centre.CurrentStatus;

import static android.content.Context.MODE_PRIVATE;


class MovementController extends Observable {

    /**
     * Current board manager.
     */
    private BoardManager boardManager = null;

    /**
     * Mapping from username to corresponding user's board manager.
     */
    private Map<String, BoardManager> boardManagers;

    /**
     * Tag for logging.
     */
    private static final String LOG_TAG = "MovementController";

    /**
     * Constructor.
     */
    MovementController() {
    }

    void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    /**
     * Check if the move is valid, if yes, process the move. Also checks whether the game has ended
     * or not. Also uses the counter to auto save the game.
     * @param context context
     * @param position the position that the player chooses
     */
    void processTapMovement(Context context, int position) {
        if (boardManager.isValidTap(position)) {
            boardManager.touchMove(position);

            if (boardManager.getMoveCounter() % 5 == 0) {
                autoSaveToFile(context);
            }

            if (boardManager.puzzleSolved()) {
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
                setChanged(); notifyObservers(boardManager.getMoveCounter());
            }
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Auto save the board manager to fileName.
     */
    private void autoSaveToFile(Context context){
            loadFromFile(context);
            boardManagers.put(CurrentStatus.getCurrentUser().getUsername(), boardManager);
            saveToFile(context);
    }

    /**
     * Load the board manager from fileName.
     *
     * @param context the context that is used for inputStream. Basically GameActivity.
     */
    private void loadFromFile(Context context) {
        try {
            InputStream inputStream = context.openFileInput(SlidingTilesStartingActivity.SAVE_FILENAME);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                boardManagers = (HashMap<String, BoardManager>) input.readObject();

                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e(LOG_TAG, "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e(LOG_TAG, "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e(LOG_TAG, "File contained unexpected data type: " + e.toString());
        } catch (NullPointerException e) {
            Log.e(LOG_TAG, "Calling on null reference:" + e.toString());
        } finally {
            if (boardManagers == null) {
                boardManagers = new HashMap<>();
            }
        }
    }

    /**
     * Save the board manager to fileName.
     *
     * @param context the Context that got passed down
     */
    private void saveToFile(Context context) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    context.openFileOutput(SlidingTilesStartingActivity.SAVE_FILENAME, MODE_PRIVATE));
            outputStream.writeObject(boardManagers);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
