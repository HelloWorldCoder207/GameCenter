package fall2018.csc2017.game_centre;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Timer;
import java.util.TimerTask;

import static org.junit.Assert.*;

public class GameTimerTest {
    private GameTimer gameTimer;

    @Before
    public void setUp() throws Exception {
        gameTimer = new GameTimer();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testPauseAction() throws InterruptedException {
        assertEquals(0, gameTimer.getTotalTime());
        gameTimer.resumeAction();
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                assertTrue(gameTimer.getTotalTime() > 0);
            }
        };
        timer.schedule(timerTask, 1000);
        gameTimer.pauseAction();

    }
}