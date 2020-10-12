package com.ulan.timetable.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ulan.timetable.R;
import com.ulan.timetable.utils.DbHelper;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "Login";
    private static final int REQUEST_SIGNUP = 0;
    private static DbHelper db;
    private EditText userIDText, passwordText;
    private TextView signupLink;
    private Button loginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        userIDText = (EditText) findViewById(R.id.input_userID);
        passwordText = (EditText) findViewById(R.id.input_password);
        loginButton = (Button) findViewById(R.id.btn_login);
        signupLink = (TextView) findViewById(R.id.link_signup);

        db = new DbHelper(this);
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });

    }

    public void login() {

        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        loginButton.setEnabled(false);

        String userID = userIDText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        int status = Integer.parseInt(db.getLoginCount(userID, password));
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
        if (status > 0) {
            onLoginSuccess();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("userID", userID);
            startActivity(intent);
        } else {
            onLoginFailed();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        //This 'finish()' is for exiting the app when back button pressed from Home page which is ActivityHome
        finish();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String userID = userIDText.getText().toString();
        String password = passwordText.getText().toString();

        if (userID.isEmpty()) {
            userIDText.setError("enter a user ID");
            valid = false;
        } else {
            userIDText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            userIDText.setError(null);
        }

        return valid;
    }
}

