package fall2018.csc2017.game_centre;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * An abstract super class ScoreBoard.
 * Entails activities needed to be performed by scoreboards of different games.
 */
public abstract class ScoreBoard {
    /**
     * Abstract method calculates the score using an ArrayList of parameters
     * @param array The information needed for calculation
     * @return the score
     */
    public abstract int calculateScore(ArrayList<Integer> array);
    /**
     * Abstract method update user classes of their score
     * @param score the score to update
     * @param users the user that score update on
     */
    public abstract void update(Integer score, Map<String, User> users);
}
