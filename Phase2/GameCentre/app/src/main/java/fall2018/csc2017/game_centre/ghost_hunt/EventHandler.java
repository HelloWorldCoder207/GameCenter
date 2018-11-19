package fall2018.csc2017.game_centre.ghost_hunt;

import android.content.Context;

import java.util.Observable;

class EventHandler extends Observable {

    /**
     * Board manager.
     */
    private BoardManager boardManager;

    /**
     * Set the board manager
     * @param boardManager board manager
     */
    void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    void processSingleTapEvent(Context context, int position) {
        // TODO
    }
}
