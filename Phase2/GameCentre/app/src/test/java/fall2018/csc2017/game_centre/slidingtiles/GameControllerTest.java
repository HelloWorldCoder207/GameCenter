package fall2018.csc2017.game_centre.slidingtiles;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import fall2018.csc2017.game_centre.slidingtiles.GameController;
import fall2018.csc2017.game_centre.slidingtiles.Tile;
import fall2018.csc2017.game_centre.slidingtiles.Board;
import fall2018.csc2017.game_centre.slidingtiles.BoardManager;

import static org.junit.Assert.*;

public class GameControllerTest {

    private GameController testController;
    private BoardManager boardManager;
    private Context context;


    /**
     * Make a set of tiles that are in order.
     *
     * @param size size of the grid
     * @return a set of tiles that are in order
     */
    private List<Tile> makeOrderedTiles(int size) {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = size * size;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        return tiles;
    }

    @Before
    public void setUp() {
        testController= new GameController();
        List<Tile> tiles = makeOrderedTiles(3);
        Board board = new Board(tiles, 3);
        boardManager = new BoardManager(1, board);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void SetBoardManager() {

    }

    @Test
    public void processTapMovement() {
        testController.setBoardManager(boardManager);
        assertEquals(9, boardManager.getBoard().getTile(2, 2).getId());
        assertEquals(6, boardManager.getBoard().getTile(1, 2).getId());
        testController.processTapMovement(context, 5);
        assertEquals(6, boardManager.getBoard().getTile(2, 2).getId());
        assertEquals(9, boardManager.getBoard().getTile(1, 2).getId());
    }

    @Test
    public void undo() {
        testController.setBoardManager(boardManager);
        assertEquals(9, boardManager.getBoard().getTile(2, 2).getId());
        assertEquals(6, boardManager.getBoard().getTile(1, 2).getId());
        testController.processTapMovement(context, 5);
        assertEquals(6, boardManager.getBoard().getTile(2, 2).getId());
        assertEquals(9, boardManager.getBoard().getTile(1, 2).getId());
        testController.undo();
        assertEquals(9, boardManager.getBoard().getTile(2, 2).getId());
        assertEquals(6, boardManager.getBoard().getTile(1, 2).getId());

    }
}