package fall2018.csc2017.game_centre.slidingtiles;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardManagerTest {

    private BoardManager boardManager;

    @Before
    public void setUp() {
        boardManager = new BoardManager(3, 3);
    }

    @Test
    public void testSaveMove() {
        boardManager.saveMove(1, 1);
        int[] result = boardManager.getLastMove();
        assertEquals(1, result[0]);
        assertEquals(1, result[1]);
    }
}