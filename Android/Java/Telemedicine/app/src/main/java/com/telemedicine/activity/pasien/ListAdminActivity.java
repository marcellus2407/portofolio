package com.telemedicine.activity.pasien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.telemedicine.API;
import com.telemedicine.APIClient;
import com.telemedicine.R;
import com.telemedicine.Utils;
import com.telemedicine.activity.ChatActivity;
import com.telemedicine.adapter.UserAdapter;
import com.telemedicine.model.Dokter;
import com.telemedicine.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListAdminActivity extends AppCompatActivity {
    RecyclerView admins;
    UserAdapter userAdapter;
    ProgressDialog progressDialog;
    API api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_admin);
        admins=findViewById(R.id.rv_admin);
        admins.setLayoutManager(new LinearLayoutManager(this));
        userAdapter=new UserAdapter();
        userAdapter.setUserListener(new UserAdapter.UserListener() {
            @Override
            public void onLongCLickUser(int id) {}

            @Override
            public void onClickUser(int id) {
                startActivity(new Intent(ListAdminActivity.this, ChatActivity.class).putExtra("receiver",id));
            }
        });
        admins.setAdapter(userAdapter);
        progressDialog = Utils.PROGRESS(this);
        api = APIClient.getClient().create(API.class);
        getAdmins();
    }
    private void getAdmins(){
        progressDialog.show();
        Call<List<User>> adminCall = api.getAdmins();
        adminCall.enqueue(new Callback<List<User>>() {
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
                Toast.makeText(ListAdminActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}