package com.moviefavorite;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieApiAdapter extends RecyclerView.Adapter<MovieApiAdapter.ViewHolder> {

    ArrayList<Movie> movies = new ArrayList<>();
    Context ctx;
    MovieApiAdapter(Context ctx){
        this.ctx=ctx;
    }
    @NonNull
    @Override
    public MovieApiAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_movie, viewGroup, false);
        return new MovieApiAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(MovieApiAdapter.ViewHolder viewHolder, final int position) {
        Movie movie = movies.get(position);
        viewHolder.mTitle.setText(movie.title);
        Glide.with(ctx)
                .load("https://image.tmdb.org/t/p/w185"+movie.image)
                .into(viewHolder.mImage);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctx.startActivity(new Intent(ctx,DetailActivity.class)
                        .putExtra("img",movie.image)
                        .putExtra("title",movie.title)
                        .putExtra("avg", movie.vote)
                        .putExtra("desc", movie.desc));
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle;
        ImageView mImage;
        public ViewHolder(View view) {
            super(view);
            mTitle = itemView.findViewById(R.id.movieTitleRow);
            mImage = itemView.findViewById(R.id.movieImgRow);
        }
    }
}
