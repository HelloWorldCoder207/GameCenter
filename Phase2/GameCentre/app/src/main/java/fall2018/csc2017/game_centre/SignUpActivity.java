package fall2018.csc2017.game_centre;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.*;

public class SignUpActivity extends AppCompatActivity {
    /**
     * The file handler for user file io.
     */
    private UserFileHandler fileHandler = UserFileHandler.getInstance();

    /**
     * Mapping from username to user.
     */
    private Map<String,User> users;

    /**
     * The on create method for init
     * @param savedInstanceState activity field needed by superclass
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

//        if (getIntent().getExtras() == null) {
            fileHandler.loadFromFile(this, UserFileHandler.FILE_NAME);
            users = fileHandler.getUsers();
//        } else {
//            users = (HashMap<String, User>) getIntent().getExtras().get("Users");
//        }

        addSignUpButtonListener();
    }

    /**
     * Activate sign up button.
     */
    private void addSignUpButtonListener() {
        Button signUp = findViewById(R.id.buttonsignup);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etUsername = findViewById(R.id.etUsername);
                EditText etPassword = findViewById(R.id.etPassword);
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                if (Pattern.matches( "w*", username)){
                    if (username.isEmpty() || password.isEmpty()) {
                        makeToastText("Fill in empty field");
                    } else if (users.containsKey(username)) {
                        makeToastText("User already exists");
                    } else {
                        users.put(username, new User(username, password));
                        makeToastText("Sign Up successful");
                        switchToLogin();
                    }
                }
                else makeToastText("Invalid Username");
            }
        });
    }

    /**
     * Switch to login activity after successful sign up.
     */
    private void switchToLogin() {
        fileHandler.saveToFile(this, UserFileHandler.FILE_NAME);
        Intent i = new Intent(this, LoginActivity.class);
        i.putExtra("UpdatedUsers", (Serializable) users);
        startActivity(i);
    }

    /**
     * Make text using Toast.
     * @param msg message to display
     */
    private void makeToastText(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

//    /**
//     * Load users from file.
//     * @param fileName file name
//     */
//    private void loadFromFile(String fileName) {
//        try {
//            InputStream inputStream = this.openFileInput(fileName);
//            if (inputStream != null) {
//                ObjectInputStream input = new ObjectInputStream(inputStream);
//                users = (HashMap<String, User>) input.readObject();
//                inputStream.close();
//            }
//        } catch (FileNotFoundException e) {
//            Log.e(LOG_TAG, "File not found: " + e.toString());
//        } catch (IOException e) {
//            Log.e(LOG_TAG, "Can not read file: " + e.toString());
//        } catch (ClassNotFoundException e) {
//            Log.e(LOG_TAG, "File contained unexpected data type: " + e.toString());
//        } finally {
//            if (users == null) {
//                users = new HashMap<>();
//            }
//        }
//    }
//
//    /**
//     * Save users to file.
//     * @param fileName file name
//     */
//    private void saveToFile(String fileName) {
//        try {
//            ObjectOutputStream outputStream = new ObjectOutputStream(
//                    this.openFileOutput(fileName, MODE_PRIVATE));
//            outputStream.writeObject(users);
//            outputStream.close();
//        } catch (IOException e) {
//            Log.e(LOG_TAG, "File write failed: " + e.toString());
//        }
//    }
}
