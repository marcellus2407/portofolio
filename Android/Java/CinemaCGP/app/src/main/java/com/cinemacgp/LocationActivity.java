package com.cinemacgp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cinemacgp.databinding.ActivityLocationBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.tabs.TabLayout;

public class LocationActivity extends AppCompatActivity  implements OnMapReadyCallback {
    GoogleMap maps;
    LatLng latLngAlpha, latLngBeta;
    ActivityLocationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        latLngAlpha =  new LatLng(-6.193924061113853, 106.78813220277623);
        latLngBeta = new LatLng(- 6.20175020412279, 106.78223868546155);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fcv_map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        maps = googleMap;
        maps.addMarker(
                new MarkerOptions()
                        .position(latLngAlpha)
                        .title("Cinema CGP Alpha")
        );
        maps.addMarker(
                new MarkerOptions()
                        .position(latLngBeta)
                        .title("Cinema CGP Beta")
        );
        maps.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngAlpha,15f));
        binding.tlMain.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    maps.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngAlpha,15f));
                } else if (tab.getPosition() == 1) {
                    maps.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngBeta,15f));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }
}