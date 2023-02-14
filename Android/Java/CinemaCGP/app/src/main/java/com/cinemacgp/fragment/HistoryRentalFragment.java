package com.cinemacgp.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cinemacgp.DetailMovieActivity;
import com.cinemacgp.R;
import com.cinemacgp.adapter.RentalHistoryAdapter;
import com.cinemacgp.database.DatabaseHelper;
import com.cinemacgp.databinding.FragmentHistoryRentalBinding;
import com.cinemacgp.model.Movie;
import com.cinemacgp.utils.UtilsMovieAPI;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HistoryRentalFragment extends Fragment {
    FragmentHistoryRentalBinding binding;
    DatabaseHelper db;
    int idUser;
    public HistoryRentalFragment(DatabaseHelper db, int idUser){
        this.db=db;
        this.idUser=idUser;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentHistoryRentalBinding.inflate(getLayoutInflater());
        binding.lvHistoryRental.setAdapter(new RentalHistoryAdapter(requireContext(), db.getRentalHistory(idUser), id -> getMovie(id)));
        return binding.getRoot();
    }
    private void getMovie(int id) {
        Gson gson = new Gson();
        RequestQueue requestQueue  = Volley.newRequestQueue(requireContext());
        ProgressDialog dialog = ProgressDialog.show(requireContext(), "",
                "Loading. Please wait...", true);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, UtilsMovieAPI.getMovie(id), null, response -> {
            try {
                Movie movie = new Movie(
                        response.getInt("id"),
                        response.getString("title"),
                        response.getString("poster_path"),
                        response.getDouble("vote_average"),
                        response.getString("overview"));
                startActivity(
                        new Intent(requireContext(), DetailMovieActivity.class)
                                .putExtra("movie",gson.toJson(movie)));
            } catch (Exception w) {
                Toast.makeText(requireContext(), w.getMessage(), Toast.LENGTH_LONG).show();
            }
            dialog.dismiss();
        }, error -> Toast.makeText(requireContext(), error.getMessage(), Toast.LENGTH_LONG).show());
        requestQueue.add(jsonObjectRequest);
    }
}