package com.favorite.movie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

public class detailFragment extends Fragment {
    String image, title, desc;
    double avg;
    detailFragment(String image, String title,double avg, String desc){
        this.image = image;
        this.title = title;
        this.avg = avg;
        this.desc = desc;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ImageView imageview = view.findViewById(R.id.imageDetail);
        TextView titleview  = view.findViewById(R.id.titleDetail);
        TextView avgview  = view.findViewById(R.id.voteDetail);
        TextView descview  = view.findViewById(R.id.descDetail);
        titleview .setText(this.title);
        avgview .setText("Vote Average : "+this.avg);
        descview .setText(this.desc);
        Glide.with(requireContext())
                .load("https://image.tmdb.org/t/p/w500"+this.image)
                .into(imageview);
        return view;
    }
}