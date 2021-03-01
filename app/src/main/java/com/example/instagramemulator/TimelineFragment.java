package com.example.instagramemulator;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.instagramemulator.Models.Post;
import com.example.instagramemulator.Models.PostAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimelineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimelineFragment extends Fragment {
    public static final String TAG = "TIMELINE_FRAGMENT";
    protected RecyclerView rvPosts;
    protected PostAdapter adapter;
    protected SwipeRefreshLayout swipeRefreshContainer;
    List<Post> listPosts = new ArrayList<>();

    public TimelineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TimelineFragment.
     */
    public static TimelineFragment newInstance() {
        return new TimelineFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_timeline, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvPosts = view.findViewById(R.id.rvPosts);
        swipeRefreshContainer = view.findViewById(R.id.swipeRefreshContainer);
        adapter = new PostAdapter(getContext(), listPosts);
        rvPosts.setAdapter(adapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        fetchPosts(20);

        swipeRefreshContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPosts(20);
            }
        });
    }

    public void fetchPosts(int n) {
        ParseQuery<Post> query = ParseQuery.getQuery("Post");
        query.setLimit(n);
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