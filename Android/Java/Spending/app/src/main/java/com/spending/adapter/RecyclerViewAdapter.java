package com.spending.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spending.R;
import com.spending.activity.MainActivity;
import com.spending.model.Spending;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    Locale localeID = new Locale("in", "ID");
    public ArrayList<Spending> spendings = new ArrayList<>();
    MainActivity mainActivity;
    public RecyclerViewAdapter(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.spending_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder viewHolder, final int position) {
        Spending spending = spendings.get(position);
        viewHolder.title.setText(spending.title);
        viewHolder.nominal.setText(NumberFormat.getCurrencyInstance(localeID).format((double)spending.nominal));
        viewHolder.category.setImageResource(spending.category);
        viewHolder.itemView.setOnLongClickListener(view -> {
            if(viewHolder.delete.getVisibility()==View.GONE) {
                viewHolder.delete.setVisibility(View.VISIBLE);
            }else{
                viewHolder.delete.setVisibility(View.GONE);
            }
            return true;
        });
        viewHolder.delete.setOnClickListener(view -> {
            mainActivity.delete_spending(position);
            viewHolder.delete.setVisibility(View.GONE);
        });
        viewHolder.itemView.setOnClickListener(view -> mainActivity.edit_spending(position));
    }

    @Override
    public int getItemCount() {
        return spendings.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, nominal;
        ImageView category;
        Button delete;
        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            nominal = view.findViewById(R.id.nominal);
            category = view.findViewById(R.id.category);
            delete = view.findViewById(R.id.delete);
        }
    }

}
