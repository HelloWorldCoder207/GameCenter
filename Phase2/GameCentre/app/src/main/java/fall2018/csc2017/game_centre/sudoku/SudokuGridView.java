package fall2018.csc2017.game_centre.sudoku;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Sudoku view for sudoku number grid.
 */
public class SudokuGridView extends GridView {
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

    public SudokuGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    /**
     * Process the context.
     * @param context app context
     */
    private void init(final Context context) {
        // TODO: touch event
    }
}
