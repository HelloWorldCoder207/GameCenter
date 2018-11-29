package fall2018.csc2017.game_centre.slidingtiles;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class SlidingTilesScoreBoardFileHandler {
    /**
     * The save file.
     */
    public final String SAVE_SCOREBOARD = "save_slidetiles_scoreboard.ser";

    /**
     * The fileSaver instance.
     */
    private static SlidingTilesScoreBoardFileHandler fileSaver;

    /**
     * leader board, loaded from SAVE_SCOREBOARD, type-casted into a nested ArrayList
     * Each sub ArrayList denotes a "tuple" of two elements, with the score being the first and
     * user name being the second.
     */
    public ArrayList<ArrayList> leaderBoard;

    /**
     * Private constructor for singleton.
     */
    private SlidingTilesScoreBoardFileHandler() {
    }

    /**
     * The getter for Singleton File Saver.
     *
     * @return the file saver as needed
     */
    static SlidingTilesScoreBoardFileHandler getInstance() {
        if (fileSaver == null) {
            fileSaver = new SlidingTilesScoreBoardFileHandler();
        }
        return fileSaver;
    }

    /**
     * Load the scoreboard from SAVE_SCOREBOARD.
     */
    public void loadFromScoreFile(Context context) {
        try {
            InputStream inputStream = context.openFileInput(SAVE_SCOREBOARD);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                leaderBoard = (ArrayList<ArrayList>) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        } finally {
            if (leaderBoard == null) {
                leaderBoard = new ArrayList<>();
            }
        }
    }

    /**
     * Save the scoreboard to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToScoreFile(Context context, String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    context.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(leaderBoard);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
