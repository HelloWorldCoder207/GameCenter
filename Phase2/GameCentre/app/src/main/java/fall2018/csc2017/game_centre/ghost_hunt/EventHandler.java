package fall2018.csc2017.game_centre.ghost_hunt;

import android.content.Context;

import java.util.Observable;

class EventHandler extends Observable {

    /**
     * Board manager.
     */
    private BoardManager boardManager;

    /**
     * Constructor taking in a board manager.
     * @param boardManager manager of the game board
     */
    EventHandler(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    void processEvent(DirectionIntent direction) {
        // TODO
    }
}
