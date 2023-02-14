package com.chainprojectphotos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap maps;
    LatLng latLng;
    ArrayList<Tempat> tempats;
    String name;
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        if(getIntent().getBundleExtra("extra")!=null){
            Bundle extra = getIntent().getBundleExtra("extra");
            tempats = (ArrayList<Tempat>) extra.getSerializable("tempats");
            pos = extra.getInt("pos");

        }
        latLng = new LatLng(tempats.get(pos).lat,tempats.get(pos).lng);
        name = tempats.get(pos).name;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        maps = googleMap;
        maps.addMarker(
                new MarkerOptions()
                        .position(latLng)
                        .title(name)
        );
        maps.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15f));
    }
}