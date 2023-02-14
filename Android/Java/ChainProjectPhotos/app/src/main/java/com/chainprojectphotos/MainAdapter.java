package com.chainprojectphotos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>{

    ArrayList<Tempat> tempats;
    MainActivity act;
    public MainAdapter(MainActivity act, ArrayList<Tempat> tempats) {
        this.act = act;
        this.tempats=tempats;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView img;
        Button loc;
        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.nameRowMain);
            img = view.findViewById(R.id.imgRowMain);
            loc = view.findViewById(R.id.locRowMain);
        }
    }
    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_main, viewGroup, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(MainAdapter.ViewHolder viewHolder, final int position) {
        Tempat tempat=tempats.get(position);
        viewHolder.name.setText(tempat.name);
        Glide.with(act.getBaseContext())
                .load(tempat.imageurl)
                .into(viewHolder.img);
        viewHolder.loc.setOnClickListener(view -> {
            act.gotoLoc(position);
        });
    }
    @Override
    public int getItemCount() {
        return tempats.size();
    }

}
