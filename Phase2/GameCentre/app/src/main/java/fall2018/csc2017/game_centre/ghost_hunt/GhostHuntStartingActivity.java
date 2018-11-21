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
     * Controller of the game.
     */
    private GameController gameController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost_starting);
        if (this.gameController == null) {
            this.gameController = new GameController(this);
        } else {
            this.gameController.setContext(this);
        }
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
                gameController.saveGame();
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
     *
     * @param msg the message to display
     */
    private void makeToastText(String msg) {
        Toast.makeText(GhostHuntStartingActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Switch to the SlidingTilesGameActivity view to play the game.
     */
    private void switchToGame() {
        Intent i = new Intent(this, GhostHuntGameActivity.class);
        i.putExtra(GameController.INTENT_NAME, gameController);
        startActivity(i);
    }

    /**
     * Switch to the Scoreboard of Sliding Tiles.
     */
    private void switchToScoreboard() {
        Intent i = new Intent(this, GhostHuntScoreboardActivity.class);
        startActivity(i);
    }
}
