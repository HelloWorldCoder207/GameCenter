package fall2018.csc2017.game_centre;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {

    /**
     * Username and password of the user.
     */
    private String username, password;

    /**
     * Mapping from game to highest score. Initialized for all games with 0;
     */
    private Map<Game, Integer> scores = new HashMap<>();
    {
        for (Game game : Game.values()) {
            scores.put(game, 0);
        }
    }

    /**
     * Mapping from game to filename.
     */
    private Map<Game, String> userFilename = new HashMap<>();

    /**
     * The constructor for a new user
     * @param username the username, should be unique
     * @param password the password of this user
     */
    User(String username, String password){
        this.username = username;
        this.password = password;
        userFilename.put(Game.SlidingTiles, username + "_sliding_tiles_save.ser");
        userFilename.put(Game.GhostHunt, username + "_ghost_hunt_save.ser");
        userFilename.put(Game.Sudoku, username + "_sudoku_save.ser");
    }

    /**
     * Return the highest score for a particular game.
     * @param game the game to find highest score
     * @return highest score by user playing game
     */
    public int getScore(Game game) {
        return this.scores.get(game);
    }

    /**
     * Set new highest score for a game.
     * @param game game to set highest score
     * @param score new highest score
     */
    public void setScore(Game game, Integer score) {
        this.scores.put(game, score);
    }

    /**
     * Reset password into given parameter
     * @param  newPassword the new password to replace the old one
     */
    public void resetPassword(String newPassword) {
        this.password = newPassword;
    }
    /**
     * Update scores for compatibility between versions.
     */
    void updateScores() {
        for (Game game : Game.values()) {
            if (!scores.containsKey(game)) {
                scores.put(game, 0);
            }
        }
    }

    /**
     * Return the username of user.
     * @return username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Return the password of user.
     * @return password
     */
    protected String getPassword() {
        return this.password;
    }

    public String getUserFilename(Game game) {
        return userFilename.get(game);
    }
}
