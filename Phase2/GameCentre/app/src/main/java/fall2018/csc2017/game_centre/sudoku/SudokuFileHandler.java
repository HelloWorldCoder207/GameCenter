package fall2018.csc2017.game_centre.sudoku;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fall2018.csc2017.game_centre.CurrentStatus;
import fall2018.csc2017.game_centre.Game;

import static android.content.Context.MODE_PRIVATE;

/**
 * Model
 *
 * Deal with file input/output for sudoku.
 */
class SudokuFileHandler {
    /**
     * The Log tag of logging.
     */
    private static final String LOG_TAG = "SudokuFileHandler";
    /**
     * The state of current game.
     */
    private SudokuGameState gameState;

    /**
     * The file handler instance/
     */
    private static SudokuFileHandler fileHandler;

    private SudokuFileHandler(){}

    static SudokuFileHandler getInstance(){
        if (fileHandler == null){
            fileHandler = new SudokuFileHandler();
        }
        return fileHandler;
    }

    /**
     * Load the game state from user's fileName.
     * @param context  The context that got adapted from activity
     */
    void loadFromFile(Context context) {
        try {
            InputStream inputStream = context.openFileInput(
                    CurrentStatus.getCurrentUser().getUserFilename(Game.Sudoku));
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                gameState = (SudokuGameState) input.readObject();
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
     * Save the game state to fileName.
     *
     * @param context  the activity
     */
    void saveToFile(Context context) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(context.openFileOutput(
                    CurrentStatus.getCurrentUser().getUserFilename(Game.Sudoku), MODE_PRIVATE));
            outputStream.writeObject(gameState);
            outputStream.close();
        } catch (IOException e) {
            Log.e(LOG_TAG, "File write failed: " + e.toString());
        }
    }

    void setGameState(SudokuGameState gameState) {
        this.gameState = gameState;
    }

    SudokuGameState getGameState() {
        return gameState;
    }
}
