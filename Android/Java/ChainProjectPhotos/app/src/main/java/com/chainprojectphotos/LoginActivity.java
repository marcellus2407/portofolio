package com.chainprojectphotos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    Button login;
    TextView register;
    public ArrayList<User> users = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.emailLogin);
        password=findViewById(R.id.passwordLogin);
        login=findViewById(R.id.loginLogin);
        register=findViewById(R.id.registerLogin);
        if(getIntent().getBundleExtra("extra")!=null){
            Bundle extra = getIntent().getBundleExtra("extra");
            users = (ArrayList<User>) extra.getSerializable("users");
        }
        login.setOnClickListener(view -> {
            if(validation()){
                startActivity( new Intent(this,MainActivity.class));
                finish();
            }
        });

        register.setOnClickListener(view -> {
            Bundle extra = new Bundle();
            extra.putSerializable("users", users);
            startActivity(new Intent(this,RegisterActivity.class).putExtra("extra", extra));
            finish();
        });
    }
    private boolean validation(){
        boolean isValid = true;
        if(email.getText().toString().isEmpty()){
            isValid=false;
            email.setError("Email must be filled");
        }
        if(password.getText().toString().isEmpty()){
            isValid=false;
            password.setError("Password must be filled");
        }
        if(isValid){
            for (User user:users) {
                if(user.email.equals(email.getText().toString())){
                    if(user.password.equals(password.getText().toString())){
                        return true;
                    }else{
                        password.setError("Password is wrong");
                        return false;
                    }
                }
            }
            email.setError("Email not registered");
            return false;
        }else{
            return false;
        }
    }
}