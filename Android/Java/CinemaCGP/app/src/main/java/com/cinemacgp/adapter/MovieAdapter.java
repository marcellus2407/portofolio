package com.cinemacgp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cinemacgp.databinding.RowMovieBinding;
import com.cinemacgp.model.Movie;
import com.cinemacgp.utils.UtilsMovieAPI;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    ArrayList<Movie> list = new ArrayList<>();
    MovieListener listener;

    public MovieAdapter(MovieListener listener) {
        this.listener = listener;
    }

    public void setList(ArrayList<Movie> list){
        this.list=list;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RowMovieBinding bind;
        public ViewHolder(RowMovieBinding bind) {
            super(bind.getRoot());
            this.bind=bind;
        }
        public void bind(Movie movie){
            Glide.with(bind.getRoot().getContext())
                    .load(UtilsMovieAPI.IMAGE_URL +movie.image)
                    .into(bind.ivMovie);
            bind.tvTitle.setText(movie.id+" - "+movie.title);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ViewHolder(RowMovieBinding.inflate(LayoutInflater.from(viewGroup.getContext()),viewGroup,false));
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Movie movie = list.get(position);
        viewHolder.bind(movie);
        viewHolder.bind.getRoot().setOnClickListener(view -> listener.onClick(movie));
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface MovieListener{
        void onClick(Movie movie);
    }
}
