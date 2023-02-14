package com.telemedicine.activity.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.telemedicine.API;
import com.telemedicine.APIClient;
import com.telemedicine.R;
import com.telemedicine.Utils;
import com.telemedicine.activity.ChatActivity;
import com.telemedicine.activity.auth.LoginActivity;
import com.telemedicine.activity.dokter.DokterActivity;
import com.telemedicine.adapter.UserAdapter;
import com.telemedicine.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminActivity extends AppCompatActivity {
    RecyclerView chats;
    UserAdapter userAdapter;
    ProgressDialog progressDialog;
    API api;
    int id;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        chats=findViewById(R.id.rv_chat);
        chats.setLayoutManager(new LinearLayoutManager(this));
        userAdapter=new UserAdapter();
        userAdapter.setUserListener(new UserAdapter.UserListener() {
            @Override
            public void onLongCLickUser(int id) {}

            @Override
            public void onClickUser(int id) {
                startActivity(new Intent(AdminActivity.this, ChatActivity.class).putExtra("receiver",id));
            }
        });
        chats.setAdapter(userAdapter);
        progressDialog = Utils.PROGRESS(this);
        api = APIClient.getClient().create(API.class);
        preferences= getSharedPreferences("auth", 0);
        id = preferences.getInt("id",-1);
        getChat();
    }
    private void getChat(){
        progressDialog.show();
        Call<List<User>> chatCall = api.getUserChat(id);
        chatCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.body()!=null){
                    userAdapter.setUsers(response.body());
                }
                progressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(AdminActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                preferences.edit().remove("id").commit();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}