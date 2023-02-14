package com.example.bluedoll;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.bluedoll.model.Doll;

public class DetailDollsFragment extends Fragment {
    Doll doll;
    TextView name, creator, desc;
    ImageView image;
    public DetailDollsFragment(Doll doll){
        this.doll=doll;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_dolls, container, false);
        name = view.findViewById(R.id.detailName);
        creator = view.findViewById(R.id.detailCreator);
        desc = view.findViewById(R.id.detailDesc);
        image = view.findViewById(R.id.detailImage);
        name.setText(doll.getName());
        creator.setText(doll.getCreator());
        desc.setText(doll.getDescription());
        image.setImageResource(doll.getImage());
        return view;
    }
}