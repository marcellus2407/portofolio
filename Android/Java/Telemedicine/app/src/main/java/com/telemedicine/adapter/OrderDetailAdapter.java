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
import com.telemedicine.model.Order;
import com.telemedicine.model.OrderDetail;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {
    List<OrderDetail> orderDetails = new ArrayList<>();
    Locale localeID = new Locale("in", "ID");
    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
        notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView foto;
        TextView nama, harga, qty;
        public ViewHolder(View view) {
            super(view);
            foto=view.findViewById(R.id.iv_obat);
            nama=view.findViewById(R.id.tv_nama);
            harga=view.findViewById(R.id.tv_harga);
            qty=view.findViewById(R.id.tv_qty);
        }
    }
    @NonNull
    @Override
    public OrderDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_order_detail, viewGroup, false);
        return new OrderDetailAdapter.ViewHolder(view);
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(OrderDetailAdapter.ViewHolder viewHolder, final int position) {
        OrderDetail orderDetail = orderDetails.get(position);
        Glide.with(viewHolder.itemView.getContext())
                .load(ContextCompat
                        .getDrawable(viewHolder.itemView.getContext(),
                                viewHolder.itemView.getContext()
                                        .getResources()
                                        .getIdentifier(orderDetail.foto.substring(0, orderDetail.foto.indexOf('.')),
                                                "drawable",
                                                viewHolder.itemView.getContext().getPackageName())))
                .into(viewHolder.foto);
        //Log.d("TAG", APIClient.BASE_URL+"obat/"+obat.foto);
        viewHolder.nama.setText(orderDetail.nama);
        viewHolder.harga.setText(NumberFormat.getCurrencyInstance(localeID).format((double) orderDetail.harga));
        viewHolder.qty.setText("Total : "+orderDetail.quantity);
    }
    @Override
    public int getItemCount() {
        return orderDetails.size();
    }
}
