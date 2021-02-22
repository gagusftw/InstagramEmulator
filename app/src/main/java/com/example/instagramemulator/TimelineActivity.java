/*
PROJECT GOALS
-Use Parse to set up a backend that will allow users to authenticate
-Utilize Parse server to store user data and post data in a database
-Develop an app that will allow users to (1) create new account, (2) log in/out (3) create a post
-Use the camera API to allow users to take/upload photos with their device

BRAINSTORM
-LoginActivity, SignUpActivity??, MainActivity, PostActivity
-Gotta setup layout of menu and/or bottom bar
-Get IG resources imported and setup
-Handle basic logic to facilitate posting
*/

package com.example.instagramemulator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.instagramemulator.Models.Post;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

public class TimelineActivity extends AppCompatActivity {
    public static final String TAG = "TIMELINE_ACTIVITY";
    public static final int CAMERA_REQUEST_CODE = 666;
    public static final int POST_REQUEST_CODE = 554;
    public final Context context = this;
    BottomNavigationView bottomNavigation;
    String photoFileName = "photo.jpg";
    File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bottomNavigation = findViewById(R.id.btmNavigation);
        bottomNavigation.getMenu().getItem(0).setIcon(R.drawable.ic_home_filled);
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.icPost:
                    if(R.id.icPost != bottomNavigation.getSelectedItemId()) {
                        Log.i(TAG, "Post icon selected; opening camera");
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        photoFile = getPhotoFileUri(photoFileName);
                        Uri fileProvider = FileProvider.getUriForFile(TimelineActivity.this, "com.codepath.fileprovider", photoFile);
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
                case R.id.icProfile:
                    if(R.id.icProfile != bottomNavigation.getSelectedItemId()) {
                        Log.i(TAG, "Profile icon selected; switching to ProfileActivity");
                        Intent intent = new Intent(context, ProfileActivity.class);
                        startActivity(intent);
                    }
                    break;
            }
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                Intent intent = new Intent(this, PostActivity.class);
                intent.putExtra("picture", takenImage);
                startActivityForResult(intent, POST_REQUEST_CODE);
            }
            else {
                Toast.makeText(this, "Error taking photo", Toast.LENGTH_SHORT).show();
            }
        }
        else if(requestCode == POST_REQUEST_CODE && resultCode == RESULT_OK) {
            savePost(data.getExtras().getString("caption"), ParseUser.getCurrentUser(), photoFile);
            //Do the Recycler view stuff
        }
    }

    public File getPhotoFileUri(String fileName) {
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);
        if(!mediaStorageDir.exists() && !mediaStorageDir.mkdirs())
            Log.e(TAG, "Failed to create directory");

        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }

    protected void savePost(String caption, ParseUser user, File photoFile) {
        Post post = new Post();
        post.setCaption(caption);
        post.setUser(user);
        post.setPicture(new ParseFile(photoFile));
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null) {
                    Log.i(TAG, "Post successfully saved to Parse server");
                }
                else {
                    Log.e(TAG, "Error saving post to Parse server");
                }
            }
        });
    }
}