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


class GameController extends Observable {

    /**
     * Current board manager.
     */
    private BoardManager boardManager = null;

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
     * @param boardManager the boardManager to be set
     */
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
                fileSaver.saveToFile(context, SlidingTilesFileSaver.SAVE_FILENAME);
            }

            if (boardManager.puzzleSolved()) {
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
                setChanged(); notifyObservers(boardManager.getMoveCounter());
            }
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }
}
