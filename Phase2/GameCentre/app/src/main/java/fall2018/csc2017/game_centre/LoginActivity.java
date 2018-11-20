package fall2018.csc2017.game_centre;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static fall2018.csc2017.game_centre.R.layout.activity_login;


public class LoginActivity extends AppCompatActivity {

    /**
     * Tag for logging.
     */
    private static final String LOG_TAG = "LoginActivity";

    /**
     * Mapping from username to user.
     */
    private Map<String, User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_login);

        if (getIntent().getExtras() == null) {
            loadFromFile(User.FILE_NAME);
        } else {
            this.users = (HashMap<String, User>) getIntent().getExtras().get("UpdatedUsers");
        }

        addLoginButtonListener();
        addSignUpLinkListener();
        addUpdateLinkListener();
    }

    /**
     * Activate login button.
     */
    private void addLoginButtonListener() {
        Button login = findViewById(R.id.buttonlogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etUsername = findViewById(R.id.etUsername);
                EditText etPassword = findViewById(R.id.etPassword);
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                if (users.containsKey(username) && password.equals(users.get(username).getPassword())) {
                    saveToFile(User.FILE_NAME);
                    CurrentStatus.setCurrentUser(users.get(username));
                    Intent i = new Intent(getApplicationContext(), GameCentreActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Activate sign up button.
     */
    private void addSignUpLinkListener() {
        TextView signUp = findViewById(R.id.signuplink);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                i.putExtra("Users", (Serializable) users);
                startActivity(i);
            }
        });
    }

    /**
     * Activate update button as the logo.
     */
    private void addUpdateLinkListener() {
        ImageView logo = findViewById(R.id.LOGO);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (User user : users.values()) {
                    user.updateScores();
                }
            }
        });
    }

    /**
     * Load users from filename.
     * @param fileName file name
     */
    private void loadFromFile(String fileName) {
        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                users = (HashMap<String, User>) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e(LOG_TAG, "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e(LOG_TAG, "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e(LOG_TAG, "File contained unexpected data type: " + e.toString());
        } finally {
            if (users == null) {
                users = new HashMap<>();
            }
        }
    }

    /**
     * Save users to filename.
     * @param fileName file name
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(users);
            outputStream.close();
        } catch (IOException e) {
            Log.e(LOG_TAG, "File write failed: " + e.toString());
        }
    }
}
