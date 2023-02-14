package com.telemedicine.activity.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.telemedicine.API;
import com.telemedicine.APIClient;
import com.telemedicine.R;
import com.telemedicine.Utils;
import com.telemedicine.activity.admin.AdminActivity;
import com.telemedicine.activity.dokter.DokterActivity;
import com.telemedicine.activity.pasien.PasienActivity;
import com.telemedicine.model.LoginResponse;
import com.telemedicine.model.RegisterResponse;
import com.telemedicine.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextView register;
    EditText email, password;
    Button login;
    ProgressDialog progressDialog;
    API api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        register = findViewById(R.id.tv_register);
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        login = findViewById(R.id.btn_login);
        progressDialog = Utils.PROGRESS(this);
        api = APIClient.getClient().create(API.class);
        SharedPreferences preferences= getSharedPreferences("auth", 0);
        if(preferences.getInt("id",-1)!=-1){
            gotoDashboard(preferences.getInt("id",-1));
            finish();
        }
        register.setOnClickListener(view -> {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        });
        login.setOnClickListener(view -> login());

    }
    private void login(){
        progressDialog.show();
        Call<LoginResponse> loginCall = api.login(email.getText().toString(), password.getText().toString());
        loginCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progressDialog.dismiss();
                if(response.body().id!=-1){
                    SharedPreferences.Editor editor = getSharedPreferences("auth", 0).edit();
                    editor.putInt("id", response.body().id);
                    editor.apply();
                    gotoDashboard(response.body().id);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this,"Email atau password salah",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void gotoDashboard(int id){
        Call<User> getUserCall = api.getUser(id);
        getUserCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.body().role.equals("Admin")){
                    startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                    finish();
                }else if(response.body().role.equals("Dokter")){
                    startActivity(new Intent(LoginActivity.this, DokterActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(LoginActivity.this, PasienActivity.class));
                    finish();
                }
                progressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.dismiss();
                finish();
            }
        });
    }
}