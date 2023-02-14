package com.telemedicine.activity.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.telemedicine.API;
import com.telemedicine.APIClient;
import com.telemedicine.R;
import com.telemedicine.Utils;
import com.telemedicine.model.LoginResponse;
import com.telemedicine.model.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {
    TextView login;
    EditText email, password, nama, spesialis;
    TextInputLayout tilSpesialis;
    AutoCompleteTextView role;
    Button register;
    ProgressDialog progressDialog;
    API api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register = findViewById(R.id.btn_register);
        nama = findViewById(R.id.et_nama);
        role = findViewById(R.id.dd_role);
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        spesialis = findViewById(R.id.et_spesialis);
        login = findViewById(R.id.tv_login);
        tilSpesialis = findViewById(R.id.til_spesialis);
        role.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,new String[]{"Pasien","Dokter","Admin"}));
        login.setOnClickListener(view -> {
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        });
        progressDialog = Utils.PROGRESS(this);
        register.setOnClickListener(view -> register());
        api = APIClient.getClient().create(API.class);
        role.setOnItemClickListener((adapterView, view, i, l) -> {
            if(role.getText().toString().equals("Dokter")){
                tilSpesialis.setVisibility(View.VISIBLE);
            }else{
                tilSpesialis.setVisibility(View.GONE);
            }
        });
    }
    public void register(){
        if(role.getText().toString().equals("Dokter")){
            if(
                    nama.getText().toString().isEmpty()||
                            email.getText().toString().isEmpty()||
                            password.getText().toString().isEmpty()||
                            role.getText().toString().isEmpty()||
                            spesialis.getText().toString().isEmpty()
            ){
                Toast.makeText(this,"Isi input dengan benar",Toast.LENGTH_SHORT).show();
            }else {
                progressDialog.show();
                Call<RegisterResponse> registerCall = api.register(
                        nama.getText().toString(),
                        email.getText().toString(),
                        password.getText().toString(),
                        role.getText().toString(),
                        spesialis.getText().toString()
                );
                registerCall.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        progressDialog.dismiss();
                        if(response.body().response.equals("Register berhasil")){
                            nama.setText(null);
                            email.setText(null);
                            password.setText(null);
                            role.setText(null,false);
                        }
                        Toast.makeText(RegisterActivity.this, response.body().response, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }else{
            if(
                    nama.getText().toString().isEmpty()||
                            email.getText().toString().isEmpty()||
                            password.getText().toString().isEmpty()||
                            role.getText().toString().isEmpty()
            ){
                Toast.makeText(this,"Isi input dengan benar",Toast.LENGTH_SHORT).show();
            }else {
                progressDialog.show();
                Call<RegisterResponse> registerCall = api.register(nama.getText().toString(), email.getText().toString(), password.getText().toString(), role.getText().toString());
                registerCall.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        progressDialog.dismiss();
                        if(response.body().response.equals("Register berhasil")){
                            nama.setText(null);
                            email.setText(null);
                            password.setText(null);
                            role.setText(null,false);
                        }
                        Toast.makeText(RegisterActivity.this, response.body().response, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}