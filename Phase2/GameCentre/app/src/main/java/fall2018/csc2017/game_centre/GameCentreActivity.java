package fall2018.csc2017.game_centre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import fall2018.csc2017.game_centre.slidingtiles.SlidingTilesStartingActivity;

/**
 * Game selection centre activity.
 */
public class GameCentreActivity extends AppCompatActivity {

    /**
     * Currently available games.
     */
    private final Map<Game, Class> GAMES = new HashMap<>();
    {
        GAMES.put(Game.SlidingTiles, SlidingTilesStartingActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_centre);
        addBackButtonListener();
        addSlidingTilesButtonListener();
        Toast.makeText(this,
                String.format("Welcome back, %s", CurrentStatus.getCurrentUser().getUsername()),
                Toast.LENGTH_LONG).show();
    }

    /**
     * Activate back button to login.
     */
    private void addBackButtonListener() {
        ImageButton back = findViewById(R.id.Backtologin);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToLogin();
            }
        });
    }

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        switchToLogin();
    }

    /**
     * Activate SlidingTiles button.
     */
    private void addSlidingTilesButtonListener() {
        Button slidingTiles = findViewById(R.id.SlidingTiles);
        slidingTiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToGame(Game.SlidingTiles);
            }
        });
    }

    /**
     * Switch to login activity.
     */
    private void switchToLogin() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    /**
     * Switch to corresponding game activity based on input index.
     * @param game name of the game in Game enum
     */
    private void switchToGame(Game game) {
        Intent tmp = new Intent(this, GAMES.get(game));
        startActivity(tmp);
    }
}
