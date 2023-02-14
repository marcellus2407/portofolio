package com.telemedicine.adapter;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ViewTarget;
import com.telemedicine.API;
import com.telemedicine.APIClient;
import com.telemedicine.R;
import com.telemedicine.model.Dokter;
import com.telemedicine.model.Obat;
import com.telemedicine.model.User;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ObatAdapter extends RecyclerView.Adapter<ObatAdapter.ViewHolder> {
    List<Obat> obats = new ArrayList<>();
    Locale localeID = new Locale("in", "ID");
    ObatListener obatListener = (id,qty) -> {};

    public void setObats(List<Obat> obats) {
        this.obats = obats;
        notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView foto;
        TextView nama, harga, deskripsi, stok, qty;
        Button add;
        ImageButton plus,minus;
        public ViewHolder(View view) {
            super(view);
            foto=view.findViewById(R.id.iv_obat);
            nama=view.findViewById(R.id.tv_nama);
            harga=view.findViewById(R.id.tv_harga);
            deskripsi=view.findViewById(R.id.tv_deskripsi);
            stok=view.findViewById(R.id.tv_stok);
            qty=view.findViewById(R.id.tv_qty);
            add=view.findViewById(R.id.btn_add);
            plus=view.findViewById(R.id.btn_plus);
            minus=view.findViewById(R.id.btn_minus);
        }
    }
    @NonNull
    @Override
    public ObatAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_obat, viewGroup, false);
        return new ObatAdapter.ViewHolder(view);
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(ObatAdapter.ViewHolder viewHolder, final int position) {
        Obat obat = obats.get(position);
        Glide.with(viewHolder.itemView.getContext())
                .load(ContextCompat
                        .getDrawable(viewHolder.itemView.getContext(),
                                viewHolder.itemView.getContext()
                                        .getResources()
                                        .getIdentifier(obat.foto.substring(0,obat.foto.indexOf('.')),
                                                "drawable",
                                                viewHolder.itemView.getContext().getPackageName())))
                .into(viewHolder.foto);
        //Log.d("TAG", APIClient.BASE_URL+"obat/"+obat.foto);
        viewHolder.nama.setText(obat.nama);
        viewHolder.harga.setText(NumberFormat.getCurrencyInstance(localeID).format((double)obat.harga));
        viewHolder.stok.setText("Stok : "+obat.stok);
        viewHolder.deskripsi.setText(obat.deskripsi);
        final int[] qty = {1};
        viewHolder.minus.setOnClickListener(view -> {
            if(qty[0] >1){
                qty[0] -=1;
                viewHolder.qty.setText(String.valueOf(qty[0]));
            }
        });
        viewHolder.plus.setOnClickListener(view -> {
            if(qty[0] < obat.stok){
                qty[0] +=1;
                viewHolder.qty.setText(String.valueOf(qty[0]));
            }
        });

        viewHolder.add.setOnClickListener(view -> {
            obatListener.onClickObat(obat.id, qty[0]);
            qty[0]=1;
            viewHolder.qty.setText(String.valueOf(qty[0]));
        });
    }
    @Override
    public int getItemCount() {
        return obats.size();
    }

    public interface ObatListener {
        void onClickObat(int id, int qty);
    }

    public void setObatListener(ObatListener obatListener){
        this.obatListener=obatListener;
    }

}
