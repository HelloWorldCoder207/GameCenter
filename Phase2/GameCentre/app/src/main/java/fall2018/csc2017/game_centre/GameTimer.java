package fall2018.csc2017.game_centre;

public class GameTimer {
    /**
     * The starting time for each interval.
     */
    private long startingTime;

    /**
     * the total time.
     */
    private long totalTime;

    /**
     * Constructor.
     */
    public GameTimer() {
    }

    /**
     * Called when OnPause.
     */
    public void pauseAction() {
        totalTime = totalTime + System.currentTimeMillis() - startingTime;
    }

    /**
     * Called when OnResume.
     */
    public void resumeAction() {
        startingTime = System.currentTimeMillis();
    }

    /**
     * Convert totalTime into seconds.
     * @return an Integer in total number of seconds.
     */
    public Integer convertToSeconds() {
        return (int)((totalTime+ System.currentTimeMillis() - startingTime)/1000);
    }
}
