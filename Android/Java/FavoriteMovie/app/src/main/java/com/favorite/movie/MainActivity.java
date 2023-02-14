package com.favorite.movie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawer;
    NavigationView navigationView;
    int id;
    public DatabaseHelper databaseHelper;
    public Cursor allmobil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        View header=navigationView.getHeaderView(0);
        TextView namaheader = header.findViewById(R.id.namaheader);
        TextView emailheader = header.findViewById(R.id.emailheader);
        databaseHelper = new DatabaseHelper(this);
        SharedPreferences prefs = getSharedPreferences("Mypref", MODE_PRIVATE);
        id=prefs.getInt("id", -1);
        User user = databaseHelper.getUser(id);
        namaheader.setText(user.username);
        emailheader.setText(user.email);
        loadFragment(new allmovieFragment());
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.all:
                        loadFragment(new allmovieFragment());
                        break;
                    case R.id.fav:
                        loadFragment(new favoriteFragment());
                        break;
                    case R.id.logout:
                        SharedPreferences preferences = getSharedPreferences("Mypref", 0);
                        preferences.edit().remove("id").commit();
                        startActivity(new Intent(getBaseContext(), LoginActivity.class));
                        finish();
                }
                return true;
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_open_drawer,R.string.navigation_close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
    public boolean loadFragment(Fragment selectedFragment) {
        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            if (! getSupportFragmentManager().isDestroyed())
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
        return false;
    }
}