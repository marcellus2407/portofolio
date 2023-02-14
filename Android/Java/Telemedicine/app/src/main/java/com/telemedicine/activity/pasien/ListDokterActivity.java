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

public class ListDokterActivity extends AppCompatActivity {
    RecyclerView dokters;
    SearchView searchView;
    UserAdapter userAdapter;
    ProgressDialog progressDialog;
    API api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_dokter);
        dokters=findViewById(R.id.rv_dokter);
        searchView=findViewById(R.id.sv_dokter);
        dokters.setLayoutManager(new LinearLayoutManager(this));
        userAdapter=new UserAdapter();
        userAdapter.setUserListener(new UserAdapter.UserListener() {
            @Override
            public void onLongCLickUser(int id) {}
            @Override
            public void onClickUser(int id) {
                startActivity(new Intent(ListDokterActivity.this, ChatActivity.class).putExtra("receiver",id));
            }
        });
        dokters.setAdapter(userAdapter);
        progressDialog = Utils.PROGRESS(this);
        api = APIClient.getClient().create(API.class);
        getDokters();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchDokters(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()){
                    getDokters();
                }
                return false;
            }
        });
    }
    private void getDokters(){
        progressDialog.show();
        Call<List<Dokter>> doktersCall = api.getDokters();
        doktersCall.enqueue(new Callback<List<Dokter>>() {
            @Override
            public void onResponse(Call<List<Dokter>> call, Response<List<Dokter>> response) {
                if(response.body()!=null){
                    List<User> users = new ArrayList<>();
                    for(Dokter dokter : response.body()){
                        users.add(dokter);
                    }
                    userAdapter.setUsers(users);
                }
                progressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<List<Dokter>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ListDokterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void searchDokters(String keyword){
        progressDialog.show();
        Call<List<Dokter>> searchCall = api.searchDokters(keyword);
        searchCall.enqueue(new Callback<List<Dokter>>() {
            @Override
            public void onResponse(Call<List<Dokter>> call, Response<List<Dokter>> response) {
                if(response.body()!=null){
                    List<User> users = new ArrayList<>();
                    for(Dokter dokter : response.body()){
                        users.add(dokter);
                    }
                    userAdapter.setUsers(users);
                }
                progressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<List<Dokter>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ListDokterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}