package fall2018.csc2017.game_centre.ghost_hunt;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

public class GameControllerTest {

    @Mock
    private Board mockBoard;

    @Mock
    private GameState mockState;

    @Mock
    private Context mockContext;

    @Mock
    private FileHandler mockHandler;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private GameController controller;

    @Before
    public void setUp() throws Exception {
        controller = new GameController(mockContext, mockHandler, mockState);
    }

    /**
     * Mocking a controller with mocking context, file handler and state.
     */
    private void setMockController() {
//        controller = new GameController(mockContext, mockHandler, mockState);
    }

    private void setSimpleStateController() {
        GameState state = new GameState(makeSimpleBoard());
        controller = new GameController(mockContext, mockHandler, state);
    }

    /**
     * Make a simple ghost hunt board.
     * p = player
     * g = ghost
     * E = exit
     * +---+---+---+
     * |         g |
     * +   +   +   +
     * | E         |
     * +   +   +   +
     * |     p     |
     * +---+---+---+
     * @return above board
     */
    private Board makeSimpleBoard() {
        FileHandler handler = FileHandler.getInstance();
        String[] map_csv = {
                "0110,0111,0011",
                "1110,1111,1011",
                "1100,1101,1001"
        };
        int row = 0;
        Tile[][] grid = new Tile[3][3];
        for (String line : map_csv) {
            grid[row] = handler.readTile(line);
            row++;
        }
        grid[1][0].setExit();
        Player p = new Player(2, 1);
        Ghost g = new Ghost(0, 2);
        return new Board(1, grid, p, g);
    }

    @After
    public void tearDown() {
        controller = null;
    }

    @Test
    public void setContext() {
        setMockController();
        Context context = mock(Context.class);
        controller.setContext(context);
        assertEquals(context, controller.getContext());
    }

    @Test
    public void getContext() {
        setMockController();
        Context context = mock(Context.class);
        controller.setContext(context);
        assertEquals(context, controller.getContext());
    }

    @Test
    public void setState() {
        setMockController();
        GameState state = mock(GameState.class);
        controller.setState(state);
        assertEquals(state, controller.getState());
    }

    @Test
    public void getState() {
        setMockController();
        GameState state = mock(GameState.class);
        controller.setState(state);
        assertEquals(state, controller.getState());
    }

    @Test
    public void startGame() {
        setMockController();
        when(mockHandler.getBoard()).thenReturn(mockBoard);
        controller.startGame();
        verify(mockHandler).loadMap(mockContext, 1);
        assertEquals(controller.getState().getBoard(), mockHandler.getBoard());
        verify(mockHandler).setState(controller.getState());
    }

    @Test
    public void loadGameHasExistingGame() {
        setMockController();
        when(mockHandler.getState()).thenReturn(mockState);
        assertTrue(controller.loadGame());
        verify(mockHandler).loadFromFile(mockContext);
        assertEquals(controller.getState(), mockState);
    }

    @Test
    public void loadGameNoExistingGame() {
        setMockController();
        when(mockHandler.getState()).thenReturn(null);
        assertFalse(controller.loadGame());
        verify(mockHandler).loadFromFile(mockContext);
        assertNull(controller.getState());
    }

    @Test
    public void saveGame() {
        setMockController();
        controller.saveGame();
        // once in constructor, once in saveGame()
        verify(mockHandler, times(2)).setState(controller.getState());
        verify(mockHandler).saveToFile(mockContext);
    }

    @Test
    public void undo() {
        setSimpleStateController();
        controller.processEvent(Direction.LEFT);
        GameState state = controller.getState();
        assertEquals(2, state.getBoard().getPlayer().getRow());
    }

    @Test
    public void restart() {
    }

    @Test
    public void processEvent() {
    }
}