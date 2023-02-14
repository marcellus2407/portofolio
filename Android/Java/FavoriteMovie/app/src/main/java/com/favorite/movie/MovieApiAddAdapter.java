package com.favorite.movie;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.favorite.movie.MainActivity;
import com.favorite.movie.allmovieFragment;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieApiAddAdapter extends RecyclerView.Adapter<MovieApiAddAdapter.HolderData> {

    ArrayList<Movie> movies = new ArrayList<>();
    Context ctx;
    MainActivity act;
    public MovieApiAddAdapter(Context context, MainActivity act) {
        this.inflater = LayoutInflater.from(context);
        ctx=context;
        this.act = act;
    }

    LayoutInflater inflater;

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_movie,parent,false);
        return new HolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        Movie movie = movies.get(position);
        holder.mTitle.setText(movie.title);
        Glide.with(ctx)
                .load("https://image.tmdb.org/t/p/w185"+movie.image)
                .into(holder.mImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", ""+movie.vote);
                if(act.databaseHelper.insertMovie(movie.title,movie.image,movie.vote,movie.desc,act.id)){
                    Toast.makeText(act, "Movie added to favorite",
                            Toast.LENGTH_LONG).show();
                    act.loadFragment(new allmovieFragment());
                }else{
                    Toast.makeText(act, "Add to favorite failed",
                            Toast.LENGTH_LONG).show();
                }
                        //ailFragment(movie.image, movie.title, movie.vote, movie.desc));
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class HolderData extends RecyclerView.ViewHolder{
        TextView mTitle;
        ImageView mImage;
        public HolderData(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.movieTitleRow);
            mImage = itemView.findViewById(R.id.movieImgRow);
        }
    }
}
