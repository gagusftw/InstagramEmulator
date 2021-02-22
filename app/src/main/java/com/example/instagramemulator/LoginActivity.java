package com.example.instagramemulator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "LOGIN_ACTIVITY";
    ImageView ivLogo;
    EditText etUsername;
    EditText etPassword;
    Button btnLogin;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        ivLogo = findViewById(R.id.ivLogo);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
    }

    public void attemptLogin(View view) {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e == null) {
                    Log.i(TAG, String.format("%s logged in successfully", username));
                    Toast.makeText(LoginActivity.this, "Logging in...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, TimelineActivity.class);
                    startActivity(intent);
                }
                else {
                    Log.e(TAG, String.format("%s could not be logged in", user));
                    Toast.makeText(LoginActivity.this, "Error logging in. Try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void signUp(View view) {
        ParseUser user = new ParseUser();
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        user.setUsername(username);
        user.setPassword(password);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null) {
                    Log.i(TAG, String.format("New account created for %s", username));
                    Toast.makeText(LoginActivity.this, "User signed up", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, TimelineActivity.class);
                    startActivity(intent);
                }
                else {
                    Log.e(TAG, String.format("Could not create account for %s", username));
                    Toast.makeText(LoginActivity.this, "Error signing up. Try again", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }
}