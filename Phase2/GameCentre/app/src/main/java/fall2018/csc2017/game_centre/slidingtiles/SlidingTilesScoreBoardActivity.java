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
import java.util.Map;

import fall2018.csc2017.game_centre.CurrentStatus;
import fall2018.csc2017.game_centre.R;
import fall2018.csc2017.game_centre.User;
import fall2018.csc2017.game_centre.UserFileHandler;

/**
 * Score Board Activity
 */
public class SlidingTilesScoreBoardActivity extends AppCompatActivity implements Serializable {

    /**
     * The file handler for user file io.
     */
    private UserFileHandler userFileHandler = UserFileHandler.getInstance();
    
    /**
     * The file handler for score file io.
     */
    private SlidingTilesScoreBoardFileHandler scoreFileHandler = SlidingTilesScoreBoardFileHandler.getInstance();

    /**
     * ScoreBoard class for game.
     */
    private static SlidingTilesScoreBoard scoreBoard = new SlidingTilesScoreBoard();

    /**
     * Users, loaded from SAVE_ALL_USERS type-casted into a HashMap
     */
    private Map<String, User> users = userFileHandler.getUsers();

//    /**
//     * leader board, loaded from SAVE_SCOREBOARD, type-casted into a nested ArrayList
//     * Each sub ArrayList denotes a "tuple" of two elements, with the score being the first and
//     * user name being the second.
//     */
//    public static ArrayList<ArrayList> leaderBoard;

    /**
     * on create method
     * @param savedInstanceState from superclass.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        scoreFileHandler.loadFromScoreFile(this);
        if ((getIntent().getExtras()) != null) {
            int move = (int) getIntent().getExtras().get("move");
            int time = (int) getIntent().getExtras().get("totalTime");
            ArrayList<Integer> updateParam =
                    new ArrayList<>(Arrays.asList(move, time));
            update(updateParam);
        }
        scoreBoard.formatUsers(users, scoreFileHandler.leaderBoard); // sorts user information and prepares them for display
        scoreFileHandler.saveToScoreFile(this, scoreFileHandler.SAVE_SCOREBOARD);
        addTopFivePlayersTextView();
    }

//    /**
//     * Load the users from SAVE_ALL_USERS.
//     */
//    private void loadFromUserFile() {
//        try {
//            InputStream inputStream = this.openFileInput(SAVE_ALL_USERS);
//            if (inputStream != null) {
//                ObjectInputStream input = new ObjectInputStream(inputStream);
//                users = (HashMap<String, User>) input.readObject();
//                inputStream.close();
//            }
//        } catch (FileNotFoundException e) {
//            Log.e("login activity", "File not found: " + e.toString());
//        } catch (IOException e) {
//            Log.e("login activity", "Can not read file: " + e.toString());
//        } catch (ClassNotFoundException e) {
//            Log.e("login activity", "File contained unexpected data type: " + e.toString());
//        }
//    }

//    /**
//     * Load the scoreboard from SAVE_SCOREBOARD.
//     */
//    private void loadFromScoreFile() {
//        try {
//            InputStream inputStream = this.openFileInput(SAVE_SCOREBOARD);
//            if (inputStream != null) {
//                ObjectInputStream input = new ObjectInputStream(inputStream);
//                scoreFileHandler.leaderBoard = (ArrayList<ArrayList>) input.readObject();
//                inputStream.close();
//            }
//        } catch (FileNotFoundException e) {
//            Log.e("login activity", "File not found: " + e.toString());
//        } catch (IOException e) {
//            Log.e("login activity", "Can not read file: " + e.toString());
//        } catch (ClassNotFoundException e) {
//            Log.e("login activity", "File contained unexpected data type: " + e.toString());
//        } finally {
//            if (scoreFileHandler.leaderBoard == null) {
//                scoreFileHandler.leaderBoard = new ArrayList<>();
//            }
//        }
//    }

//    /**
//     * Save the scoreboard to fileName.
//     *
//     * @param fileName the name of the file
//     */
//    public void saveToScoreFile(String fileName) {
//        try {
//            ObjectOutputStream outputStream = new ObjectOutputStream(
//                    this.openFileOutput(fileName, MODE_PRIVATE));
//            outputStream.writeObject(scoreFileHandler.leaderBoard);
//            outputStream.close();
//        } catch (IOException e) {
//            Log.e("Exception", "File write failed: " + e.toString());
//        }
//    }

//    /**
//     * Save the scoreboard to fileName.
//     *
//     * @param fileName the name of the file
//     */
//    public void saveToUsersFile(String fileName) {
//        try {
//            ObjectOutputStream outputStream = new ObjectOutputStream(
//                    this.openFileOutput(fileName, MODE_PRIVATE));
//            outputStream.writeObject(users);
//            outputStream.close();
//        } catch (IOException e) {
//            Log.e("Exception", "File write failed: " + e.toString());
//        }
//    }
    /**
     * Generate appropriate text for TextView Display
     */
    private String generateText(int index){
        String tvDisplay = String.format(Locale.CANADA, "%s : %s points",
                scoreFileHandler.leaderBoard.get(0).get(1), scoreFileHandler.leaderBoard.get(0).get(0));
        return tvDisplay;
    }

