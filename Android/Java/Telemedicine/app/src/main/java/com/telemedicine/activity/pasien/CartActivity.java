package com.telemedicine.activity.pasien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.telemedicine.API;
import com.telemedicine.APIClient;
import com.telemedicine.R;
import com.telemedicine.Utils;
import com.telemedicine.adapter.CartAdapter;
import com.telemedicine.model.AddToCartResponse;
import com.telemedicine.model.Cart;
import com.telemedicine.model.CheckoutResponse;
import com.telemedicine.model.Order;
import com.telemedicine.model.OrderDetail;
import com.telemedicine.model.RemoveFromCartResponse;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {
    RecyclerView carts;
    CartAdapter cartAdapter;
    ProgressDialog progressDialog;
    TextInputLayout tilAlamat;
    EditText alamat;
    API api;
    int user;
    TextView total;
    Button checkout;
    Locale localeID = new Locale("in", "ID");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        carts =findViewById(R.id.rv_cart);
        total=findViewById(R.id.tv_total);
        checkout=findViewById(R.id.btn_checkout);
        tilAlamat=findViewById(R.id.til_alamat);
        alamat=findViewById(R.id.et_alamat);
        SharedPreferences preferences= getSharedPreferences("auth", 0);
        user = preferences.getInt("id",-1);
        carts.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter =new CartAdapter();
        cartAdapter.setCartListener(id -> new AlertDialog.Builder(this)
                .setTitle("Peringatan")
                .setMessage("Yakin ingin menghapus data ini?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, (dialog1, whichButton) -> {
                    removeFromCart(id);
                })
                .setNegativeButton(android.R.string.no, null).show());
        carts.setAdapter(cartAdapter);
        progressDialog = Utils.PROGRESS(this);
        api = APIClient.getClient().create(API.class);
        getCarts();
    }
    private void getCarts(){
        progressDialog.show();
        Call<List<Cart>> cartCall = api.getCart(user);
        cartCall.enqueue(new Callback<List<Cart>>() {
            @Override
            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                int total=0;
                checkout.setOnClickListener(view -> {
                    if(alamat.getText().toString().isEmpty()){
                        tilAlamat.setError("Isi Alamat");
                    }else{
                        checkout();
                    }
                });
                for(Cart cart:response.body()){
                    total+=cart.harga*cart.quantity;
                    if(cart.stok<cart.quantity){
                        checkout.setOnClickListener(view -> {
                            Toast.makeText(CartActivity.this,"Hapus yang stoknya tidak mencukupi",Toast.LENGTH_SHORT).show();
                        });
                    }
                }
                if(response.body().isEmpty()){
                    checkout.setOnClickListener(view -> {
                        Toast.makeText(CartActivity.this,"Keranjang masih kosong",Toast.LENGTH_SHORT).show();
                    });
                }
                CartActivity.this.total.setText(NumberFormat.getCurrencyInstance(localeID).format((double) total));
                cartAdapter.setCarts(response.body());
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CartActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void removeFromCart(int id){
        progressDialog.show();
        Call<RemoveFromCartResponse> removeFromCartCall = api.removeFromCart(user,id);
        removeFromCartCall.enqueue(new Callback<RemoveFromCartResponse>() {
            @Override
            public void onResponse(Call<RemoveFromCartResponse> call, Response<RemoveFromCartResponse> response) {
                if(response.body().response){
                    Toast.makeText(CartActivity.this,"Berhasil Menghapus",Toast.LENGTH_SHORT).show();
                    getCarts();
                }else{
                    Toast.makeText(CartActivity.this,"Gagal Menghapus",Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<RemoveFromCartResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CartActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void checkout(){
        progressDialog.show();
        Call<CheckoutResponse> checkoutCall = api.checkout(user,alamat.getText().toString());
        checkoutCall.enqueue(new Callback<CheckoutResponse>() {
            @Override
            public void onResponse(Call<CheckoutResponse> call, Response<CheckoutResponse> response) {
                if(response.body().response) {
                    Toast.makeText(CartActivity.this, "Berhasil checkout", Toast.LENGTH_SHORT).show();
                    getCarts();
                    alamat.setText(null);
                    showNotification("CHECKOUT","Nomor Order "+response.body().order.id,response.body().order);
                }else{
                    Toast.makeText(CartActivity.this, "Gagal checkout", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<CheckoutResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CartActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    void showNotification(String title, String message, Order order) {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("YOUR_CHANNEL_ID",
                    "YOUR_CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DESCRIPTION");
            mNotificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "YOUR_CHANNEL_ID")
                .setSmallIcon(R.drawable.ic_baseline_notifications_24) // notification icon
                .setContentTitle(title) // title for notification
                .setContentText(message)// message for notification
                .setAutoCancel(true); // clear notification after click
        Intent intent = new Intent(this, DetailOrderActivity.class)
                .putExtra("id", order.id)
                .putExtra("alamat",order.alamat)
                .putExtra("waktu",order.waktu);
        PendingIntent pi = PendingIntent.getActivity(CartActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);
        mNotificationManager.notify(0, mBuilder.build());
    }
}