package com.example.instagramemulator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;

public class ProfileActivity extends AppCompatActivity {
    public static final String TAG = "PROFILE_ACTIVITY";
    public final Context context = this;
    BottomNavigationView bottomNavigation;
    TextView tvCurrentUser;
    Button btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        bottomNavigation = findViewById(R.id.btmNavigation);
        tvCurrentUser = findViewById(R.id.tvCurrentUser);
        btnLogOut = findViewById(R.id.btnLogOut);

        bottomNavigation.getMenu().getItem(2).setIcon(R.drawable.ic_profile_filled);
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.icHome:
                    if(R.id.icHome != bottomNavigation.getSelectedItemId()) {
                        Log.i(TAG, "Home icon selected; switching to TimelineActivity");
                        Intent intent = new Intent(context, TimelineActivity.class);
                        startActivity(intent);
                    }
                    break;
                case R.id.icPost:
                    if(R.id.icPost != bottomNavigation.getSelectedItemId()) {
                        Log.i(TAG, "Post icon selected; switching to PostActivity");
                        Intent intent = new Intent(context, PostActivity.class);
                        startActivity(intent);
                    }
                    break;
            }
            return true;
        });

        tvCurrentUser.setText(String.format("Logged in as @%s", ParseUser.getCurrentUser().getUsername()));
    }

    public void logOut(View view) {
        ParseUser.logOutInBackground();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}