package com.example.instagramemulator;


import androidx.fragment.app.Fragment;

import android.content.Context;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
//    public static final String TAG = "PROFILE_FRAGMENT";
    public final Context context = getActivity();
//    BottomNavigationView bottomNavigation;
    TextView tvCurrentUser;
    Button btnLogOut;
//    String photoFileName = "photo.jpg";
//    File photoFile;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProfileFragment2.
     */
    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
//        bottomNavigation = view.findViewById(R.id.btmNavigation);
        tvCurrentUser = view.findViewById(R.id.tvCurrentUser);
        btnLogOut = view.findViewById(R.id.btnLogOut);

        tvCurrentUser.setText(String.format("Logged in as @%s", ParseUser.getCurrentUser().getUsername()));
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOutInBackground();
                getActivity().finish();
            }
        });

//        bottomNavigation.getMenu().getItem(2).setIcon(R.drawable.ic_profile_filled);
//        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.icHome:
//                    if(R.id.icHome != bottomNavigation.getSelectedItemId()) {
//                        Log.i(TAG, "Home icon selected; switching to TimelineFragment");
//                        finish();
//                    }
//                    break;
//                case R.id.icPost:
//                    if(R.id.icPost != bottomNavigation.getSelectedItemId()) {
//                        Log.i(TAG, "Post icon selected; opening camera");
//                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        photoFile = getPhotoFileUri(photoFileName);
//                        Uri fileProvider = FileProvider.getUriForFile(ProfileFragment.this, "com.codepath.fileprovider", photoFile);
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
//
//                        if(intent.resolveActivity(getPackageManager()) != null) {
//                            try {
//                                startActivityForResult(intent, CAMERA_REQUEST_CODE);
//                            }
//                            catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                    break;
//            }
//            return true;
//        });
    }

//    public File getPhotoFileUri(String fileName) {
//        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);
//        if(!mediaStorageDir.exists() && !mediaStorageDir.mkdirs())
//            Log.e(TAG, "Failed to create directory");
//
//        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);
//
//        return file;
//    }
}