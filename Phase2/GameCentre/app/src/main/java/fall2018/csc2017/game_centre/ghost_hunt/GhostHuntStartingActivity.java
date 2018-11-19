package fall2018.csc2017.game_centre.ghost_hunt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Map;

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
     * Activate back button.
     */
    private void addBackButtonListener() {
        Button back = findViewById(R.id.BackToGameCentre);
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
//                loadFromFile(SAVE_FILENAME);
//                saveToFile(TEMP_SAVE_FILENAME);
                if (boardManager == null) {
                    makeToastText("No previous saved game");
                } else {
                    makeToastText("Loaded Game");
                    switchToGame();
                }
            }
        });
    }

    /**
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                loadFromFile(TEMP_SAVE_FILENAME);
//                saveToFile(SAVE_FILENAME);
                makeToastText("Game Saved");
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
        // TODO: go to game centre
        startActivity(i);
    }

    /**
     * Switch to the SlidingTilesGameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, GhostHuntGameActivity.class);
        // TODO: go to game
        startActivity(tmp);
    }

    /**
     * Switch to the Scoreboard of Sliding Tiles.
     */
    private void switchToScoreboard() {
        Intent tmp = new Intent(this, GhostHuntScoreboardActivity.class);
        // TODO: go to scoreboard
        startActivity(tmp);
    }
}
