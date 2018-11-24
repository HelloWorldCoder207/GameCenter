package fall2018.csc2017.game_centre.slidingtiles;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

import fall2018.csc2017.game_centre.CurrentStatus;
import fall2018.csc2017.game_centre.R;
import fall2018.csc2017.game_centre.User;

/**
 * Score Board Activity
 */
public class SlidingTilesScoreBoardActivity extends AppCompatActivity implements Serializable {

    /**
     * The save files.
     */
    public static final String SAVE_SCOREBOARD = "save_score_board.ser";
    public static final String SAVE_ALL_USERS = User.FILE_NAME;

    /**
     * ScoreBoard class for game.
     */
    private static SlidingTilesScoreBoard scoreBoard = new SlidingTilesScoreBoard();

    /**
     * Users, loaded from SAVE_ALL_USERS type-casted into a HashMap
     */
    private HashMap<String, User> users;

    /**
     * leader board, loaded from SAVE_SCOREBOARD, type-casted into a nested ArrayList
     * Each sub ArrayList denotes a "tuple" of two elements, with the score being the first and
     * user name being the second.
     */
    public static ArrayList<ArrayList> leaderBoard;

    /**
     * on create method
     * @param savedInstanceState from superclass.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        loadFromUserFile();
        loadFromScoreFile();
        if (!((getIntent().getExtras()) == null)) {
            int move = (int) getIntent().getExtras().get("move");
            int time = (int) getIntent().getExtras().get("totalTime");
            ArrayList<Integer> updateParam =
                    new ArrayList<>(Arrays.asList(move, time));
            update(updateParam);
        }
        scoreBoard.formatUsers(users, leaderBoard); // sorts user information and prepares them for display
        saveToScoreFile(SlidingTilesScoreBoardActivity.SAVE_SCOREBOARD);
        addTopFivePlayersTextView();
    }

    /**
     * Load the users from SAVE_ALL_USERS.
     */
    private void loadFromUserFile() {
        try {
            InputStream inputStream = this.openFileInput(SAVE_ALL_USERS);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                users = (HashMap<String, User>) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Load the scoreboard from SAVE_SCOREBOARD.
     */
    private void loadFromScoreFile() {
        try {
            InputStream inputStream = this.openFileInput(SAVE_SCOREBOARD);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                leaderBoard = (ArrayList<ArrayList>) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        } finally {
            if (leaderBoard == null) {
                leaderBoard = new ArrayList<>();
            }
        }
    }

    /**
     * Save the scoreboard to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToScoreFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(leaderBoard);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Save the scoreboard to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToUsersFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(users);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Add TextView for top five players
     */
    private void addTopFivePlayersTextView() {
        TextView tvFirst = findViewById(R.id.first);
        String firstDisplay = String.format(Locale.CANADA, "%s : %d points",
                leaderBoard.get(0).get(1), leaderBoard.get(0).get(0));
        tvFirst.setText(firstDisplay);
        TextView tvSecond = findViewById(R.id.second);
        String secondDisplay = "No data recorded.";
        if (leaderBoard.size() > 1) {
            secondDisplay = String.format(Locale.CANADA, "%s : %d points",
                    leaderBoard.get(1).get(1), leaderBoard.get(1).get(0));
        }
        tvSecond.setText(secondDisplay);
        TextView tvThird = findViewById(R.id.third);
        String thirdDisplay = "No data recorded.";
        if (leaderBoard.size() > 2) {
            thirdDisplay = String.format(Locale.CANADA, "%s : %d points",
                    leaderBoard.get(2).get(1), leaderBoard.get(2).get(0));
        }
        tvThird.setText(thirdDisplay);
        TextView tvFourth = findViewById(R.id.fourth);
        String fourthDisplay = "No data recorded.";
        if (leaderBoard.size() > 3) {
            fourthDisplay = String.format(Locale.CANADA, "%s : %d points",
                    leaderBoard.get(3).get(1), leaderBoard.get(3).get(0));
        }
        tvFourth.setText(fourthDisplay);
        TextView tvFifth = findViewById(R.id.fifth);
        String fifthDisplay = "No data recorded.";
        if (leaderBoard.size() > 4) {
            fifthDisplay = String.format(Locale.CANADA, "%s : %d points",
                    leaderBoard.get(4).get(1), leaderBoard.get(4).get(0));
        }
        tvFifth.setText(fifthDisplay);
        TextView tvCurrentPlayer = findViewById(R.id.player_rank);
        String currentDisplay = ("No data recorded, yet.");
        User currentUser = CurrentStatus.getCurrentUser();
        for (int index = 0; index < leaderBoard.size(); index++) {
            if (leaderBoard.get(index).get(1).equals(currentUser.getUsername())) {
                currentDisplay = String.format(Locale.CANADA, "%s : %d points, rank %d",
                        leaderBoard.get(index).get(1),
                        leaderBoard.get(index).get(0), index + 1);
                break;
            }
        }
        tvCurrentPlayer.setText(currentDisplay);
    }

    /**
     * Update the leaderBoard.
     * @param scoreParameter the array list of information needed for score.
     */
    public void update(ArrayList<Integer> scoreParameter) {
        int newScore = scoreBoard.calculateScore(scoreParameter);
        scoreBoard.update(newScore, users);
        saveToUsersFile(SAVE_ALL_USERS);
        saveToScoreFile(SAVE_SCOREBOARD);
        loadFromUserFile();
        loadFromScoreFile();
        scoreBoard.formatUsers(users, leaderBoard);
        addTopFivePlayersTextView();
    }
}
