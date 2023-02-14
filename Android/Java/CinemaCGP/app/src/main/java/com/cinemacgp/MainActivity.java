package com.cinemacgp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cinemacgp.adapter.MovieAdapter;
import com.cinemacgp.database.DatabaseHelper;
import com.cinemacgp.databinding.ActivityMainBinding;
import com.cinemacgp.model.Movie;
import com.cinemacgp.utils.UtilsMovieAPI;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    MovieAdapter adapter;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.rvMovie.setLayoutManager(new LinearLayoutManager(this));
        Gson gson = new Gson();
        adapter = new MovieAdapter(movie ->
                startActivity(
                        new Intent(this, DetailMovieActivity.class)
                        .putExtra("movie",gson.toJson(movie)))
        );

        binding.rvMovie.setAdapter(adapter);
        requestQueue = Volley.newRequestQueue(this);
        getalldata();
        binding.svMovie.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()) {
                    getalldata();
                }else{
                    getSearchData(newText);
                }
                return false;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.location:
                startActivity(new Intent(this, LocationActivity.class));
                break;
            case R.id.rental:
                startActivity(new Intent(this,RentalActivity.class));
                break;
            case R.id.logout:
                SharedPreferences preferences = getSharedPreferences("Mypref", 0);
                preferences.edit().remove("id").commit();
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void getSearchData(String keyword) {
        ProgressDialog dialog = ProgressDialog.show(this, "",
                "Loading. Please wait...", true);
        ArrayList<Movie> movies = new ArrayList<>();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, UtilsMovieAPI.getSearchMoviesURL(keyword), null, response -> {
            try {
                JSONArray results = (JSONArray) response.get("results");
                for(int i=0;i<results.length();i++) {
                    JSONObject jsonObject = results.getJSONObject(i);
                    Movie movie = new Movie(
                            jsonObject.getInt("id"),
                            jsonObject.getString("title"),
                            jsonObject.getString("poster_path"),
                            jsonObject.getDouble("vote_average"),
                            jsonObject.getString("overview"));
                    movies.add(movie);
                }
            } catch (Exception w) {
                Toast.makeText(this, w.getMessage(), Toast.LENGTH_LONG).show();
            }
            adapter.setList(movies);
            dialog.dismiss();
        }, error -> Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show());
        requestQueue.add(jsonObjectRequest);
    }
    private void getalldata() {
        ProgressDialog dialog = ProgressDialog.show(this, "",
                "Loading. Please wait...", true);
        ArrayList<Movie> movies = new ArrayList<>();
        for (int i = 1; i <= 500; i++) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, UtilsMovieAPI.getMoviesURL(i), null, response -> {
                try {
                    int page = response.getInt("page");
                    JSONArray results = (JSONArray) response.get("results");
                    for (int j = 0; j < results.length(); j++) {
                        JSONObject jsonObject = results.getJSONObject(j);
                        Movie movie = new Movie(
                                jsonObject.getInt("id"),
                                jsonObject.getString("title"),
                                jsonObject.getString("poster_path"),
                                jsonObject.getDouble("vote_average"),
                                jsonObject.getString("overview"));
                        movies.add(movie);
                    }
                    if(page==500){
                        dialog.dismiss();
                    }
                } catch (Exception w) {
                    Toast.makeText(this, w.getMessage(), Toast.LENGTH_LONG).show();
                }
                adapter.setList(movies);
            }, error -> Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show());
            requestQueue.add(jsonObjectRequest);
        }
    }
}