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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;

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
import android.view.View;
import android.widget.Toast;

import com.example.instagramemulator.Models.Post;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MAIN_ACTIVITY";
    public static final int CAMERA_REQUEST_CODE = 666;
    public final Context context = this;
    BottomNavigationView bottomNavigation;
    FragmentManager fragmentManager = getSupportFragmentManager();
    String photoFileName = "photo.jpg";
    File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Add the TimelineFragment to the activity upon first startup
        if(savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragmentPrimary, TimelineFragment.class, null)
                    .commit();
        }

        bottomNavigation = findViewById(R.id.btmNavigation);
        bottomNavigation.getMenu().getItem(0).setIcon(R.drawable.ic_home_filled);
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.icHome:
                    fragmentManager.beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentPrimary, TimelineFragment.class, null)
                            .commit();
                    bottomNavigation.getMenu().getItem(0).setIcon(R.drawable.ic_home_filled);
                    bottomNavigation.getMenu().getItem(1).setIcon(R.drawable.ic_post_outline);
                    bottomNavigation.getMenu().getItem(2).setIcon(R.drawable.ic_profile_outline);
                    break;
                case R.id.icPost:
                    if(R.id.icPost != bottomNavigation.getSelectedItemId()) {
                        Log.i(TAG, "Post icon selected; opening camera");
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        photoFile = getPhotoFileUri(photoFileName);
                        Uri fileProvider = FileProvider.getUriForFile(MainActivity.this, "com.codepath.fileprovider", photoFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

                        if(intent.resolveActivity(getPackageManager()) != null) {
                            try {
                                startActivityForResult(intent, CAMERA_REQUEST_CODE);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        bottomNavigation.getMenu().getItem(0).setIcon(R.drawable.ic_home_outline);
                        bottomNavigation.getMenu().getItem(1).setIcon(R.drawable.ic_post_filled);
                        bottomNavigation.getMenu().getItem(2).setIcon(R.drawable.ic_profile_outline);
                    }
                    break;
                case R.id.icProfile:
                    if(R.id.icProfile != bottomNavigation.getSelectedItemId()) {
                        Log.i(TAG, "Profile icon selected; switching to ProfileFragment");
                        fragmentManager.beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.fragmentPrimary, ProfileFragment.class, null)
                                .commit();
                        bottomNavigation.getMenu().getItem(0).setIcon(R.drawable.ic_home_outline);
                        bottomNavigation.getMenu().getItem(1).setIcon(R.drawable.ic_post_outline);
                        bottomNavigation.getMenu().getItem(2).setIcon(R.drawable.ic_profile_filled);
//                        Intent intent = new Intent(context, ProfileFragment.class);
//                        startActivity(intent);
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
                takenImage = BitmapScaler.scaleToFitWidth(takenImage, 800);
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                takenImage.compress(Bitmap.CompressFormat.JPEG, 50, bs);

//                Intent intent = new Intent(this, PostActivity.class);
//                intent.putExtra("picture_bytes", bs.toByteArray());
                try {
                    Log.i(TAG, "Have picture; starting PostFragment");

                    Bundle bundle = new Bundle();
                    bundle.putByteArray("photo", bs.toByteArray());
                    bundle.putString("photoFilePath", photoFile.getAbsolutePath());
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragmentPrimary, PostFragment.class, bundle)
                            .setReorderingAllowed(true)
                            .commit();
//                    startActivityForResult(intent, POST_REQUEST_CODE);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                Toast.makeText(this, "Error taking photo", Toast.LENGTH_SHORT).show();
            }
        }
//        else if(requestCode == POST_REQUEST_CODE && resultCode == RESULT_OK) {
//            try {
//                savePost(data.getExtras().getString("caption"), ParseUser.getCurrentUser(), photoFile);
//                //Do the Recycler view stuff
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        else {
//            Log.e(TAG, "Bad result while finishing activity");
//        }
    }

    public File getPhotoFileUri(String fileName) {
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);
        if(!mediaStorageDir.exists() && !mediaStorageDir.mkdirs())
            Log.e(TAG, "Failed to create directory");

        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }
}