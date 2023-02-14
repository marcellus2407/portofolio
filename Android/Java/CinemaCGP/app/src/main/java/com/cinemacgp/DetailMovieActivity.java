package com.cinemacgp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.cinemacgp.databinding.ActivityDetailMovieBinding;
import com.cinemacgp.model.Movie;
import com.cinemacgp.utils.UtilsMovieAPI;
import com.google.gson.Gson;

public class DetailMovieActivity extends AppCompatActivity {
    ActivityDetailMovieBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDetailMovieBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Gson gson = new Gson();
        Movie movie = gson.fromJson(getIntent().getExtras().getString("movie"),Movie.class);
        binding.tvTitle.setText(movie.title);
        binding.tvVote.setText("Vote Average : "+movie.vote);
        binding.tvDesc.setText(movie.desc);
        Glide.with(this)
                .load(UtilsMovieAPI.IMAGE_URL +movie.image)
                .into(binding.ivImage);
    }
}