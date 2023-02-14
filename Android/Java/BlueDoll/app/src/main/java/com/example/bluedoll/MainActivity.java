package com.example.bluedoll;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.bluedoll.model.Doll;
import com.example.bluedoll.model.User;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawer;
    NavigationView navigationView;
    User creator;
    public ArrayList<User> users;
    public ArrayList<Doll> dolls = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extra = getIntent().getBundleExtra("extra");
        users = (ArrayList<User>) extra.getSerializable("users");
        creator = users.get(extra.getInt("idx_user"));
        dolls = (ArrayList<Doll>) extra.getSerializable("dolls");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        loadFragment(new ViewDollsFragment());
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.view_doll:
                        loadFragment(new ViewDollsFragment());
                        break;
                    case R.id.insert_doll:
                        loadFragment(new InsertDollsFragment());
                        break;
                    case R.id.logout:
                        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                        Bundle extra = new Bundle();
                        extra.putSerializable("users", users);
                        extra.putSerializable("dolls", dolls);
                        intent.putExtra("extra", extra);
                        startActivity(intent);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.kebab_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.our_location:
                loadFragment(new AboutUsFragment());
                break;
        }
        return super.onOptionsItemSelected(item);
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