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

    private GameState makeSimpleState() {
        GameState state = new GameState(makeSimpleBoard());
        return null;
    }

    private Board makeSimpleBoard() {
        String[] map = {};
        Tile[][] grid = {
                {}
        };
//        Board board = new Board();
        return null;
    }

    @After
    public void tearDown() throws Exception {
        controller = null;
    }

    @Test
    public void setContext() {
        Context context = mock(Context.class);
        controller.setContext(context);
        assertEquals(context, controller.getContext());
    }

    @Test
    public void getContext() {
        Context context = mock(Context.class);
        controller.setContext(context);
        assertEquals(context, controller.getContext());
    }

    @Test
    public void setState() {
        GameState state = mock(GameState.class);
        controller.setState(state);
        assertEquals(state, controller.getState());
    }

    @Test
    public void getState() {
        GameState state = mock(GameState.class);
        controller.setState(state);
        assertEquals(state, controller.getState());
    }

    @Test
    public void startGame() {
        when(mockHandler.getBoard()).thenReturn(mockBoard);
        controller.startGame();
        verify(mockHandler).loadMap(mockContext, 1);
        assertEquals(controller.getState().getBoard(), mockHandler.getBoard());
        verify(mockHandler).setState(controller.getState());
    }

    @Test
    public void loadGameHasExistingGame() {
        when(mockHandler.getState()).thenReturn(mockState);
        assertTrue(controller.loadGame());
        verify(mockHandler).loadGame(mockContext);
        assertEquals(controller.getState(), mockState);
    }

    @Test
    public void loadGameNoExistingGame() {
        when(mockHandler.getState()).thenReturn(null);
        assertFalse(controller.loadGame());
        verify(mockHandler).loadGame(mockContext);
        assertNull(controller.getState());
    }

    @Test
    public void saveGame() {
        controller.saveGame();
        verify(mockHandler).setState(controller.getState());
        verify(mockHandler).saveGame(mockContext);
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