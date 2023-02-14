package com.sewain;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemAdapter extends CursorAdapter {
    public ItemAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.row_item, parent, false);
    }
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nama = view.findViewById(R.id.namaRow);
        ImageView img = view.findViewById(R.id.imgRow);

        int id = cursor.getInt(1);
        String namadata = cursor.getString(2);
        byte[] imgdata = cursor.getBlob(3);
        Log.d("TAG", namadata);
        nama.setText(namadata);
        img.setImageBitmap(BitmapFactory.decodeByteArray(imgdata, 0, imgdata.length));
        view.setOnClickListener(view1 -> {
            context.startActivity(new Intent(context,DetailActivity.class).putExtra("id",id));
        });
    }
}
