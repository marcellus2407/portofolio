package com.cinemacgp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cinemacgp.database.DatabaseHelper;
import com.cinemacgp.databinding.ActivityRentalBinding;
import com.cinemacgp.fragment.FormRentalFragment;
import com.cinemacgp.fragment.HistoryRentalFragment;
import com.cinemacgp.model.Movie;
import com.cinemacgp.utils.UtilsMovieAPI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class RentalActivity extends AppCompatActivity {
    ActivityRentalBinding binding;
    ArrayList<Movie> movies;
    DatabaseHelper db;
    int idUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRentalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db=new DatabaseHelper(this);
        SharedPreferences prefs = getSharedPreferences("Mypref", MODE_PRIVATE);
        idUser=prefs.getInt("id", -1);
        binding.bnvRental.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.form:
                    loadFragment(new FormRentalFragment(movies,db,idUser));
                    break;
                case R.id.history:
                    loadFragment(new HistoryRentalFragment(db,idUser));
                    break;
            }
            return true;
        });
        getalldata();
    }
    private void getalldata() {
        RequestQueue requestQueue  = Volley.newRequestQueue(this);
        ProgressDialog dialog = ProgressDialog.show(this, "",
                "Loading. Please wait...", true);
        movies = new ArrayList<>();
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
                        loadFragment(new FormRentalFragment(movies,db,idUser));
                    }
                } catch (Exception w) {
                    Toast.makeText(this, w.getMessage(), Toast.LENGTH_LONG).show();
                }

            }, error -> Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show());
            requestQueue.add(jsonObjectRequest);
        }
    }
    public boolean loadFragment(Fragment selectedFragment) {
        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            if (! getSupportFragmentManager().isDestroyed())
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
        return false;
    }
}