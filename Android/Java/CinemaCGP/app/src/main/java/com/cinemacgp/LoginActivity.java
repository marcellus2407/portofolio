package com.cinemacgp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.cinemacgp.database.DatabaseHelper;
import com.cinemacgp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db=new DatabaseHelper(this);
        SharedPreferences prefs = getSharedPreferences("Mypref", MODE_PRIVATE);
        if(prefs.getInt("id", -1)!=-1){
            gotoMain();
        }
        binding.tvRegister.setOnClickListener(view -> gotoRegister());
        binding.btnLogin.setOnClickListener(view -> {
            int id = db.login(binding.etEmail.getText().toString(),binding.etPassword.getText().toString());
            if(id!=-1){
                SharedPreferences.Editor editor = getSharedPreferences("Mypref", MODE_PRIVATE).edit();
                editor.putInt("id",id);
                editor.apply();
                gotoMain();
            }else{
                Toast.makeText(this,"Wrong Email or Password",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void gotoRegister(){
        startActivity(new Intent(this, RegisterActivity.class));
        finish();
    }
    private void gotoMain(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}