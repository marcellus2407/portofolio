package com.tugas.kontrolkeuangan.View.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tugas.kontrolkeuangan.Database.Entity.Hutang;
import com.tugas.kontrolkeuangan.R;
import com.tugas.kontrolkeuangan.View.Fragment.Lend_Fragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerHutangAdapter extends RecyclerView.Adapter<RecyclerHutangAdapter.ViewHolder>{

    List<Hutang> list;
    Lend_Fragment a;
    Context ctx;
    public RecyclerHutangAdapter(Lend_Fragment Fragment, Context context) {
        this.a=Fragment;
        this.ctx=context;
    }
    public void setList(List<Hutang> list) {
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama, hutang, tanggal;
        public ViewHolder(View view) {
            super(view);
            nama = view.findViewById(R.id.TvNama);
            hutang = view.findViewById(R.id.TvHutang);
            tanggal = view.findViewById(R.id.TvTanggal);
        }
    }

    @NonNull
    @Override
    public RecyclerHutangAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_hutang, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerHutangAdapter.ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Hutang hutang=list.get(position);
        viewHolder.nama.setText(hutang.Nama);
        viewHolder.hutang.setText(hutang.Hutang);
        viewHolder.tanggal.setText(hutang.Tanggal);

        viewHolder.itemView.setOnTouchListener(new View.OnTouchListener() {
            private final GestureDetector gestureDetector = new GestureDetector(ctx, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    Log.d("TEST", "onDoubleTap");
                    a.set_Update_Data_Hutang(hutang);
                    return super.onDoubleTap(e);
                }
                @Override
                public void onLongPress(MotionEvent e) {
                    a.Delete_Data_Hutang(hutang);
                    Log.d("TEST", "onLongClick");
                }
            });
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
