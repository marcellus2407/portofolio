package com.favorite.movie;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.favorite.movie.favoriteFragment;

public class MovieAdapter extends CursorAdapter {
    favoriteFragment frg;
    public MovieAdapter(Context context, Cursor cursor, favoriteFragment frg) {
        super(context, cursor);
        this.frg = frg;
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.grid_movie, parent, false);
    }
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView title = view.findViewById(R.id.titleGrid);
        ImageView image = view.findViewById(R.id.imageGrid);
        int id = cursor.getInt(1);
        String titledata = cursor.getString(3);
        String imagedata = cursor.getString(4);
        double avg = cursor.getDouble(5);
        String desc = cursor.getString(6);
        title.setText(titledata);
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w185"+imagedata)
                .into(image);
        view.setOnClickListener(view1 -> {
            frg.goDetail(imagedata,titledata,avg,desc);
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                frg.delete(id);
                return true;
            }
        });
    }
}
