package com.codepath.instagram.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.instagram.Post;
import com.codepath.instagram.PostsAdapter;
import com.codepath.instagram.R;
import com.codepath.instagram.UserAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";

    private ImageView ivProfilePicture;
    private ParseUser user;
    private TextView tvHandle;
    private RecyclerView rvPosts;
    private UserAdapter postsAdapter;
    private List<Post> userPosts;

    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user = ParseUser.getCurrentUser();

        rvPosts = view.findViewById(R.id.rvPosts);
        tvHandle = view.findViewById(R.id.tvHandle);
        ivProfilePicture = view.findViewById(R.id.ivProfilePicture);

        userPosts = new ArrayList<>();
        postsAdapter = new UserAdapter(getContext(), userPosts);
        rvPosts.setAdapter(postsAdapter);
        rvPosts.setLayoutManager(new GridLayoutManager(getContext(), 3));

        if  (user != null) {
            tvHandle.setText(user.getUsername());
            ParseFile image = user.getParseFile("image");
            if (image != null) {
                Glide.with(this)
                        .load(image.getUrl())
                        .circleCrop()
                        .into(ivProfilePicture);
            }
        }
        queryPosts();
    }

    private void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.addDescendingOrder("createdAt");
        query.setLimit(20);
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Unable to fetch posts", e);
                    return;
                } else {
//                    swipeContainer.setRefreshing(false);
                    postsAdapter.clear();
                    userPosts.addAll(posts);
                    postsAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}