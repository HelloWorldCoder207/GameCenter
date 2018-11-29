package fall2018.csc2017.game_centre.slidingtiles;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;
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

    /**
     * @param  array an array of integers
     * @return a list of tiles that are ordered as array.
     */
    private List<Tile> createTileList(int[] array) {
        List<Tile> tiles = new ArrayList<>();
        for (int i : array) {
            tiles.add(new Tile(i));
        }
        return tiles;
    }

    @Before
    public void setUp() {
        testController= new GameController();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testProcessTapMovementOneMove() {
        List<Tile> tiles = makeOrderedTiles(3);
        Board board = new Board(tiles, 3);
        boardManager = new BoardManager(1, board);
        testController.setBoardManager(boardManager);
        assertEquals(9, boardManager.getBoard().getTile(2, 2).getId());
        assertEquals(6, boardManager.getBoard().getTile(1, 2).getId());
        testController.processTapMovement(context, 5);
        assertEquals(6, boardManager.getBoard().getTile(2, 2).getId());
        assertEquals(9, boardManager.getBoard().getTile(1, 2).getId());
    }

    @Test
    public void testProcessTapMovementPuzzleSolved() {
        Board board = new Board(createTileList(new int[]{0, 1, 2, 3, 4, 5, 6, 8, 7}), 3);
        boardManager = new BoardManager(2, board);
        testController.setBoardManager(boardManager);
        testController.processTapMovement(context, 8);
        assertTrue(testController.puzzleSolved());
    }

    @Test
    public void testProcessTapMovementInvalidTap() {
        Board board = new Board(createTileList(new int[]{0, 1, 2, 3, 4, 5, 6, 8, 7}), 3);
        boardManager = new BoardManager(2, board);
        testController.setBoardManager(boardManager);
        testController.processTapMovement(context, 0);
        assertEquals(board.getTile(2, 2).getId(), 8);
        assertFalse(testController.puzzleSolved());
    }

    @Test
    public void undo() {
        List<Tile> tiles = makeOrderedTiles(3);
        Board board = new Board(tiles, 3);
        boardManager = new BoardManager(1, board);
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