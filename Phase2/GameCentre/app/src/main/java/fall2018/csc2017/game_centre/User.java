package fall2018.csc2017.game_centre;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
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
    private Map<Game, File> userfiles = new HashMap<>();
    {
        for (Game game: Game.values()){
            File myfile = new File("username_game.ser");
            userfiles.put(game, myfile);
        }
    }

    /**
     * The constructor for a new user
     * @param username the username, should be unique
     * @param password the password of this user
     */
    User(String username, String password){
        this.username = username;
        this.password = password;
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
}
