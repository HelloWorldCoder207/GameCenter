package fall2018.csc2017.game_centre.ghost_hunt;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.GridView;

class MapGridView extends GridView {

    /**
     * Handler for events.
     */
    private EventHandler eventHandler;

    /**
     * Gesture detector for the grid.
     */
    private GestureDetector gestureDetector;

    MapGridView(Context context) {
        super(context);
        init(context);
    }

    MapGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    MapGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    MapGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    /**
     * Listen for single tap event on the grid.
     * @param context context of the grid view
     */
    private void init(final Context context) {
        this.eventHandler = new EventHandler();
        this.gestureDetector = new GestureDetector(context,
                new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                int position = MapGridView.this.pointToPosition
                        (Math.round(e.getX()), Math.round(e.getY()));
                eventHandler.processSingleTapEvent(context, position);
                return true;
            }
        });
    }

    /**
     * Getter the event handler of the grid view.
     * @return event handler
     */
    EventHandler getEventHandler() {
        return this.eventHandler;
    }

    /**
     * Setter for board manager
     * @param boardManager set the board manager for event handler
     */
    void setBoardManager(BoardManager boardManager) {
        this.eventHandler.setBoardManager(boardManager);
    }
}
