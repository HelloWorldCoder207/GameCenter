package fall2018.csc2017.game_centre.ghost_hunt;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class GhostHuntGridView extends GridView {

    /**
     * Board manager for the view.
     */
    private BoardManager boardManager;

    public GhostHuntGridView(Context context) {
        super(context);
    }

    public GhostHuntGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GhostHuntGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GhostHuntGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    // TODO
}
