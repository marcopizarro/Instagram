package com.codepath.instagram;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.util.Log;
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

import org.parceler.Parcels;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
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
        private ImageView ivPostUserPhoto;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPostDesc = itemView.findViewById(R.id.tvPostDesc);
            tvPostUsername = itemView.findViewById(R.id.tvPostUsername);
            ivPostImage = itemView.findViewById(R.id.ivPostImage);
            ivPostUserPhoto = itemView.findViewById(R.id.ivPostUserPhoto);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition(); // gets item position
                    if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                        Post post = posts.get(position);
                        Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                        intent.putExtra("post", Parcels.wrap(post));
                        view.getContext().startActivity(intent);
                    }
                }
            });
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
            ParseUser user = post.getUser();
            if  (user != null) {
                ParseFile image1 = user.getParseFile("image");
                if (image1 != null) {
                    Glide.with(context)
                            .load(image1.getUrl())
                            .circleCrop()
                            .into(ivPostUserPhoto);
                } else {
                    Glide.with(context)
                            .load(R.drawable.anon)
                            .circleCrop()
                            .into(ivPostUserPhoto);
                }
            }


        }
    }
}
