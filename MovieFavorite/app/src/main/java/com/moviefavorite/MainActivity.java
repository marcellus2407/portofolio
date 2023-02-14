package com.moviefavorite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    RecyclerView ListAllMovie;
    MovieApiAdapter movieApiAdapter;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListAllMovie = findViewById(R.id.ListAllMovie);
        ListAllMovie.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        movieApiAdapter = new MovieApiAdapter(this);
        requestQueue = Volley.newRequestQueue(this);
        ListAllMovie.setAdapter(movieApiAdapter);
        getalldata();
    }
    private void getalldata() {
        movieApiAdapter.movies = new ArrayList<>();
        for (int i = 1; i <= 472; i++) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ("https://api.themoviedb.org/3/movie/popular?api_key=1805007963a4760044640bde94121491&page="+i), null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray results = (JSONArray) response.get("results");
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject jsonObject = results.getJSONObject(i);
                            Movie movie = new Movie(
                                    jsonObject.getString("title"),
                                    jsonObject.getString("poster_path"),
                                    jsonObject.getDouble("vote_average"),
                                    jsonObject.getString("overview"));
                            movieApiAdapter.movies.add(movie);
                        }
                        movieApiAdapter.notifyDataSetChanged();
                    } catch (Exception w) {
                        Toast.makeText(getBaseContext(), w.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getBaseContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            requestQueue.add(jsonObjectRequest);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.kebab_menu,menu);
        menu.findItem(R.id.set).setTitle(getResources().getText(R.string.set));
        menu.findItem(R.id.fav).setTitle(getResources().getText(R.string.fav));
        menu.findItem(R.id.home).setTitle(getResources().getText(R.string.home));
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.set:
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
                break;
            case R.id.fav:
                startActivity(new Intent(this,FavActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}