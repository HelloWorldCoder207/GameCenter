package fall2018.csc2017.game_centre.ghost_hunt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.game_centre.CurrentStatus;
import fall2018.csc2017.game_centre.R;

/**
 * Activity for Ghost Hunt game.
 */
public class GhostHuntGameActivity extends AppCompatActivity implements Observer {

    /**
     * Tag for logging.
     */
    private static final String LOG_TAG = "GhostHuntGameActivity";

    /**
     * Temporary saving file.
     */
    protected static final String TEMP_SAVE_FILENAME = "ghost_hunt_temp.ser";

    /**
     * Saving file.
     */
    protected static final String SAVE_FILENAME = "ghost_hunt_save.ser";

    /**
     * Number of rows in the board.
     */
    private int boardRow;

    /**
     * Number of columns in the board.
     */
    private int boardCol;

    /**
     * Board managers loaded from file.
     */
    private Map<String, BoardManager> boardManagerMap;

    /**
     * Board manager.
     */
    private BoardManager boardManager;

    /**
     * Grid view for the map.
     */
    private GridView gridView;

    /**
     * Handler for motion events.
     */
    private GameController gameController;

    /**
     * Dimension of the tile in the grid.
     */
    private int tileWidth, tileHeight;

    /**
     * Views of the tiles in the grid.
     */
    private ArrayList<ImageView> tileViews;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            gameController = (GameController) getIntent().getExtras().getSerializable(GameController.INTENT_NAME);
        } catch (NullPointerException e) {
            Log.e(LOG_TAG, "Get null object from previous activity: " + e.toString());
        }

        // ======== to be revised ========
        loadFromFile(TEMP_SAVE_FILENAME);
        boardRow = boardManager.getBoard().getNumRow();
        boardCol = boardManager.getBoard().getNumCol();
        createTileViews(this);
        setContentView(R.layout.activity_ghost_game);
        addDirectionButtonListener();
        boardManager.getBoard().addObserver(this);
        gridView = findViewById(R.id.GridView);
        gridView.setNumColumns(boardCol);
//        gridView.setBoardManager(boardManager);
//        gridView.getEventHandler().addObserver(this);
//        gridView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                gridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                int displayWidth = gridView.getMeasuredWidth();
//                int displayHeight = gridView.getMeasuredHeight();
//                tileWidth = displayWidth / boardCol;
//                tileHeight = displayHeight / boardRow;
//                gridView.setAdapter(new GridViewAdapter(tileViews, tileWidth, tileHeight));
//                display();
//            }
//        });
        int tileWidth = gridView.getMeasuredWidth() / boardCol;
        int tileHeight = gridView.getMeasuredHeight() / boardRow;
        gridView.setAdapter(new GridViewAdapter(tileViews, tileWidth, tileHeight));
        display();
    }

    /**
     * Dispatch onResume() to fragments.
     */
    @Override
    protected void onResume() {
        super.onResume();
        // TODO: resume the activity
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        // TODO: pause the activity
    }

    /**
     * Create the image views for displaying the tiles.
     * @param context context where views display
     */
    private void createTileViews(Context context) {
        Board board = boardManager.getBoard();
        tileViews = new ArrayList<>();
        for (int row = 0; row != boardRow; row++) {
            for (int col = 0; col != boardCol; col++) {
                ImageView tmp = new ImageView(context);
                tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                this.tileViews.add(tmp);
            }
        }
    }

    /**
     * Update backgrounds of the image views.
     */
    private void updateTileViews() {
        Board board = boardManager.getBoard();
        int nextPos = 0;
        for (ImageView v : tileViews) {
            int row = nextPos / boardRow;
            int col = nextPos % boardCol;
            int index = board.getTile(row, col).getID();
            // TODO: update avatar view
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
                gameController.processEvent(DirectionIntent.UP);
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameController.processEvent(DirectionIntent.DOWN);
            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameController.processEvent(DirectionIntent.LEFT);
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameController.processEvent(DirectionIntent.RIGHT);
            }
        });
    }

    /**
     * Save the board manager to filename.
     *
     * @param filename name of the file
     */
    private void saveToFile(String filename) {
        try {
            if (boardManagerMap == null) {
                boardManagerMap = new HashMap<>();
            }
            boardManagerMap.put(CurrentStatus.getCurrentUser().getUsername(), boardManager);
            ObjectOutputStream outputStream = new ObjectOutputStream(this.openFileOutput(filename, MODE_PRIVATE));
            outputStream.writeObject(boardManagerMap);
            outputStream.close();
        } catch (IOException e) {
            Log.e(LOG_TAG, "File write failed: " + e.toString());
        }
    }

    /**
     * Load the board manager from filename.
     *
     * @param filename name of the file
     */
    private void loadFromFile(String filename) {
        try {
            InputStream inputStream = this.openFileInput(filename);
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
     * Display all contents.
     */
    private void display() {
        updateTileViews();
    }

    /**
     * Update the activity view when observing object changed.
     *
     * @param o   the observable object
     * @param arg an argument passed to the observer
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Board) {
            display();
        } else if (o instanceof GameController) {
            switchToScoreboard();
        }
    }

    /**
     * Switch to scoreboard.
     */
    private void switchToScoreboard() {
        Intent i = new Intent(this, GhostHuntScoreboardActivity.class);
        // TODO: final scores put extra
        startActivity(i);
    }
}
