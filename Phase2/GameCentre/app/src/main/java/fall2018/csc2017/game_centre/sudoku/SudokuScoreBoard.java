package fall2018.csc2017.game_centre.sudoku;

import java.util.ArrayList;
import java.util.Map;

import fall2018.csc2017.game_centre.CurrentStatus;
import fall2018.csc2017.game_centre.Game;
import fall2018.csc2017.game_centre.ScoreBoard;
import fall2018.csc2017.game_centre.User;

public class SudokuScoreBoard extends ScoreBoard {

    /**
     * calculate score of game
     *
     * @param array an ArrayList of length 3, index 0 is number of hints remaining,
     *              index 1 is the number of wrongs, and index 2 is time in seconds.
     * @return the score of game
     */
    public int calculateScore(ArrayList<Integer> array) {
        int hintLeft = array.get(0);
        int wrong = array.get(1);
        int time_in_sec = array.get(2);
        int score = 10000 - (100 * wrong) - time_in_sec + (500 * hintLeft);
        return Math.max(1, score); // for user experience there will not be a negative score
    }

    /**
     * Updates currentUser's high score, if needed.
     *
     * @param score the new score from end of game
     * @param users the hash map of users.
     */
    public void update(Integer score, Map<String, User> users) {
        User currentUser = CurrentStatus.getCurrentUser();
        if (score > currentUser.getScore(Game.Sudoku)) {
            for (User user : users.values()){
                if (currentUser.getUsername().equals(user.getUsername())){
                    user.setScore(Game.Sudoku, score);
                }
            }
        }
    }

    /**
     * Formats each user's information into an ArrayList:
     * [int score, String username]
     * @param users       the user hash map
     * @param leaderBoard the array list of leader board.
     */
    void formatUsers(Map<String, User> users, ArrayList<ArrayList> leaderBoard) {
        ArrayList<ArrayList> scoreKeyArray = new ArrayList<>();
        Game slidingTile = Game.Sudoku;
        for (User entry : users.values()) {
            ArrayList<Object> scoreKeyPair = new ArrayList<>();
            scoreKeyPair.add(entry.getScore(slidingTile));
            scoreKeyPair.add(entry.getUsername());
            scoreKeyArray.add(scoreKeyPair);
        }
        customSort(scoreKeyArray, leaderBoard);
    }

    /**
     * sorts this nested list
     * by the first element in each sub list.
     *
     * @param nestedList a nested ArrayList
     * @param leaderBoard the array list of leader board.
     */
    private void customSort(ArrayList<ArrayList> nestedList, ArrayList<ArrayList> leaderBoard) {
        int size = nestedList.size();
        for (ArrayList pair : nestedList) {
            if (leaderBoard.contains(pair)) {
                continue;
            }
            if (leaderBoard.isEmpty()) {
                leaderBoard.add(pair);
            } else if ((int) pair.get(0) < (int) leaderBoard.get(leaderBoard.size() - 1).get(0)) {
                leaderBoard.add(pair);
            } else {
                for (int i = 0; i < size; i++) {
                    if ((int) pair.get(0) >= (int) leaderBoard.get(i).get(0)) {
                        leaderBoard.add(i, pair);
                        break;
                    }
                }
            }
        }
    }
}
