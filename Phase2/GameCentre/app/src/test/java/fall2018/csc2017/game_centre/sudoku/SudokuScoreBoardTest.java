package fall2018.csc2017.game_centre.sudoku;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import fall2018.csc2017.game_centre.ScoreBoard;
import fall2018.csc2017.game_centre.User;
import fall2018.csc2017.game_centre.UserFileHandler;

import static org.junit.Assert.*;

public class SudokuScoreBoardTest {
    private ArrayList<Integer> testList1 = new ArrayList<>();
    private ArrayList<Integer> testList2 = new ArrayList<>();
    ScoreBoard scoreBoard;
    private UserFileHandler userFileHandler;
    private Map<String, User> users;


    @Before
    public void setUp() throws Exception {
        testList1.add(0,3);
        testList1.add(1,0);
        testList1.add(2,200);
        testList2.add(0,0);
        testList2.add(1,3);
        testList2.add(2,300);
        userFileHandler = UserFileHandler.getInstance();
        users = userFileHandler.getUsers();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void calculateScore() {
        SudokuScoreBoard scoreBoard = new SudokuScoreBoard();
        assertEquals(11300, scoreBoard.calculateScore(testList1));
        assertEquals(9400, scoreBoard.calculateScore(testList2));
    }

    @Test
    public void update(){
    }

    @Test
    public void formatUsers() {
    }
}