package com.telemedicine.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.telemedicine.API;
import com.telemedicine.APIClient;
import com.telemedicine.R;
import com.telemedicine.Utils;
import com.telemedicine.adapter.ChatAdapter;
import com.telemedicine.model.ChatResponse;
import com.telemedicine.model.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    RecyclerView chat;
    EditText message;
    Button send;
    ProgressDialog progressDialog;
    API api;
    ChatAdapter chatAdapter;
    int sender, receiver;
    Runnable updater;
    Handler updateHandler;
    int n = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chat=findViewById(R.id.rv_chat);
        message=findViewById(R.id.et_message);
        send=findViewById(R.id.btn_send);
        chat.setLayoutManager(new LinearLayoutManager(this));
        progressDialog = Utils.PROGRESS(this);
        api = APIClient.getClient().create(API.class);
        SharedPreferences preferences= getSharedPreferences("auth", 0);
        sender = preferences.getInt("id",-1);
        receiver = getIntent().getExtras().getInt("receiver");
        chatAdapter = new ChatAdapter(sender);
        chat.setAdapter(chatAdapter);
        send.setOnClickListener(view -> sendMessage());
        retrieveMessage();
        updateMessage();
    }
    void updateMessage() {
        updateHandler = new Handler();
        updater = () -> {
            retrieveMessage();
            updateHandler.postDelayed(updater,1000);
        };
        updateHandler.post(updater);
    }
    private void sendMessage(){
        if(!message.getText().toString().isEmpty()){
            progressDialog.show();
            Call<ChatResponse> sendCall = api.sendMessage(sender,receiver,message.getText().toString());
            sendCall.enqueue(new Callback<ChatResponse>() {
                @Override
                public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                    if(response.isSuccessful()){
                        retrieveMessage();
                        message.setText(null);
                    }else{
                        Toast.makeText(ChatActivity.this,"Gagal Mengirim",Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                @Override
                public void onFailure(Call<ChatResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(ChatActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void retrieveMessage(){
        Call<List<Message>> retrieveCall = api.retrieveMessage(sender,receiver);
        retrieveCall.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                chatAdapter.setChat(response.body());
                    if(n<response.body().size()){
                        n=response.body().size();
                        chat.scrollToPosition(response.body().size()-1);
                    }
            }
            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Toast.makeText(ChatActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        updateHandler.removeCallbacks(updater);
    }
}