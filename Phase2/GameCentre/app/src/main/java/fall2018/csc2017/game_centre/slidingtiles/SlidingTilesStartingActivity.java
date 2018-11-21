package fall2018.csc2017.game_centre.slidingtiles;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
 * The initial activity for the sliding puzzle tile game.
 */
public class SlidingTilesStartingActivity extends AppCompatActivity {

    /**
     * The file saver for file io.
     */
    private SlidingTilesFileSaver fileSaver;

    /**
     * Current player's boardManager.
     */
    private BoardManager boardManager;

    /**
     * On create method
     * @param savedInstanceState param need from superclass.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fileSaver = SlidingTilesFileSaver.getInstance();
        fileSaver.loadFromFile(this, SlidingTilesFileSaver.SAVE_FILENAME);
        boardManager = fileSaver.getBoardManager();
        setContentView(R.layout.activity_slidingtiles_starting);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(SlidingTilesStartingActivity.this);
                View inflater = getLayoutInflater().inflate(R.layout.dialog_slidingtiles_starting, null);
                builder.setView(inflater);
                AlertDialog dialog = builder.create();
                setUpDialog(inflater, dialog);
                dialog.show();
            }
        });
    }

    /**
     * Set up game starting info dialog.
     * @param view the view holding dialog
     * @param dialog the dialog to set up
     */
    private void setUpDialog(final View view, final AlertDialog dialog) {
        Button start = view.findViewById(R.id.DialogStartButton);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    EditText dimension = view.findViewById(R.id.Dimension);
                    EditText undo = view.findViewById(R.id.Undo);
                    int dim = Integer.parseInt(dimension.getText().toString());
                    int u = Integer.parseInt(undo.getText().toString());
                    if (dim >= 3 && dim <= 5 && u > 0) {
                        boardManager = new BoardManager(u, dim);
                        fileSaver.setBoardManager(boardManager);
                        switchToGame();
                        dialog.dismiss();
                    } else {
                        makeToastText("Invalid input");
                    }
                } catch (NumberFormatException e) {
                    makeToastText("Fill in empty field");
                }
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
                fileSaver.loadFromFile(SlidingTilesStartingActivity.this,
                        SlidingTilesFileSaver.SAVE_FILENAME);

//                fileSaver.saveToFile(SlidingTilesStartingActivity.this,
//                        SlidingTilesFileSaver.TEMP_SAVE_FILENAME);
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
//                fileSaver.loadFromFile(SlidingTilesStartingActivity.this,
//                        SlidingTilesFileSaver.TEMP_SAVE_FILENAME);

                fileSaver.saveToFile(SlidingTilesStartingActivity.this,
                        SlidingTilesFileSaver.SAVE_FILENAME);
                makeToastText("Game Saved");
            }
            });
    }

    /**
     * Display message in sliding tiles starting activity.
     * @param msg the message to display
     */
    private void makeToastText(String msg) {
        Toast.makeText(SlidingTilesStartingActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Activate Scoreboard Button.
     */
    private void addScoreboardListener() {
        Button scoreboard = findViewById(R.id.Scoreboard);
        scoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToScoreboard();
            }
        });
    }

    /**
     * Switch to GameCentreActivity.
     */
    private void switchToGameCentre() {
        Intent i = new Intent(this, GameCentreActivity.class);
//        fileSaver.saveToFile(this, SlidingTilesFileSaver.TEMP_SAVE_FILENAME);
        startActivity(i);
    }

    /**
     * Switch to the SlidingTilesGameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, SlidingTilesGameActivity.class);
//        fileSaver.saveToFile(this, SlidingTilesFileSaver.TEMP_SAVE_FILENAME);
        startActivity(tmp);
    }

    /**
     * Switch to the Scoreboard of Sliding Tiles.
     */
    private void switchToScoreboard() {
        Intent tmp = new Intent(this, SlidingTilesScoreBoardActivity.class);
//        fileSaver.saveToFile(this, SlidingTilesFileSaver.TEMP_SAVE_FILENAME);
        startActivity(tmp);
    }

//    /**
//     * Load the board manager from fileName.
//     *
//     * @param fileName the name of the file
//     */
//    private void loadFromFile(String fileName) {
//        try {
//            InputStream inputStream = this.openFileInput(fileName);
//            if (inputStream != null) {
//                ObjectInputStream input = new ObjectInputStream(inputStream);
//                boardManagers = (HashMap<String, BoardManager>) input.readObject();
//                boardManager = boardManagers.get(CurrentStatus.getCurrentUser().getUsername());
//                inputStream.close();
//            }
//        } catch (FileNotFoundException e) {
//            Log.e(LOG_TAG, "File not found: " + e.toString());
//        } catch (IOException e) {
//            Log.e(LOG_TAG, "Can not read file: " + e.toString());
//        } catch (ClassNotFoundException e) {
//            Log.e(LOG_TAG, "File contained unexpected data type: " + e.toString());
//        } catch (NullPointerException e) {
//            Log.e(LOG_TAG, "Calling on null reference: " + e.toString());
//        }
//    }

//    /**
//     * Save the board manager to fileName.
//     *
//     * @param fileName the name of the file
//     */
//    public void saveToFile(String fileName) {
//        try {
//            if (boardManagers == null) {
//                boardManagers = new HashMap<>();
//            }
//            boardManagers.put(CurrentStatus.getCurrentUser().getUsername(), boardManager);
//            ObjectOutputStream outputStream = new ObjectOutputStream(
//                    this.openFileOutput(fileName, MODE_PRIVATE));
//            outputStream.writeObject(boardManagers);
//            outputStream.close();
//        } catch (IOException e) {
//            Log.e(LOG_TAG, "File write failed: " + e.toString());
//        }
//    }

}
