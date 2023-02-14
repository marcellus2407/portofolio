package com.example.bluedoll;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.bluedoll.model.Doll;
import com.example.bluedoll.model.User;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    public ArrayList<User> users = new ArrayList<>();
    public ArrayList<Doll> dolls = new ArrayList<>();
    public DecimalFormat formatter = new DecimalFormat("000");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(getIntent().getBundleExtra("extra")!=null){
            Bundle extra = getIntent().getBundleExtra("extra");
            users = (ArrayList<User>) extra.getSerializable("users");
            dolls = (ArrayList<Doll>) extra.getSerializable("dolls");
            for (User user:users) {
                Log.d("TAG", user.getEmail());
            }
        }else{
            users.add(new User("US"+formatter.format(users.size()+1),"Admin","admin","admin@admin.com",0,"admin"));
        }
        loadFragment(new LoginFragment());
    }
    public boolean loadFragment(Fragment selectedFragment) {
        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_login, selectedFragment).commit();
            if (! getSupportFragmentManager().isDestroyed())
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_login, selectedFragment).commit();
            return true;
        }
        return false;
    }
}