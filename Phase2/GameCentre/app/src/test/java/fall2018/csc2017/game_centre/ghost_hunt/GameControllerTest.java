package fall2018.csc2017.game_centre.ghost_hunt;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fall2018.csc2017.game_centre.Game;

import static org.junit.Assert.*;

public class GameControllerTest {

    private GameController controller;

    @Before
    public void setUp() throws Exception {
        controller = new GameController(null, makeSimpleState());
    }

    private GameState makeSimpleState() {
        GameState state = new GameState(makeSimpleBoard());
        return null;
    }

    private Board makeSimpleBoard() {
        Tile[][] grid = new Tile[][]{};
//        Board board = new Board();
        return null;
    }

    @After
    public void tearDown() throws Exception {
        controller = null;
    }

    @Test
    public void setContext() {
    }

    @Test
    public void getState() {
    }

    @Test
    public void setState() {
    }

    @Test
    public void startGame() {
    }

    @Test
    public void loadGame() {
    }

    @Test
    public void saveGame() {
    }

    @Test
    public void undo() {
    }

    @Test
    public void restart() {
    }

    @Test
    public void processEvent() {
    }
}