package com.moviefavorite;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MovieSQLiteAdapter extends CursorAdapter {
    FavActivity act;
    public MovieSQLiteAdapter(Context context, Cursor cursor, FavActivity act) {
        super(context, cursor);
        this.act = act;
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.row_movie, parent, false);
    }
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView mTitle = view.findViewById(R.id.movieTitleRow);
        ImageView  mImage = view.findViewById(R.id.movieImgRow);
        int id = cursor.getInt(1);
        String title = cursor.getString(2);
        String img = cursor.getString(3);
        Double avg = cursor.getDouble(4);
        String desc = cursor.getString(5);
        mTitle.setText(title);
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w185"+img)
                .into(mImage);
        view.setOnClickListener(view1 -> context.startActivity(new Intent(context,DetailActivity.class)
                .putExtra("img",img)
                .putExtra("title",title)
                .putExtra("avg", avg)
                .putExtra("desc", desc)
                .putExtra("fav","fav")));
        view.setOnLongClickListener(view1 -> {
            act.delete(id);
            return true;
        });
    }
}
