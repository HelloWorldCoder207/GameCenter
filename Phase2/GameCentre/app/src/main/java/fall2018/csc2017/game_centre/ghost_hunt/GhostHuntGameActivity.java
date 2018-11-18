package fall2018.csc2017.game_centre.ghost_hunt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.game_centre.R;

/**
 * Activity for Ghost Hunt game.
 */
public class GhostHuntGameActivity extends AppCompatActivity implements Observer {

    /**
     * Number of rows in the board.
     */
    private int boardRow;

    /**
     * Number of columns in the board.
     */
    private int boardCol;

    /**
     * Board manager.
     */
    private BoardManager boardManager;

    /**
     * Grid view for the map.
     */
    private GhostHuntGridView gridView;

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
//        loadFromFile();
        boardRow = boardManager.getBoard().getNumRow();
        boardCol = boardManager.getBoard().getNumCol();
//        createTileViews(this);
        setContentView(R.layout.activity_ghost_game);

        gridView = findViewById(R.id.GridView);
        gridView.setNumColumns(boardCol);
        gridView.setBoardManager(boardManager);
        boardManager.getBoard().addObserver(this);
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                gridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int displayWidth = gridView.getMeasuredWidth();
                int displayHeight = gridView.getMeasuredHeight();
                tileWidth = displayWidth / boardCol;
                tileHeight = displayHeight / boardRow;
                display();
            }
        });
    }

    private void display() {
        gridView.setAdapter(new GhostHuntAdapter(tileViews, tileWidth, tileHeight));
    }

    /**
     * Update the activity view when observing object changed.
     *
     * @param o   the observable object
     * @param arg an argument passed to the observer
     */
    @Override
    public void update(Observable o, Object arg) {

    }
}
