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


public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUp";
    private EditText nameText;
    private EditText userIDText;
    private EditText organisationText;
    private EditText passwordText;
    private EditText confirmPasswordText;
    private Button signupButton;
    private TextView loginLink;
    DbHelper db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        loginLink = (TextView) findViewById(R.id.link_login);
        signupButton = (Button) findViewById(R.id.btn_signup);
        nameText = (EditText) findViewById(R.id.input_name);
        userIDText = (EditText) findViewById(R.id.input_userID);
        organisationText = (EditText) findViewById(R.id.input_organisation);
        passwordText = (EditText) findViewById(R.id.input_password);
        confirmPasswordText = (EditText) findViewById(R.id.input_confirm_password);

        db = new DbHelper(this);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Registration successful!");
        progressDialog.show();

        String name = nameText.getText().toString();
        String userID = userIDText.getText().toString();
        String organisation = organisationText.getText().toString();
        String passsword = passwordText.getText().toString();


        boolean status = db.addUser(userID, name, organisation, passsword);
        if(status) {
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            // On complete call either onSignupSuccess or onSignupFailed
                            // depending on success
                            onSignupSuccess();
                            Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(i);
                            progressDialog.dismiss();
                        }
                    }, 1000);
        }
        else {
            onSignupFailed();
        }

    }


    public void onSignupSuccess() {
        signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Registration failed", Toast.LENGTH_LONG).show();

        signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString();
        String userID = userIDText.getText().toString();
        String organisation = organisationText.getText().toString();
        String password = passwordText.getText().toString();
        String confirmPassword = confirmPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            nameText.setError("at least 3 characters");
            valid = false;
        } else {
            nameText.setError(null);
        }

        if (userID.isEmpty()) {
            userIDText.setError("enter a user ID");
            valid = false;
        } else {
            userIDText.setError(null);
        }

        if (organisation.isEmpty()) {
            organisationText.setError("enter a organisation name");
            valid = false;
        } else {
            organisationText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        if(confirmPassword.isEmpty()) {
            confirmPasswordText.setError("enter a confirm password");
            valid = false;
        } else {
            if(!confirmPassword.equals(password)) {
                confirmPasswordText.setError("confirm password is different to password");
                valid = false;
            } else {
                confirmPasswordText.setError(null);
            }
        }
        return valid;
    }

}
