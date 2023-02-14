package com.favorite.movie;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.favorite.movie.MainActivity;
import com.favorite.movie.addmovieFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class allmovieFragment extends Fragment {
    MainActivity act;
    SearchView searchmovie;
    RecyclerView ListAllMovie;
    MovieApiAdapter movieApiAdapter;
    RequestQueue requestQueue;
    FloatingActionButton FAB;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_allmovie, container, false);
        ListAllMovie = view.findViewById(R.id.ListAllMovie);
        searchmovie = view.findViewById(R.id.searchAllMovie);
        FAB = view.findViewById(R.id.addAllmovie);
        act = (MainActivity) getActivity();
        ListAllMovie.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        movieApiAdapter = new MovieApiAdapter(requireContext(),act);
        requestQueue = Volley.newRequestQueue(requireContext());
        ListAllMovie.setAdapter(movieApiAdapter);
        getalldata("https://api.themoviedb.org/3/movie/popular?api_key=1805007963a4760044640bde94121491&page=");
        searchmovie.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()) {
                    getalldata("https://api.themoviedb.org/3/movie/popular?api_key=1805007963a4760044640bde94121491&page=");
                }else{
                    getdata("https://api.themoviedb.org/3/search/movie?api_key=1805007963a4760044640bde94121491&query=" + newText);
                }
                return false;
            }
        });
        FAB.setOnClickListener(view1 -> {
            act.loadFragment(new addmovieFragment());
        });
        return view;
    }
    private void getalldata(String url) {
        movieApiAdapter.movies = new ArrayList<>();
        for (int i = 1; i <= 472; i++) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, (url+i), null, new Response.Listener<JSONObject>() {
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
                        Toast.makeText(requireContext(), w.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(requireContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            requestQueue.add(jsonObjectRequest);
        }
    }
    private void getdata(String url) {
            movieApiAdapter.movies = new ArrayList<>();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray results = (JSONArray) response.get("results");
                    for(int i=0;i<results.length();i++) {
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
                    Toast.makeText(requireContext(), w.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(requireContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}