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
 * View
 *
 * Starting activity for the ghost hunt game.
 */
public class GhostHuntStartingActivity extends AppCompatActivity {

    /**
     * Request code for to game activity.
     */
    private static final int TO_GAME = 1;

    static final int GAME_QUIT = 10;

    static final int GAME_FINISH = 11;

    /**
     * Controller of the game.
     */
    private GameController gameController;

    /**
     * Action when activity is created.
     * @param savedInstanceState previous state bundle
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost_starting);
        gameController = new GameController(this, null);
        addStartButtonListener();
        addLoadButtonListener();
        addSaveButtonListener();
        addScoreboardListener();
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameController.startGame();
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
                boolean success = gameController.loadGame();
                if (success) {
                    makeToastText("Game loaded");
                    switchToGame();
                } else {
                    makeToastText("No previous saved game");
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
                if (gameController.getState() == null) {
                    makeToastText("No played game");
                } else {
                    gameController.saveGame();
                    makeToastText("Game saved");
                }
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
     * Display message in starting activity.
     * @param msg the message to display
     */
    private void makeToastText(String msg) {
        Toast.makeText(GhostHuntStartingActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Switch to the game view to play the game.
     */
    private void switchToGame() {
        Intent i = new Intent(this, GhostHuntGameActivity.class);
        i.putExtra(GameState.INTENT_NAME, gameController.getState());
        startActivityForResult(i, TO_GAME);
    }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode activity request
     * @param resultCode result status
     * @param data state data passed in
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TO_GAME && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            assert extras != null;
            int status = (int) extras.get(GhostHuntGameActivity.QUIT_STATUS);
            if (status == GAME_QUIT) {
                gameController.setState((GameState) extras.get(GameState.INTENT_NAME));
            } else if (status == GAME_FINISH) {
                gameController.setState((GameState) extras.get(GameState.INTENT_NAME));
                switchToScoreboard(extras.getInt("totalTime"), extras.getInt("move"));
            }
        }
    }

    /**
     * Switch to the Scoreboard.
     */
    private void switchToScoreboard() {
        Intent i = new Intent(this, GhostHuntScoreboardActivity.class);
        startActivity(i);
    }

    /**
     * Switch to scoreboard with final game status.
     * @param time total time
     * @param move moves used
     */
    private void switchToScoreboard(int time, int move) {
        Intent i = new Intent(this, GhostHuntScoreboardActivity.class);
        i.putExtra("totalTime", time);
        i.putExtra("move", move);
        startActivity(i);
    }
}
