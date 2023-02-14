package com.telemedicine.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.telemedicine.R;
import com.telemedicine.model.Dokter;
import com.telemedicine.model.Message;
import com.telemedicine.model.User;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    List<Message> messages = new ArrayList<>();
    int idSender;

    public ChatAdapter(int idSender) {
        this.idSender = idSender;
    }

    ChatListener chatListener = new ChatListener() {
        @Override
        public void onLongCLickMessage(int id) {}
        @Override
        public void onClickMessage(int id) {}
    };
    public void setChat(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView sender, receiver;
        TextView senderMessage, receiverMessage;
        public ViewHolder(View view) {
            super(view);
            sender = view.findViewById(R.id.cv_sender);
            receiver = view.findViewById(R.id.cv_receiver);
            senderMessage = view.findViewById(R.id.tv_sender);
            receiverMessage = view.findViewById(R.id.tv_receiver);
        }
    }
    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_message, viewGroup, false);
        return new ChatAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ChatAdapter.ViewHolder viewHolder, final int position) {
        Message message = messages.get(position);
        if(message.sender==idSender){
            viewHolder.senderMessage.setText(message.message);
            viewHolder.receiver.setVisibility(View.GONE);
            viewHolder.sender.setVisibility(View.VISIBLE);
        }else{
            viewHolder.receiverMessage.setText(message.message);
            viewHolder.receiver.setVisibility(View.VISIBLE);
            viewHolder.sender.setVisibility(View.GONE);
        }
        viewHolder.itemView.setOnLongClickListener(view -> {
            chatListener.onLongCLickMessage(message.id);
            return false;
        });
        viewHolder.itemView.setOnClickListener(view -> {
            chatListener.onClickMessage(message.id);
        });
    }
    @Override
    public int getItemCount() {
        return messages.size();
    }

    public interface ChatListener {
        void onLongCLickMessage(int id);
        void onClickMessage(int id);
    }

    public void setChatListener(ChatListener chatListener){
        this.chatListener=chatListener;
    }
}
