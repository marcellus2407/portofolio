package com.telemedicine.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.telemedicine.R;
import com.telemedicine.activity.pasien.MapActivity;

public class RsAdapter extends CursorAdapter {
    public RsAdapter (Context context, Cursor cursor) {
        super(context, cursor);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.row_rumah_sakit, parent, false);
    }
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        CardView rs = view.findViewById(R.id.cv_rs);
        TextView nama = view.findViewById(R.id.tv_nama);
        String namaData = cursor.getString(2);
        double lat = cursor.getDouble(3);
        double lng = cursor.getDouble(4);
        nama.setText(namaData);
        rs.setOnClickListener(view1 -> {
            context.startActivity(new Intent(context, MapActivity.class)
                    .putExtra("nama",namaData)
                    .putExtra("lat",lat)
                    .putExtra("lng",lng)
            );
        });
    }
}
