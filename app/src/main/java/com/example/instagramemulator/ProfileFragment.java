package com.example.instagramemulator;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instagramemulator.Models.Post;
import com.example.instagramemulator.Models.PostAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends TimelineFragment {
    public static final String TAG = "PROFILE_FRAGMENT";
    TextView tvCurrentUser;
    Button btnLogOut;
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
        super.onViewCreated(view, savedInstanceState);
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
    }

    @Override
    public void fetchPosts(int n) {
        ParseQuery<Post> query = ParseQuery.getQuery("Post");
        query.setLimit(n);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        query.addDescendingOrder(Post.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if(e == null) {
                    Log.i(TAG, "Successfully fetched List of Parse Posts");
                    listPosts = objects;
                    adapter.clear();
                    adapter.addAll(listPosts);
                }
                else {
                    Log.e(TAG, "Error fetching list of Parse Posts");
                    Toast.makeText(getContext(), "Could not fetch posts", Toast.LENGTH_SHORT).show();
                }
                swipeRefreshContainer.setRefreshing(false);
            }
        });
    }
}