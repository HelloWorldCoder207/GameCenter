package fall2018.csc2017.game_centre.slidingtiles;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.game_centre.CurrentStatus;
import fall2018.csc2017.game_centre.GameTimer;
import fall2018.csc2017.game_centre.R;

/**
 * The game activity.
 */
public class SlidingTilesGameActivity extends AppCompatActivity implements Observer {

    /**
     * Tag for logging.
     */
    private static final String LOG_TAG = "SlidingTilesGameActivity";

    /**
     * Request code for user picking image from gallery.
     */
    private static final int PICK_IMAGE = 100;

    /**
     * The file saver for boardManager.
     */
    private SlidingTilesFileSaver fileSaver;

    /**
     * Timer for the game.
     */
    private GameTimer timer;

    /**
     * Number of rows of the board.
     */
    private int boardRow;

    /**
     * Number of columns of the board.
     */
    private int boardCol;

    /**
     * The board manager.
     */
    private BoardManager boardManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * Grid view to display.
     */
    private GestureDetectGridView gridView;

    /**
     * Calculated column height and width based on device size.
     */
    private int columnWidth, columnHeight;

    /**
     * Image parts of the user selected picture.
     */
    private List<BitmapDrawable> customImageTiles = new ArrayList<>();

    /**
     * OnCreate method.
     * @param savedInstanceState inherited needed from activities.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fileSaver = SlidingTilesFileSaver.getInstance();
//        fileSaver.loadFromFile(this, SlidingTilesFileSaver.TEMP_SAVE_FILENAME);
        boardManager = fileSaver.getBoardManager();
        boardRow = boardManager.getBoard().getNumRow();
        boardCol = boardManager.getBoard().getNumCol();
        createTileButtons(this);
        setContentView(R.layout.activity_main);
        addUndoButtonListener();
        addChangeBackgroundButtonListener();

        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(boardCol);
        gridView.setBoardManager(boardManager);
        boardManager.getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();
                        columnWidth = displayWidth / boardCol;
                        columnHeight = displayHeight / boardRow;
                        display();
                    }
                });
        addUndoButtonListener();
        setDisplayUsername();
        gridView.addObserverMController(this);
    }

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
//
//    /**
//     * Load the board manager from fileName.
//     */
//    private void loadFromFile() {
//        try {
//            InputStream inputStream = this.openFileInput(SlidingTilesStartingActivity.TEMP_SAVE_FILENAME);
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

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
//        fileSaver.saveToFile(this, SlidingTilesFileSaver.TEMP_SAVE_FILENAME);
        timer.pauseAction();
    }

    /**
     * OnResume method.
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (timer == null){
            timer = new GameTimer();
        }
        timer.resumeAction();
    }

    /**
     * Activate SlidingTiles undo button.
     */
    private void addUndoButtonListener() {
        Button undo = findViewById(R.id.undo);
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boardManager.undo();
            }
        });
    }

    /**
     * Activate SlidingTiles change background button.
     */
    private void addChangeBackgroundButtonListener() {
        Button changeBackground = findViewById(R.id.changeBackground);
        changeBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
    }

    /**
     * Open gallery for image picking and ask for result in return.
     */
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode code representing the request
     * @param resultCode condition of the result back
     * @param data data passed back
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            try {
                Bitmap image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                int gridWidth = gridView.getMeasuredWidth();
                int gridHeight = gridView.getMeasuredHeight();
                image = Bitmap.createScaledBitmap(image, gridWidth,gridHeight, true);
                trimImage(image);
                updateTileButtons();
            } catch (IOException e) {
                Toast.makeText(this, "Cannot read image", Toast.LENGTH_SHORT).show();
                Log.e(LOG_TAG, "Cannot read image: " + e.toString());
            }
        }
    }

    /**
     * Trim image into pieces according to the difficulty.
     * @param image image to trim
     */
    private void trimImage(Bitmap image) {
        for (int col = 0; col < boardRow; col++) {
            for (int row = 0; row < boardCol; row++) {
                Bitmap tmp = Bitmap.createBitmap(image,
                        row * columnWidth, col * columnHeight, columnWidth, columnHeight);
                BitmapDrawable tile = new BitmapDrawable(tmp);
                customImageTiles.add(tile);
            }
        }
        customImageTiles.remove(customImageTiles.size() - 1);
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        Board board = boardManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != boardRow; row++) {
            for (int col = 0; col != boardCol; col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        Board board = boardManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / boardRow;
            int col = nextPos % boardCol;
            int index = board.getTile(row, col).id;
            if (customImageTiles.size() == 0 || index == board.numTiles()) {
                b.setBackgroundResource(board.getTile(row, col).getBackground());
            } else {
                b.setBackground(customImageTiles.get(index - 1));
            }
            nextPos++;
        }
    }

    /**
     * Update board if board changes, switch to score board if movementController.
     * @param o an observable class
     * @param arg an arg used by update.
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Board) {
            display();
        }
        else if (o instanceof MovementController){
            switchToScoreBoard((Integer) arg);
        }
    }

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new SlidingTilesAdapter(tileButtons, columnWidth, columnHeight));
        setDisplayMove();
    }

    /**
     * Switch to ScoreBoardActivity
     * @param move the number of moves that the ended game had
     */
    private void switchToScoreBoard(Integer move){
        Intent i = new Intent(this, SlidingTilesScoreBoardActivity.class);
        if (boardRow == 3) {
            i.putExtra("move", move * 10 );
        }
        else if (boardRow == 4) {
            i.putExtra("move", move * 5);
        }
        else {
            i.putExtra("move", move);
        }
        Integer time = timer.convertToSeconds();
        i.putExtra("totalTime", time);

        startActivity(i);
    }

    /**
     * set username to text view.
     */
    private void setDisplayUsername() {
        ((TextView)findViewById(R.id.player_textview)).setText(
                CurrentStatus.getCurrentUser().getUsername());
    }

    /**
     * set display move.
     */
    private void setDisplayMove() {
        String result = ((Integer)boardManager.getMoveCounter()).toString();
        ((TextView)findViewById(R.id.move_textview)).setText(result);
    }
}
