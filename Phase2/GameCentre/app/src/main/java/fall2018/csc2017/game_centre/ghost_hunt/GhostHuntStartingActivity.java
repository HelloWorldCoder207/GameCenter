package fall2018.csc2017.game_centre.ghost_hunt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import fall2018.csc2017.game_centre.CurrentStatus;
import fall2018.csc2017.game_centre.GameCentreActivity;
import fall2018.csc2017.game_centre.R;

/**
 * Starting activity for the ghost hunt game.
 */
public class GhostHuntStartingActivity extends AppCompatActivity {

    /**
     * Tag for logging.
     */
    private static final String LOG_TAG = "GhostHuntStartingActivity";

    /**
     * Mapping from user name to corresponding user's board manager.
     */
    private Map<String, BoardManager> boardManagerMap;

    /**
     * Current player's board
     */
    private BoardManager boardManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost_starting);
        addBackButtonListener();
        addStartButtonListener();
        addLoadButtonListener();
        addSaveButtonListener();
        addScoreboardListener();
    }

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        switchToGameCentre();
    }

    /**
     * Activate back button.
     */
    private void addBackButtonListener() {
        ImageButton back = findViewById(R.id.BackToGameCentre);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToGameCentre();
            }
        });
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: start game
                switchToGame();
            }
        });
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: load game
                loadFromFile(GhostHuntGameActivity.SAVE_FILENAME);
                if (boardManager == null) {
                    makeToastText("No previous saved game");
                } else {
                    makeToastText("Loaded game");
                    switchToGame();
                }
            }
        });
    }

    /**
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        final Button saveButton = findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: save game
                loadFromFile(GhostHuntGameActivity.TEMP_SAVE_FILENAME);
                saveToFile(GhostHuntGameActivity.SAVE_FILENAME);
                makeToastText("Game saved");
            }
        });
    }

    /**
     * Activate Scoreboard Button.
     */
    private void addScoreboardListener() {
        Button scoreboard = findViewById(R.id.Scoreboard);
        scoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToScoreboard();
            }
        });
    }

    /**
     * Display message in sliding tiles starting activity.
     * @param msg the message to display
     */
    private void makeToastText(String msg) {
        Toast.makeText(GhostHuntStartingActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Switch to GameCentreActivity.
     */
    private void switchToGameCentre() {
        Intent i = new Intent(this, GameCentreActivity.class);
        saveToFile(GhostHuntGameActivity.TEMP_SAVE_FILENAME);
        startActivity(i);
    }

    /**
     * Switch to the SlidingTilesGameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, GhostHuntGameActivity.class);
        saveToFile(GhostHuntGameActivity.TEMP_SAVE_FILENAME);
        startActivity(tmp);
    }

    /**
     * Switch to the Scoreboard of Sliding Tiles.
     */
    private void switchToScoreboard() {
        Intent tmp = new Intent(this, GhostHuntScoreboardActivity.class);
        saveToFile(GhostHuntGameActivity.TEMP_SAVE_FILENAME);
        startActivity(tmp);
    }

    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName) {
        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                boardManagerMap = (HashMap<String, BoardManager>) input.readObject();
                boardManager = boardManagerMap.get(CurrentStatus.getCurrentUser().getUsername());
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
     */
    private void saveToFile(String fileName) {
        try {
            if (boardManagerMap == null) {
                boardManagerMap = new HashMap<>();
            }
            boardManagerMap.put(CurrentStatus.getCurrentUser().getUsername(), boardManager);
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(boardManagerMap);
            outputStream.close();
        } catch (IOException e) {
            Log.e(LOG_TAG, "File write failed: " + e.toString());
        }
    }
}
