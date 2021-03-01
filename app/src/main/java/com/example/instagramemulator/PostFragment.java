package com.example.instagramemulator;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.instagramemulator.Models.Post;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostFragment extends Fragment {
    public static final String TAG = "POST_FRAGMENT";
    EditText etCaption;
    Button btnPost;
    ImageView ivPicture;
    Bitmap photo;
    String photoFilePath;
    private static final String ARG_PARAM1 = "photo";
    private static final String ARG_PARAM2 = "photoFilePath";

    public PostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param photoBytes Byte array of photo to display
     * @param photoFilePath Path to photo URI
     * @return A new instance of fragment PostFragment.
     */
    public static PostFragment newInstance(byte[] photoBytes, String photoFilePath) {
        PostFragment fragment = new PostFragment();
        Bundle args = new Bundle();
        args.putByteArray(ARG_PARAM1, photoBytes);
        args.putString(ARG_PARAM2, photoFilePath);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            byte[] photoBytes = getArguments().getByteArray(ARG_PARAM1);
            photo = BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes.length);
            photoFilePath = getArguments().getString("photoFilePath");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etCaption = view.findViewById(R.id.etCaption);
        btnPost = view.findViewById(R.id.btnPost);
        ivPicture = view.findViewById(R.id.ivPicture);
        ivPicture.setImageBitmap(photo);

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postToFeed();
            }
        });
    }

    public void postToFeed() {
        String caption = etCaption.getText().toString();
        File photoFile = new File(photoFilePath);
        savePost(caption, ParseUser.getCurrentUser(), photoFile);
        etCaption.setText("");
        ivPicture.setImageBitmap(null);
    }

    protected void savePost(String caption, ParseUser user, File photoFile) {
        Post post = new Post();
        post.setCaption(caption);
        post.setUser(user);
        post.setPicture(new ParseFile(photoFile));

        getParentFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentPrimary, TimelineFragment.class, null)
                .commit();

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