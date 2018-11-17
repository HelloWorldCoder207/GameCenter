package fall2018.csc2017.game_centre.sudoku;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.game_centre.R;

public class SudokuGameActivity extends AppCompatActivity implements Observer {

    /**
     * Size of the grid.
     */
    private final int GRID_SIZE = 9;

    /**
     * Grid view for the Sudoku cells.
     */
    private SudokuGridView gridView;

    /**
     * Buttons to display.
     */
    private ArrayList<Button> cellButtons;

    /**
     * Grid cell dimensions.
     */
    private int cellWidth;
    private int cellHeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createCellButtons(this);
        setContentView(R.layout.activity_sudoku_game);
    }

    /**
     * Create grid buttons.
     * @param context where button is displayed
     */
    private void createCellButtons(Context context) {
        // TODO
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

    }
}
