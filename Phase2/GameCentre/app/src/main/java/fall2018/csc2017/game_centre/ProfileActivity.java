package fall2018.csc2017.game_centre;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {

    /**
     * The Current User
     */
    User currentUser = CurrentStatus.getCurrentUser();
    /**
     * User File Handler
     */
    private UserFileHandler userFileHandler = UserFileHandler.getInstance();
    /**
     * Images of games
     */
    int[] IMAGES = {R.drawable.slide_icon, R.drawable.ghost_hunt_icon, R.drawable.sudoku_icon};
    /**
     * Names of games
     */
    String[] GAME_NAMES = {"Sliding Tiles", "Ghost Hunt", "Sudoku" };
    /**
     * Score of games
     */
    int[] GAME_SCORES = {currentUser.getScore(Game.SlidingTiles), currentUser.getScore(Game.GhostHunt),
            currentUser.getScore(Game.Sudoku)};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setPlayerName();
        setListView();
        TextView currentPassword = findViewById(R.id.tvCurrentPassword);
        currentPassword.setText(currentUser.getPassword());
        addResetButtonListener();
    }

    /**
     * Activate Password Reset Button
     */
    private void addResetButtonListener() {
        Button reset = findViewById(R.id.btnreset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etPassword = findViewById(R.id.etNewPassword);
                String newPassword = etPassword.getText().toString();
                if (newPassword.isEmpty()) {
                    makeToastText("Invalid Password");
                } else {
                    currentUser.resetPassword(newPassword);
                    userFileHandler.saveToFile(ProfileActivity.this, UserFileHandler.FILE_NAME);
                    makeToastText("Password Reset Successful");
                }
            }
        });
    }

    /**
     * Make text using Toast.
     * @param msg message to display
     */
    private void makeToastText(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Set Player Name TextView to the current currentUser's name
     */
    public void setPlayerName(){
        TextView playerName = findViewById(R.id.tvPlayerName);
        playerName.setText(currentUser.getUsername());
    }

    /**
     * Set ListView to current user's high scores
     */
    public void setListView(){
        ListView listView = findViewById((R.id.scores));
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
    }

    /**
     * Custom adapter class that converts lists (IMAGES, GAME_NAMES, GAME_SCORES) into
     * ListView display formats
     * Implementation follows:
     * http://ramsandroid4all.blogspot.com/2013/03/custom-listview-with-imageview.html
     */
    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return IMAGES.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.custom_list_layout, null);
            ImageView imageView = view.findViewById(R.id.imageView_image);
            TextView textView_game = view.findViewById(R.id.textView_name);
            TextView textView_score = view.findViewById(R.id.textView_highscore);

            imageView.setImageResource(IMAGES[i]);
            textView_game.setText(GAME_NAMES[i]);
            textView_score.setText(String.format(Locale.CANADA, "Score: %d", GAME_SCORES[i]));
            return view;
        }
    }
}