    /**
     * Add TextView for top five players
     */
    private void addTopFivePlayersTextView() {
        ArrayList<String> displayText = new ArrayList<>();
        for (int i = 0; i < scoreFileHandler.leaderBoard.size(); i++){
            displayText.add(generateText(i));
        }
        TextView tvFirst = findViewById(R.id.first);
        tvFirst.setText(displayText.get(0));
        TextView tvSecond = findViewById(R.id.second);
        String secondDisplay = (scoreFileHandler.leaderBoard.size() > 1)? generateText(1) : "No data recorded.";
//        if (scoreFileHandler.leaderBoard.size() > 1) {
//            secondDisplay = String.format(Locale.CANADA, "%s : %d points",
//                    scoreFileHandler.leaderBoard.get(1).get(1), scoreFileHandler.leaderBoard.get(1).get(0));
//        }
        tvSecond.setText(secondDisplay);
        TextView tvThird = findViewById(R.id.third);
        String thirdDisplay = (scoreFileHandler.leaderBoard.size() > 2)? generateText(2) : "No data recorded.";
//        if (scoreFileHandler.leaderBoard.size() > 2) {
//            thirdDisplay = String.format(Locale.CANADA, "%s : %d points",
//                    scoreFileHandler.leaderBoard.get(2).get(1), scoreFileHandler.leaderBoard.get(2).get(0));
//        }
        tvThird.setText(thirdDisplay);
        TextView tvFourth = findViewById(R.id.fourth);
        String fourthDisplay = (scoreFileHandler.leaderBoard.size() > 3)? generateText(3) : "No data recorded.";;
//        if (scoreFileHandler.leaderBoard.size() > 3) {
//            fourthDisplay = String.format(Locale.CANADA, "%s : %d points",
//                    scoreFileHandler.leaderBoard.get(3).get(1), scoreFileHandler.leaderBoard.get(3).get(0));
//        }
        tvFourth.setText(fourthDisplay);
        TextView tvFifth = findViewById(R.id.fifth);
        String fifthDisplay = (scoreFileHandler.leaderBoard.size() > 4)? generateText(4) : "No data recorded.";;
//        if (scoreFileHandler.leaderBoard.size() > 4) {
//            fifthDisplay = String.format(Locale.CANADA, "%s : %d points",
//                    scoreFileHandler.leaderBoard.get(4).get(1), scoreFileHandler.leaderBoard.get(4).get(0));
//        }
        tvFifth.setText(fifthDisplay);
        TextView tvCurrentPlayer = findViewById(R.id.player_rank);
        String currentDisplay = generateCurrentText();
        tvCurrentPlayer.setText(currentDisplay);
    }

    /**
     * Generate the current player's rank and points
     * @return the current player's rank and points
     */
    private String generateCurrentText(){
        String currentDisplay = ("No data recorded, yet.");
        User currentUser = CurrentStatus.getCurrentUser();
        for (int index = 0; index < scoreFileHandler.leaderBoard.size(); index++) {
            if (scoreFileHandler.leaderBoard.get(index).get(1).equals(currentUser.getUsername())) {
                currentDisplay = String.format(Locale.CANADA, "%s : %d points, rank %d",
                        scoreFileHandler.leaderBoard.get(index).get(1),
                        scoreFileHandler.leaderBoard.get(index).get(0), index + 1);
                break;
            }
        }
        return currentDisplay;
    }

    /**
     * Update the scoreFileHandler.leaderBoard.
     * @param scoreParameter the array list of information needed for score.
     */
    public void update(ArrayList<Integer> scoreParameter) {
        int newScore = scoreBoard.calculateScore(scoreParameter);
        scoreBoard.update(newScore, users);
        scoreFileHandler.saveToScoreFile(this, scoreFileHandler.SAVE_SCOREBOARD);
        scoreFileHandler.loadFromScoreFile(this);
        scoreBoard.formatUsers(users, scoreFileHandler.leaderBoard);
        addTopFivePlayersTextView();
    }
}
