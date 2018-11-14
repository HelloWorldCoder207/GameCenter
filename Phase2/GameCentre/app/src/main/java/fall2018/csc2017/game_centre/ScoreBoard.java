package fall2018.csc2017.game_centre;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * An abstract super class ScoreBoard.
 * Entails activities needed to be performed by scoreboards of different games.
 */
public abstract class ScoreBoard {
    /**
     * Abstract method calculates the score using an ArrayList of parameters
     */
    public abstract int calculateScore(ArrayList<Integer> array);
    /**
     * Abstract method update user classes of their score
     */
    public abstract void update(Integer score, HashMap<String, User> users);
}
