package com.cinemacgp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import com.cinemacgp.databinding.RowHistoryRentalBinding;

public class RentalHistoryAdapter extends CursorAdapter {
    RentalHistoryListener listener;
    public RentalHistoryAdapter(Context context, Cursor cursor, RentalHistoryListener listener) {
        super(context, cursor);
        this.listener = listener;
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return RowHistoryRentalBinding.inflate(LayoutInflater.from(context), parent, false).getRoot();
    }
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        RowHistoryRentalBinding binding = RowHistoryRentalBinding.bind(view);
        int idMovie = cursor.getInt(1);
        String location = cursor.getString(2);
        String studio = cursor.getString(3);
        String session = cursor.getString(4);
        String date = cursor.getString(5);

        binding.tvStudio.setText(location+" - "+studio);
        binding.tvData.setText("Movie ID : "+idMovie+"\n" +session+"\n"+date);
        view.setOnClickListener(view1 -> listener.onClick(idMovie));
    }
    public interface RentalHistoryListener {
        void onClick(int id);
    }
}
