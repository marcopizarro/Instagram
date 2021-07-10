package com.codepath.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.parceler.Parcels;

public class DetailsActivity extends AppCompatActivity {
    public static final String TAG = "DetailActivity";

    private TextView tvPostUsername;
    private TextView tvPostDesc;
    private ImageView ivPostImage;
    private TextView tvPostTime;
    private ImageView ivPostUserPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        Post post = (Post) Parcels.unwrap(getIntent().getParcelableExtra("post"));

        tvPostDesc = findViewById(R.id.tvPostDesc);
        tvPostUsername = findViewById(R.id.tvPostTimeAgo);
        ivPostImage = findViewById(R.id.ivPostImage);
        tvPostTime = findViewById(R.id.tvPostTime);
        ivPostUserPhoto = findViewById(R.id.ivPostUserPhoto);

        tvPostDesc.setText(post.getDescription());
        tvPostTime.setText(Post.calculateTimeAgo(post.getCreatedAt()));
        tvPostUsername.setText(post.getUser().getUsername());
        ParseFile image = post.getImage();
        if (image != null) {
            Glide.with(this)
                    .load(post.getImage().getUrl())
                    .into(ivPostImage);
        }
        ParseUser user = post.getUser();
        if  (user != null) {
            ParseFile image1 = user.getParseFile("image");
            if (image1 != null) {
                Glide.with(this)
                        .load(image1.getUrl())
                        .circleCrop()
                        .into(ivPostUserPhoto);
            }
        }
    }
}