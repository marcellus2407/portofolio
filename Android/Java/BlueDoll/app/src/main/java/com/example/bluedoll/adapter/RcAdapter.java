package com.example.bluedoll.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bluedoll.R;
import com.example.bluedoll.ViewDollsFragment;
import com.example.bluedoll.model.Doll;
import com.example.bluedoll.ViewDollsFragment;

import java.util.List;

public class RcAdapter extends RecyclerView.Adapter<RcAdapter.ViewHolder>{

    List<Doll> list;
    ViewDollsFragment a;
    Context ctx;
    public RcAdapter (ViewDollsFragment Fragment, Context context) {
        this.a=Fragment;
        this.ctx=context;
    }
    public void setList(List<Doll> list) {
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama, desc, creator;
        ImageView img;
        Button detail, edit, delete;
        public ViewHolder(View view) {
            super(view);
            nama = view.findViewById(R.id.tvName);
            desc = view.findViewById(R.id.tvDesc);
            creator = view.findViewById(R.id.tvcreator);
            img = view.findViewById(R.id.ivDoll);
            detail = view.findViewById(R.id.btnDetail);
            edit = view.findViewById(R.id.btnEdit);
            delete = view.findViewById(R.id.btnDelete);
        }
    }

    @NonNull
    @Override
    public RcAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclerview_value_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RcAdapter.ViewHolder viewHolder, final int position) {

        Doll doll=list.get(position);
        viewHolder.nama.setText(doll.getName());
        viewHolder.creator.setText(doll.getCreator());
        viewHolder.desc.setText(doll.getDescription());
        viewHolder.img.setImageResource(doll.getImage());
        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a.edit(doll);
            }
        });
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a.delete(doll);
            }
        });
        viewHolder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a.detail(doll);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}