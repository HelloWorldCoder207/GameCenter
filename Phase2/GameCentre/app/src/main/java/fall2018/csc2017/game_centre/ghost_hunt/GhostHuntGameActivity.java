package fall2018.csc2017.game_centre.ghost_hunt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.game_centre.R;

/**
 * View
 *
 * Activity for Ghost Hunt game.
 */
public class GhostHuntGameActivity extends AppCompatActivity implements Observer {

    /**
     * Number of rows in the board.
     */
    private int rowNum;

    /**
     * Number of columns in the board.
     */
    private int colNum;

    /**
     * Handler for motion events.
     */
    private GameController gameController;

    /**
     * Views of the tiles in the grid.
     */
    private ArrayList<ImageView> tileViews;

    /**
     * Action when activity is created.
     * @param savedInstanceState previous saved state bundle
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extra = getIntent().getExtras();
        if (extra == null) {
            gameController = new GameController(this, null);
        } else {
            gameController = (GameController) extra.getSerializable(GameState.INTENT_NAME);
        }
        assert gameController != null;
        gameController.addObserver(this);
        setContentView(R.layout.activity_ghost_game);
        addDirectionButtonListener();
        createTileViews(this);
        setUpGridView();
        updateTileViews();
    }

    /**
     * Dispatch onResume() to fragments.
     */
    @Override
    protected void onResume() {
        super.onResume();
        this.gameController.getState().getTimer().resumeAction();
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        this.gameController.getState().getTimer().pauseAction();
    }

    /**
     * Set up the grid view for game.
     */
    private void setUpGridView() {
        GameState boardManager = gameController.getState();
        rowNum = boardManager.getBoard().getNumRow();
        colNum = boardManager.getBoard().getNumCol();
        GridView gridView = findViewById(R.id.GridView);
        gridView.setNumColumns(colNum);
        int tileWidth = gridView.getMeasuredWidth() / colNum;
        int tileHeight = gridView.getMeasuredHeight() / rowNum;
        gridView.setAdapter(new GridViewAdapter(tileViews, tileWidth, tileHeight));
    }

    /**
     * Create the image views for displaying the tiles.
     * @param context context where views display
     */
    private void createTileViews(Context context) {
        Board board = gameController.getState().getBoard();
        tileViews = new ArrayList<>();
        for (int row = 0; row != rowNum; row++) {
            for (int col = 0; col != colNum; col++) {
                ImageView tmp = new ImageView(context);
                tmp.setBackgroundResource(board.getTileBackground(row, col));
                this.tileViews.add(tmp);
            }
        }
    }

    /**
     * Update backgrounds of the image views.
     */
    private void updateTileViews() {
        Board board = gameController.getState().getBoard();
        int nextPos = 0;
        for (ImageView v : tileViews) {
            int row = nextPos / rowNum;
            int col = nextPos % colNum;
            v.setBackgroundResource(board.getTileBackground(row, col));
            nextPos++;
        }
    }

    /**
     * Activate direction control buttons.
     */
    private void addDirectionButtonListener() {
        ImageButton up = findViewById(R.id.UpButton);
        ImageButton down = findViewById(R.id.DownButton);
        ImageButton left = findViewById(R.id.LeftButton);
        ImageButton right = findViewById(R.id.RightButton);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameController.processEvent(Direction.UP);
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameController.processEvent(Direction.DOWN);
            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameController.processEvent(Direction.LEFT);
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameController.processEvent(Direction.RIGHT);
            }
        });
    }

    /**
     * Update the activity view when observing object changed.
     *
     * @param o   the observable object
     * @param arg an argument passed to the observer
     */
    @Override
    public void update(Observable o, Object arg) {
        if (arg == GameController.BOARD_CHANGE) {
            updateTileViews();

        } else if (arg == GameController.GAME_OVER) {
            switchToScoreboard();
        }
    }

    /**
     * Switch to scoreboard.
     */
    private void switchToScoreboard() {
        Intent i = new Intent(this, GhostHuntScoreboardActivity.class);
        // TODO: final scores put extra
        int time = gameController.getState().getTimer().convertToSeconds();
        int move = gameController.getState().getMoveCount();
        startActivity(i);
    }
}
