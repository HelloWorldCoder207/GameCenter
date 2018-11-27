package fall2018.csc2017.game_centre.slidingtiles;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import fall2018.csc2017.game_centre.ScoreBoard;
import fall2018.csc2017.game_centre.User;
import fall2018.csc2017.game_centre.UserFileHandler;
import fall2018.csc2017.game_centre.slidingtiles.SlidingTilesScoreBoard;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.*;

public class SlidingTilesScoreBoardTest {


    private ArrayList<Integer> testList1 = new ArrayList<>();
    private ArrayList<Integer> testList2 = new ArrayList<>();
    private UserFileHandler userFileHandler;
    private Map<String, User> users;
    ScoreBoard scoreBoard;
    @Before
    public void setUp() {
        testList1.add(0,1);
        testList1.add(0,1);
        testList2.add(0,500);
        testList2.add(0,10);

        userFileHandler = UserFileHandler.getInstance();
        users = userFileHandler.getUsers();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCalculateScore() {
        SlidingTilesScoreBoard scoreBoard= new SlidingTilesScoreBoard();
        assertEquals(6250,scoreBoard.calculateScore(testList1) );
        assertEquals(154,scoreBoard.calculateScore(testList2) );
    }

    @Test
    public void update() {
    }

    @Test
    public void formatUsers() {
    }
}