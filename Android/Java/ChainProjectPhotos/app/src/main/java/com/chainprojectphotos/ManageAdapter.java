package com.chainprojectphotos;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ManageAdapter extends RecyclerView.Adapter<ManageAdapter.ViewHolder>{

    ArrayList<Tempat> tempats;
    ManagePhotoActivity act;
    public ManageAdapter(ManagePhotoActivity act, ArrayList<Tempat> tempats) {
        this.act = act;
        this.tempats=tempats;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView img;
        ImageButton edit,delete;
        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.nameRowManage);
            img = view.findViewById(R.id.imgRowManage);
            edit = view.findViewById(R.id.edRowManage);
            delete = view.findViewById(R.id.delRowManage);
        }
    }
    @NonNull
    @Override
    public ManageAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_manage, viewGroup, false);
        return new ManageAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ManageAdapter.ViewHolder viewHolder, final int position) {
        Tempat tempat=tempats.get(position);
        viewHolder.name.setText(tempat.name);
        Glide.with(act.getBaseContext())
                .load(tempat.imageurl)
                .into(viewHolder.img);
        viewHolder.edit.setOnClickListener(view -> {
            act.edit(position);
        });
        viewHolder.delete.setOnClickListener(view -> {
            act.delete(position);
        });
    }
    @Override
    public int getItemCount() {
        return tempats.size();
    }

}
