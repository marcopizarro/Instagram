package com.codepath.instagram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvPostUsername;
        private TextView tvPostDesc;
        private ImageView ivPostImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPostDesc = itemView.findViewById(R.id.tvPostDesc);
            tvPostUsername = itemView.findViewById(R.id.tvPostUsername);
            ivPostImage = itemView.findViewById(R.id.ivPostImage);
        }

        public void bind(Post post) {
            tvPostDesc.setText(post.getDescription());
            tvPostUsername.setText(post.getUser().getUsername());
            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context)
                        .load(post.getImage().getUrl())
                        .into(ivPostImage);
            }
        }
    }
}
