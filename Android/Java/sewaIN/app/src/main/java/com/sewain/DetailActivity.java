package com.sewain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.NumberFormat;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnCameraMoveStartedListener{
    ImageView img;
    GoogleMap maps;
    double lat, lng;
    LatLng latLng;
    String namadt, deskripsidt;
    int lbdt,ltdt,ktdt,kmdt,listrikdt,lantaidt,hargadt;
    DatabaseHelper databaseHelper;
    TextView nama,lb,lt,kt,km,listrik,lantai,harga,deskripsi;
    ScrollView sv;
    byte[] imgdata;
    Locale localeID = new Locale("in", "ID");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        sv = findViewById(R.id.svDetail);
        nama = findViewById(R.id.namaDetail);
        img = findViewById(R.id.imgDetail);
        lb=findViewById(R.id.lbDetail);
        lt=findViewById(R.id.ltDetail);
        kt=findViewById(R.id.ktDetail);
        km=findViewById(R.id.kmDetail);
        listrik=findViewById(R.id.listrikDetail);
        lantai=findViewById(R.id.lantaiDetail);
        harga=findViewById(R.id.hargaDetail);
        deskripsi=findViewById(R.id.deskripsiDetail);

        Bundle b = getIntent().getExtras();
        int id = b.getInt("id");

        databaseHelper = new DatabaseHelper(this);
        Cursor csr = databaseHelper.getItem(id);
        csr.moveToFirst ();

        namadt = csr.getString(2);
        imgdata = csr.getBlob(3);
        lbdt = csr.getInt(4);
        ltdt = csr.getInt(5);
        ktdt = csr.getInt(6);
        kmdt = csr.getInt(7);
        listrikdt = csr.getInt(8);
        lantaidt = csr.getInt(9);
        hargadt = csr.getInt(10);
        deskripsidt = csr.getString(11);
        lat = csr.getDouble(12);
        lng = csr.getDouble(13);
        latLng = new LatLng(lat,lng);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        nama.setText(namadt);
        img.setImageBitmap(BitmapFactory.decodeByteArray(imgdata, 0, imgdata.length));
        lb.setText(String.valueOf(lbdt));
        lt.setText(String.valueOf(ltdt));
        kt.setText(String.valueOf(ktdt));
        km.setText(String.valueOf(kmdt));
        listrik.setText(String.valueOf(listrikdt));
        lantai.setText(String.valueOf(lantaidt));
        harga.setText(NumberFormat.getCurrencyInstance(localeID).format((double)hargadt));
        deskripsi.setText(deskripsidt);

    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        maps = googleMap;
        maps.addMarker(
                new MarkerOptions()
                        .position(latLng)
                        .title(namadt)
        );
        maps.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15f));
        maps.setOnCameraMoveStartedListener(this);
    }

    @Override
    public void onCameraMoveStarted(int reason) {
        if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
            sv.requestDisallowInterceptTouchEvent(true);

        } else if (reason == GoogleMap.OnCameraMoveStartedListener
                .REASON_API_ANIMATION) {
            sv.requestDisallowInterceptTouchEvent(true);

        } else if (reason == GoogleMap.OnCameraMoveStartedListener
                .REASON_DEVELOPER_ANIMATION) {
            sv.requestDisallowInterceptTouchEvent(true);
        }
    }
}