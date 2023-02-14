package com.cinemacgp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import com.cinemacgp.database.DatabaseHelper;
import com.cinemacgp.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db=new DatabaseHelper(this);
        binding.tvLogin.setOnClickListener(view -> gotoLogin());
        binding.btnRegister.setOnClickListener(view -> {
            if(isValid()){
                if(db.register(
                        binding.etEmail.getText().toString(),
                        binding.etUsername.getText().toString(),
                        binding.etPassword.getText().toString()
                )){
                    binding.etEmail.setText(null);
                    binding.etUsername.setText(null);
                    binding.etPassword.setText(null);
                    Toast.makeText(this,"Registration Successfull",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this,"Registration Failed",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private boolean isValid(){
        boolean valid = true;
        if(binding.etEmail.getText().toString().isEmpty()){
            binding.tilEmail.setError("Fill The Email");
            valid=false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.getText().toString()).matches()){
            binding.tilEmail.setError("Email invalid");
            valid=false;
        }else if(!db.isEmailUnique(binding.etEmail.getText().toString())){
            binding.tilEmail.setError("Email already in use");
            valid=false;
        }else{
            binding.tilEmail.setErrorEnabled(false);
        }
        if(binding.etUsername.getText().toString().isEmpty()){
            binding.tilUsername.setError("Fill The Username");
            valid=false;
        }else{
            binding.tilUsername.setErrorEnabled(false);
        }
        if(binding.etPassword.getText().toString().isEmpty()){
            binding.tilPassword.setError("Fill The Password");
            valid=false;
        }else{
            binding.tilPassword.setErrorEnabled(false);
        }
        if(!binding.etRepassword.getText().toString().equals(binding.etPassword.getText().toString())){
            binding.tilRepassword.setError("Confirmation Password doesn't match");
            valid=false;
        }else{
            binding.tilRepassword.setErrorEnabled(false);
        }
        return valid;
    }
    private void gotoLogin(){
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}