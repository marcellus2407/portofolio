package com.telemedicine.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.telemedicine.R;
import com.telemedicine.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter  extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    List<Order> orders = new ArrayList<>();
    OrderListener orderListener = (id, alamat, waktu) -> {

    };
    public void setOrders(List<Order> orders) {
        this.orders = orders;
        notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView alamat, waktu, id;
        public ViewHolder(View view) {
            super(view);
            alamat = view.findViewById(R.id.tv_alamat);
            waktu = view.findViewById(R.id.tv_waktu);
            id = view.findViewById(R.id.tv_id);
        }
    }
    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_order, viewGroup, false);
        return new OrderAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(OrderAdapter.ViewHolder viewHolder, final int position) {
        Order order = orders.get(position);
        viewHolder.id.setText(String.valueOf(order.id));
        viewHolder.alamat.setText(order.alamat);
        viewHolder.waktu.setText(order.waktu);
        viewHolder.itemView.setOnClickListener(view -> {
            orderListener.onClickOrder(order.id, order.alamat, order.waktu);
        });
    }
    @Override
    public int getItemCount() {
        return orders.size();
    }

    public interface OrderListener {
        void onClickOrder(int id, String alamat, String waktu);
    }

    public void setOrderListener(OrderListener orderListener){
        this.orderListener =orderListener;
    }
}
