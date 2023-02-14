package com.favorite.movie;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.favorite.movie.DatabaseHelper;
import com.favorite.movie.MainActivity;

public class LoginActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    EditText email,password;
    Button login;
    TextView register;
    User user = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.etEmaillogin);
        password = findViewById(R.id.etPasswordlogin);
        login = findViewById(R.id.btnLogin);
        register = findViewById(R.id.tvRegister);
        databaseHelper = new DatabaseHelper(this);
        SharedPreferences prefs = getSharedPreferences("Mypref", MODE_PRIVATE);
        int id=prefs.getInt("id", -1);
        if(id!=-1){
            gotoMain();
        }
        register.setOnClickListener(view -> gotoRegister());

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().isEmpty()||
                        password.getText().toString().isEmpty()){
                    if (email.getText().toString().isEmpty())
                        email.setError("FILL THE EMAIL");
                    if (password.getText().toString().isEmpty())
                        password.setError("FILL THE PASSWORD!");
                }else{
                    user = databaseHelper.Authenticate(email.getText().toString(),password.getText().toString());
                    if(user==null){
                        Toast.makeText(getBaseContext(), "EMAIL OR PASSWORD IS WRONG", Toast.LENGTH_LONG).show();
                    }else{
                        SharedPreferences.Editor editor = getSharedPreferences("Mypref", MODE_PRIVATE).edit();
                        editor.putInt("id",user.id);
                        editor.apply();
                        gotoMain();
                    }
                }
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
