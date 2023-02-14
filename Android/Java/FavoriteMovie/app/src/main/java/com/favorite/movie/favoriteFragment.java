package com.favorite.movie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.favorite.movie.MainActivity;
import com.favorite.movie.detailFragment;

public class favoriteFragment extends Fragment {
    MainActivity act;
    GridView all;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        act = (MainActivity) getActivity();
        all = view.findViewById(R.id.allFav);
        all.setAdapter(new MovieAdapter(requireContext(),act.databaseHelper.getallMovie(act.id),this));
        return view;
    }
    public void goDetail(String image, String title,double avg, String desc){
        act.loadFragment(new detailFragment(image,title,avg,desc));
    }
    public void delete(int id){
        if (act.databaseHelper.deleteMovie(id)){
            Toast.makeText(act, "Delete succsessfull",
                    Toast.LENGTH_LONG).show();
            all.setAdapter(new MovieAdapter(requireContext(),act.databaseHelper.getallMovie(act.id),this));
        }else{
            Toast.makeText(act, "delete failed",
                    Toast.LENGTH_LONG).show();
        }
    }
}