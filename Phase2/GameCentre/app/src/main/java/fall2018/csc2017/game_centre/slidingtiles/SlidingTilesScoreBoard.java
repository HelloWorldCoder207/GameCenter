package fall2018.csc2017.game_centre.slidingtiles;

import java.util.ArrayList;

import fall2018.csc2017.game_centre.CurrentStatus;
import fall2018.csc2017.game_centre.ScoreBoard;


public class SlidingTilesScoreBoard extends ScoreBoard {

    /**
     * calculate score of game
     *
     * @param array an ArrayList of length 2, index 0 is moves, index 1 is time in seconds
     * @return the score of game
     */
    public int calculateScore(ArrayList<Integer> array) {
        float moves = array.get(0);
        float time_in_sec = array.get(1);
        double dbl_score = 1 / ((moves * 15.0) + time_in_sec);
        return (int) Math.ceil(dbl_score * 100000);
    }

//    /**
//     * Updates currentUser's high score, if needed.
//     *
//     * @param score the new score from end of game
//     * @param users the hash map of users.
//     */
//    public void update(Integer score, Map<String, User> users) {
//        User currentUser = CurrentStatus.getCurrentUser();
//        if (score > currentUser.getScore(Game.SlidingTiles)) {
//            currentUser.setScore(Game.SlidingTiles, score);
//        }
//    }

//    /**
//     * Formats each user's information into an ArrayList:
//     * [int score, String username]
//     *
//     * @param users       the user hash map
//     * @param leaderBoard the array list of leader board.
//     */
//    void formatUsers(Map<String, User> users, ArrayList<ArrayList> leaderBoard) {
//        ArrayList<ArrayList> scoreKeyArray = new ArrayList<>();
//        Game slidingTile = Game.SlidingTiles;
//        for (User entry : users.values()) {
//            if (entry.getScore(slidingTile) != 0) {
//                // 0 is the default score, implying user has not played the game yet
//                // thus will not appear on leader board
//                ArrayList<Object> scoreKeyPair = new ArrayList<>();
//                scoreKeyPair.add(entry.getScore(slidingTile));
//                scoreKeyPair.add(entry.getUsername());
//                scoreKeyArray.add(scoreKeyPair);
//            }
//        }
//        customSort(scoreKeyArray, leaderBoard);
//    }
//
//    /**
//     * sorts this nested list
//     * by the first element in each sub list.
//     *
//     * @param nestedList  a nested ArrayList
//     * @param leaderBoard the array list of leader board.
//     */
//    private void customSort(ArrayList<ArrayList> nestedList, ArrayList<ArrayList> leaderBoard) {
//        int size = nestedList.size();
//        for (ArrayList pair : nestedList) {
//            if (leaderBoard.contains(pair)) {
//                continue;
//            }
//            if (leaderBoard.isEmpty()) {
//                leaderBoard.add(pair);
//            } else if ((int) pair.get(0) < (int) leaderBoard.get(leaderBoard.size() - 1).get(0)) {
//                leaderBoard.add(pair);
//            } else {
//                for (int i = 0; i < size; i++) {
//                    if ((int) pair.get(0) >= (int) leaderBoard.get(i).get(0)) {
//                        leaderBoard.add(i, pair);
//                        break;
//                    }
//                }
//            }
//        }
//    }
}
