package com.example.instagramemulator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;

import java.io.File;

import static com.example.instagramemulator.TimelineActivity.CAMERA_REQUEST_CODE;

public class ProfileActivity extends AppCompatActivity {
    public static final String TAG = "PROFILE_ACTIVITY";
    public final Context context = this;
    BottomNavigationView bottomNavigation;
    TextView tvCurrentUser;
    Button btnLogOut;
    String photoFileName = "photo.jpg";
    File photoFile;

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
                        finish();
                    }
                    break;
                case R.id.icPost:
                    if(R.id.icPost != bottomNavigation.getSelectedItemId()) {
                        Log.i(TAG, "Post icon selected; opening camera");
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        photoFile = getPhotoFileUri(photoFileName);
                        Uri fileProvider = FileProvider.getUriForFile(ProfileActivity.this, "com.codepath.fileprovider", photoFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

                        if(intent.resolveActivity(getPackageManager()) != null) {
                            try {
                                startActivityForResult(intent, CAMERA_REQUEST_CODE);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
            }
            return true;
        });

        tvCurrentUser.setText(String.format("Logged in as @%s", ParseUser.getCurrentUser().getUsername()));
    }

    public File getPhotoFileUri(String fileName) {
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);
        if(!mediaStorageDir.exists() && !mediaStorageDir.mkdirs())
            Log.e(TAG, "Failed to create directory");

        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }

    public void logOut(View view) {
        ParseUser.logOutInBackground();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}