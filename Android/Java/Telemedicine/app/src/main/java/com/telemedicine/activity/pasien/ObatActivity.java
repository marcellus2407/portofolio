package com.telemedicine.activity.pasien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.telemedicine.API;
import com.telemedicine.APIClient;
import com.telemedicine.R;
import com.telemedicine.Utils;
import com.telemedicine.activity.ChatActivity;
import com.telemedicine.adapter.ObatAdapter;
import com.telemedicine.model.AddToCartResponse;
import com.telemedicine.model.ChatResponse;
import com.telemedicine.model.Obat;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ObatActivity extends AppCompatActivity {
    RecyclerView obats;
    SearchView searchView;
    ObatAdapter obatAdapter;
    ProgressDialog progressDialog;
    API api;
    int user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obat);
        obats =findViewById(R.id.rv_obat);
        searchView=findViewById(R.id.sv_obat);
        SharedPreferences preferences= getSharedPreferences("auth", 0);
        user = preferences.getInt("id",-1);
        obats.setLayoutManager(new LinearLayoutManager(this));
        obatAdapter =new ObatAdapter();
        obatAdapter.setObatListener((id,qty) -> {
            addToCart(id,qty);
        });
        obats.setAdapter(obatAdapter);
        progressDialog = Utils.PROGRESS(this);
        api = APIClient.getClient().create(API.class);
        getObats();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchObats(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()){
                    getObats();
                }
                return false;
            }
        });
    }
    private void getObats(){
        progressDialog.show();
        Call<List<Obat>> obatCall = api.getObats();
        obatCall.enqueue(new Callback<List<Obat>>() {
            @Override
            public void onResponse(Call<List<Obat>> call, Response<List<Obat>> response) {
                obatAdapter.setObats(response.body());
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Obat>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ObatActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void searchObats(String keyword){
        progressDialog.show();
        Call<List<Obat>> searchCall = api.searchObats(keyword);
        searchCall.enqueue(new Callback<List<Obat>>() {
            @Override
            public void onResponse(Call<List<Obat>> call, Response<List<Obat>> response) {
                obatAdapter.setObats(response.body());
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Obat>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ObatActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addToCart(int id, int qty){
        progressDialog.show();
        Call<AddToCartResponse> addToCartCall = api.addToCart(user,id,qty);
        addToCartCall.enqueue(new Callback<AddToCartResponse>() {
            @Override
            public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {
                progressDialog.dismiss();
                Toast.makeText(ObatActivity.this, response.body().response, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<AddToCartResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ObatActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}