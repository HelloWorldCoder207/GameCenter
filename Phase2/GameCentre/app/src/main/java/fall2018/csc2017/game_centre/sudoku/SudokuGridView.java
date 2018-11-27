package fall2018.csc2017.game_centre.sudoku;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * SudokuBoard view for sudoku number grid.
 */
public class SudokuGridView extends GridView {
    SudokuGameController gameController;
    private GestureDetector gDetector;

    public SudokuGridView(Context context) {
        super(context);
        init(context);
    }

    public SudokuGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SudokuGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP) // API 21
    public SudokuGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    /**
     * Process the context with Grid view.
     * @param context app context
     */
    private void init(final Context context) {
        // TODO: touch event I really don't know if this will work or not
        gameController = new SudokuGameController();
        gDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapConfirmed(MotionEvent event) {
                int position = SudokuGridView.this.pointToPosition
                        (Math.round(event.getX()), Math.round(event.getY()));

                gameController.processTapMovement(context, position);
                return true;
            }

            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }

        });
    }

    public SudokuGameController getGameController() {
        return gameController;
    }
}
