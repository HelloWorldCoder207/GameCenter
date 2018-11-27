package fall2018.csc2017.game_centre.sudoku;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import fall2018.csc2017.game_centre.GameCentreActivity;
import fall2018.csc2017.game_centre.R;

/**
 * View
 * <p>
 * SudokuBoard starting menu.
 */
public class SudokuStartingActivity extends AppCompatActivity {

    private SudokuGameState gameState;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku_starting);
        addStartButtonListener();
        addLoadButtonListener();
        addScoreboardListener();
    }

    /**
     * Activate start button.
     */
    private void addStartButtonListener() {
        Button start = findViewById(R.id.StartButton);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SudokuStartingActivity.this);
                View inflater = getLayoutInflater().inflate(R.layout.dialog_sudoku_starting, null);
                builder.setView(inflater);
                AlertDialog dialog = builder.create();
                setUpDialog(inflater, dialog);
                dialog.show();
                switchToGame();
            }
        });
    }

    /**
     * Set up game starting info dialog.
     *
     * @param view   the view holding dialog
     * @param dialog the dialog to set up
     */
    private void setUpDialog(final View view, final AlertDialog dialog) {
        Button easy = view.findViewById(R.id.DialogEasyButton);
        Button medium = view.findViewById(R.id.DialogMediumButton);
        Button hard = view.findViewById(R.id.DialogHardButton);
        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameState = new SudokuGameState(20);
//                 fileSaver.setBoardManager(gameState);
                switchToGame();
                dialog.dismiss();
            }
        });
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameState = new SudokuGameState(30);
//                fileSaver.setBoardManager(gameState);
                switchToGame();
                dialog.dismiss();
            }
        });
        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameState = new SudokuGameState(40);
//                fileSaver.setBoardManager(gameState);
                switchToGame();
                dialog.dismiss();
            }
        });
    }

    /**
     * Activate load button.
     */
    private void addLoadButtonListener() {
        Button load = findViewById(R.id.LoadButton);
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
            }
        });
    }


    /**
     * Activate scoreboard button.
     */
    private void addScoreboardListener() {
        Button start = findViewById(R.id.Scoreboard);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
            }
        });
    }

    /**
     * Resume the activity.
     */
    @Override
    protected void onResume() {
        super.onResume();
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
     * Switch to game centre activity.
     */
    private void switchToGameCentre() {
        Intent i = new Intent(this, GameCentreActivity.class);
        startActivity(i);
    }

    private void switchToGame() {
        Intent i = new Intent(this, SudokuGameActivity.class);
        i.putExtra("game state", gameState);
        startActivity(i);
    }
}
