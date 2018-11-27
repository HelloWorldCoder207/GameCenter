package fall2018.csc2017.game_centre.sudoku;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.game_centre.R;

/**
 * View
 *
 * Game activity for sudoku.
 */
public class SudokuGameActivity extends AppCompatActivity implements Observer {

    /**
     * Size of the grid.
     */
    private final int GRID_SIZE = 9;

    private SudokuGameState gameState;

    private SudokuFileHandler fileHandler = SudokuFileHandler.getInstance();

    /**
     * SudokuBoard view for the SudokuBoard cells.
     */
    private SudokuGridView gridView;

    /**
     * Sudoku game controller.
     */
    private SudokuGameController gameController;

    /**
     * Buttons to display.
     */
    private ArrayList<Button> cellButtons;

    /**
     * SudokuBoard cell dimensions.
     */
    private int cellWidth;
    private int cellHeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameState = fileHandler.getGameState();
        createCellButtons(this);
        setContentView(R.layout.activity_sudoku_game);
        //TODO instantiate and observe GameController.
        //TODO observe GameState.
        //TODO set up grid view, and gameController.
//        setUpGridView();

        // Add button listeners
        for (int i = 1; i < 10; i++) {
            addAnswerButtonListener(i);
        }
    }

//    /**
//     * Helper function for setting up the grid view in activity.
//     */
//    private void setUpGridView() {
//        gridView = findViewById(R.id.sudokuGridView);
//        gridView.setNumColumns(boardLength);
//        gridView.setBoardManager(boardManager);
//
//        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
//                new ViewTreeObserver.OnGlobalLayoutListener() {
//                    @Override
//                    public void onGlobalLayout() {
//                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
//                                this);
//                        int displayWidth = gridView.getMeasuredWidth();
//                        int displayHeight = gridView.getMeasuredHeight();
//                        columnWidth = displayWidth / boardLength;
//                        columnHeight = displayHeight / boardLength;
//                        imageProcessor = new SlidingTilesImageProcessor(boardLength,
//                                columnWidth, columnHeight);
//                        display();
//                    }
//                });
//    }

    private void addAnswerButtonListener(final int buttonNum) {
        String resource = "AnswerButton" + Integer.toString(buttonNum);
        int buttonId = this.getResources().getIdentifier(resource, "id", getPackageName());
        Button answerButton = findViewById(buttonId);
        answerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameController.answerButtonClicked(SudokuGameActivity.this, buttonNum);
            }
        });
    }

    /**
     * Create grid buttons. Also sets up the background ids for every cell.
     * @param context where button is displayed
     */
    private void createCellButtons(Context context) {
        SudokuBoard board = gameState.getBoard();
        cellButtons = new ArrayList<>();
        for (int row = 0; row != board.getSideLen(); row++) {
            for (int col = 0; col != board.getSideLen(); col++) {
                Button tmp = new Button(context);

                Cell cell = board.getCell(row, col);
                setBackgroundIdFromR(cell);

                tmp.setBackgroundResource(board.getCell(row, col).getBackground());
                this.cellButtons.add(tmp);
            }
        }
    }

    /**
     * use getResources method to find cell background id from R. Store it into the cell.
     * @param cell    the cell that is generating background id
     */
    private void setBackgroundIdFromR(Cell cell){
        int cellValue = cell.getValue();
        // Set number background
        String resource = "sudoku_" + Integer.toString(cellValue) + "a";
        cell.setNumberBackground(this.getResources().getIdentifier(
                resource, "drawable", getPackageName()));

        // Set colored background
        resource = "sudoku_" + Integer.toString(cellValue) + "a_coloured";
        cell.setColoredBackground(this.getResources().getIdentifier(
                resource, "drawable", getPackageName()));
    }

//    /**
//     * Set up the background image for each button based on the master list
//     * of positions, and then call the adapter to set the view.
//     */
//    public void display() {
//        updateTileButtons();
//        gridView.setAdapter(new SlidingTilesAdapter(tileButtons, columnWidth, columnHeight));
//        setDisplayMove();
//    }

    /**
     * Display message in sliding tiles starting activity.
     * @param msg the message to display
     */
    private void makeToastText(String msg) {
        Toast.makeText(SudokuGameActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof SudokuGameState){
            Toast.makeText(this,
                    "You Were Wrong Four Times YOU SUCKA", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
