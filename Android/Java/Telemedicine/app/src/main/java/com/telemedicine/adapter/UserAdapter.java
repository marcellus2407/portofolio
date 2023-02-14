package com.telemedicine.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.telemedicine.R;
import com.telemedicine.model.Dokter;
import com.telemedicine.model.User;
import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    List<User> users = new ArrayList<>();
    UserListener userListener = new UserListener() {
        @Override
        public void onLongCLickUser(int id) {}
        @Override
        public void onClickUser(int id) {}
    };
    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama, ket;
        public ViewHolder(View view) {
            super(view);
            nama = view.findViewById(R.id.tv_nama);
            ket = view.findViewById(R.id.tv_ket);
        }
    }
    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_user, viewGroup, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder viewHolder, final int position) {
        User user = users.get(position);
        viewHolder.nama.setText(user.nama);
        if(user instanceof Dokter){
            viewHolder.ket.setText(((Dokter)user).spesialis);
        }else{
            viewHolder.ket.setText(user.email);
        }
        viewHolder.itemView.setOnLongClickListener(view -> {
            userListener.onLongCLickUser(user.id);
            return false;
        });
        viewHolder.itemView.setOnClickListener(view -> {
            userListener.onClickUser(user.id);
        });
    }
    @Override
    public int getItemCount() {
        return users.size();
    }

    public interface UserListener {
        void onLongCLickUser(int id);
        void onClickUser(int id);
    }

    public void setUserListener(UserListener userListener){
        this.userListener=userListener;
    }
}
