package com.telemedicine.activity.pasien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.telemedicine.API;
import com.telemedicine.APIClient;
import com.telemedicine.R;
import com.telemedicine.Utils;
import com.telemedicine.activity.auth.LoginActivity;
import com.telemedicine.adapter.CartAdapter;
import com.telemedicine.adapter.OrderDetailAdapter;
import com.telemedicine.model.OrderDetail;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailOrderActivity extends AppCompatActivity {
    RecyclerView orderDetail;
    OrderDetailAdapter orderDetailAdapter;
    ProgressDialog progressDialog;
    API api;
    TextView id, alamat, waktu,total;
    Locale localeID = new Locale("in", "ID");
    int idData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        SharedPreferences preferences= getSharedPreferences("auth", 0);
        if(preferences.getInt("id",-1)==-1){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        orderDetail =findViewById(R.id.rv_order_detail);
        total=findViewById(R.id.tv_total);
        id=findViewById(R.id.tv_id);
        alamat=findViewById(R.id.tv_alamat);
        waktu=findViewById(R.id.tv_waktu);
        Bundle b = getIntent().getExtras();
        idData=b.getInt("id");
        id.setText(String.valueOf(idData));
        alamat.setText(b.getString("alamat"));
        waktu.setText(b.getString("waktu"));
        orderDetail.setLayoutManager(new LinearLayoutManager(this));
        orderDetailAdapter =new OrderDetailAdapter();
        orderDetail.setAdapter(orderDetailAdapter);
        progressDialog = Utils.PROGRESS(this);
        api = APIClient.getClient().create(API.class);
        getOrderDetails();
    }
    private void getOrderDetails(){
        progressDialog.show();
        Call<List<OrderDetail>> orderDetailsCall = api.getOrderDetails(idData);
        orderDetailsCall.enqueue(new Callback<List<OrderDetail>>() {
            @Override
            public void onResponse(Call<List<OrderDetail>> call, Response<List<OrderDetail>> response) {
                int total=0;
                for(OrderDetail orderDetail :response.body()){
                    total+= orderDetail.harga* orderDetail.quantity;
                }
                DetailOrderActivity.this.total.setText(NumberFormat.getCurrencyInstance(localeID).format((double) total));
                orderDetailAdapter.setOrderDetails(response.body());
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<OrderDetail>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DetailOrderActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}