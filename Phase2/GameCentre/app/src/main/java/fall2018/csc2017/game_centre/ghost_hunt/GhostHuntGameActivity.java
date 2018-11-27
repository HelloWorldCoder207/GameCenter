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
import java.util.Queue;

import fall2018.csc2017.game_centre.R;

/**
 * View
 *
 * Activity for Ghost Hunt game.
 */
public class GhostHuntGameActivity extends AppCompatActivity implements Observer {

    /**
     * Intent key for back pressed.
     */
    static final String QUIT_STATUS = "quit_status";

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
     * Tile width.
     */
    private int tileWidth;

    /**
     * Tile height.
     */
    private int tileHeight;

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
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        quitGame();
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
        tileWidth = gridView.getMeasuredWidth() / colNum;
        tileHeight = gridView.getMeasuredHeight() / rowNum;
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
     * Update tile display components and adapter.
     */
    private void updateDisplay() {
        updateTileViews();
        GridView gridView = findViewById(R.id.GridView);
        gridView.setAdapter(new GridViewAdapter(tileViews, tileWidth, tileHeight));
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
            updateDisplay();
        } else if (arg == GameController.GAME_OVER) {
            quitGame();
        } else if (arg == GameController.GAME_FINISH) {
            finishGame();
        }
    }

    /**
     * Interrupt game process.
     */
    private void quitGame() {
        Intent i = new Intent();
        i.putExtra(QUIT_STATUS, GhostHuntStartingActivity.GAME_QUIT);
        setResult(RESULT_OK, i);
        finish();
    }

    /**
     * Finish game process.
     */
    private void finishGame() {
        Intent i = new Intent();
        i.putExtra(QUIT_STATUS, GhostHuntStartingActivity.GAME_FINISH);
        int move = gameController.getState().getMoveCount();
        int time = gameController.getState().getTimer().getTotalTime();
        i.putExtra("time", time);
        i.putExtra("move", move);
        setResult(RESULT_OK, i);
        finish();
    }
}
