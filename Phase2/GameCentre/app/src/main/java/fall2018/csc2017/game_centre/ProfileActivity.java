package fall2018.csc2017.game_centre;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
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
     * Request code for user picking image from gallery.
     */
    private static final int GET_FROM_GALLARY = 100;
    /**
     * Tag for logging.
     */
    private static final String LOG_TAG ="ProfileActivity";
    /**
     * The image to change.
     */
    private ImageView imageView;
    /**
     * Images of games
     */
    int[] IMAGES = {R.drawable.slide_icon, R.drawable.ghost_hunt_icon, R.drawable.sudoku_icon};
    /**
     * Names of games
     */
    String[] GAME_NAMES = {"Sliding Tiles", "Ghost Hunt", "Sudoku"};
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
        setCurrentPassword();
        addResetButtonListener();
        ImageView imageView = findViewById(R.id.profile_image);

    }

    /**
     * Display current password
     */
    private void setCurrentPassword() {
        TextView currentPassword = findViewById(R.id.tvCurrentPassword);
        currentPassword.setText(currentUser.getPassword());
        addResetButtonListener();
        addChangePFPbuttonListener();
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
                if (newPassword.isEmpty() || newPassword.equals(currentUser.getPassword())) {
                    makeToastText("Invalid Password");
                } else {
                    currentUser.resetPassword(newPassword);
                    userFileHandler.saveToFile(ProfileActivity.this, UserFileHandler.FILE_NAME);
                    setCurrentPassword();
                    makeToastText("Password Reset Successful");
                }
            }
        });
    }

    /**
     * Activate ChangePFP button.
     */
    private void addChangePFPbuttonListener(){
        Button change = findViewById(R.id.ChangePFP);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallary();
            }
        });
    }
    private void openGallary(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, GET_FROM_GALLARY);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GET_FROM_GALLARY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bit = null;
            try {
                bit = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                imageView.setImageBitmap(bit);
            } catch (IOException e) {
                Toast.makeText(this, "Cannot read image", Toast.LENGTH_SHORT).show();
                Log.e(LOG_TAG, "Cannot read image: " + e.toString());
            }
        }

    }

    /**
     * Make text using Toast.
     *
     * @param msg message to display
     */
    private void makeToastText(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Set Player Name TextView to the current currentUser's name
     */
    public void setPlayerName() {
        TextView playerName = findViewById(R.id.tvPlayerName);
        playerName.setText(currentUser.getUsername());
    }

    /**
     * Set ListView to current user's high scores
     */
    public void setListView() {
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
