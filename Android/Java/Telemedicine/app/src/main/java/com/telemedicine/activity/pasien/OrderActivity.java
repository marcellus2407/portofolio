package com.telemedicine.activity.pasien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.telemedicine.API;
import com.telemedicine.APIClient;
import com.telemedicine.R;
import com.telemedicine.Utils;
import com.telemedicine.adapter.CartAdapter;
import com.telemedicine.adapter.OrderAdapter;
import com.telemedicine.model.Cart;
import com.telemedicine.model.Order;

import java.text.NumberFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {
    RecyclerView carts;
    OrderAdapter cartAdapter;
    ProgressDialog progressDialog;
    API api;
    int user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.telemedicine.R.layout.activity_order);
        carts =findViewById(R.id.rv_order);
        SharedPreferences preferences= getSharedPreferences("auth", 0);
        user = preferences.getInt("id",-1);
        carts.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter =new OrderAdapter();
        cartAdapter.setOrderListener((id, alamat, waktu) -> {
            startActivity(new Intent(this, DetailOrderActivity.class)
                    .putExtra("id",id)
                    .putExtra("alamat",alamat)
                    .putExtra("waktu",waktu)
            );
        });
        carts.setAdapter(cartAdapter);
        progressDialog = Utils.PROGRESS(this);
        api = APIClient.getClient().create(API.class);
        getCarts();
    }
    private void getCarts(){
        progressDialog.show();
        Call<List<Order>> orderCall = api.getOrders(user);
        orderCall.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                cartAdapter.setOrders(response.body());
                progressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(OrderActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}