package com.telemedicine.activity.pasien;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.telemedicine.R;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap maps;
    LatLng latLng;
    TextView nama;
    String namaData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        nama=findViewById(R.id.tv_nama);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Bundle b = getIntent().getExtras();
        namaData = b.getString("nama");
        nama.setText(namaData);
        latLng = new LatLng(b.getDouble("lat"),b.getDouble("lng"));
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        maps = googleMap;
        maps.addMarker(
                new MarkerOptions()
                        .position(latLng)
                        .title(namaData)
        );
        maps.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15f));
    }
}