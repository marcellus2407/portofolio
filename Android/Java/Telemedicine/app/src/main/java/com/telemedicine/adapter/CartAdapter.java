package com.telemedicine.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.telemedicine.R;
import com.telemedicine.model.Cart;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    List<Cart> carts = new ArrayList<>();
    Locale localeID = new Locale("in", "ID");
    CartListener cartListener = id -> {};

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
        notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView foto;
        TextView nama, harga, qty, error;
        Button remove;
        public ViewHolder(View view) {
            super(view);
            foto=view.findViewById(R.id.iv_obat);
            nama=view.findViewById(R.id.tv_nama);
            harga=view.findViewById(R.id.tv_harga);
            error=view.findViewById(R.id.tv_error);
            qty=view.findViewById(R.id.tv_qty);
            remove=view.findViewById(R.id.btn_remove);
        }
    }
    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_cart, viewGroup, false);
        return new CartAdapter.ViewHolder(view);
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(CartAdapter.ViewHolder viewHolder, final int position) {
        Cart cart = carts.get(position);
        Glide.with(viewHolder.itemView.getContext())
                .load(ContextCompat
                        .getDrawable(viewHolder.itemView.getContext(),
                                viewHolder.itemView.getContext()
                                        .getResources()
                                        .getIdentifier(cart.foto.substring(0, cart.foto.indexOf('.')),
                                                "drawable",
                                                viewHolder.itemView.getContext().getPackageName())))
                .into(viewHolder.foto);
        //Log.d("TAG", APIClient.BASE_URL+"obat/"+obat.foto);
        viewHolder.nama.setText(cart.nama);
        viewHolder.harga.setText(NumberFormat.getCurrencyInstance(localeID).format((double) cart.harga));
        viewHolder.qty.setText(String.valueOf(cart.quantity));
        viewHolder.remove.setOnClickListener(view -> {
            cartListener.onClickObat(cart.id);
        });
        if(cart.quantity>cart.stok){
            viewHolder.error.setVisibility(View.VISIBLE);
        }else{
            viewHolder.error.setVisibility(View.GONE);
        }
    }
    @Override
    public int getItemCount() {
        return carts.size();
    }

    public interface CartListener {
        void onClickObat(int id);
    }

    public void setCartListener(CartListener cartListener){
        this.cartListener = cartListener;
    }

}
