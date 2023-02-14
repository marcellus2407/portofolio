package com.favorite.movie;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.favorite.movie.DatabaseHelper;
import com.favorite.movie.LoginActivity;

public class  RegisterActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    EditText email,username,password,repassword;
    Button register;
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email=findViewById(R.id.etEmailreg);
        username=findViewById(R.id.etUsernamereg);
        password=findViewById(R.id.etPasswordreg);
        repassword=findViewById(R.id.etRepasswordreg);
        register=findViewById(R.id.btnRegister);
        back=findViewById(R.id.btnBackreg);
        databaseHelper = new DatabaseHelper(this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().isEmpty()||
                        password.getText().toString().isEmpty()||
                        repassword.getText().toString().isEmpty()||
                        email.getText().toString().isEmpty()
                ){
                    if (username.getText().toString().isEmpty())
                        username.setError("FILL THE USERNAME!");
                    if (password.getText().toString().isEmpty())
                        password.setError("FILL THE PASSWORD!");
                    if (repassword.getText().toString().isEmpty())
                        repassword.setError("FILL THE CONFIRMATION PASSWORD!");
                    if (email.getText().toString().isEmpty())
                        email.setError("FILL THE EMAIL!");
                }else {
                    boolean valid = true;
                    if(username.getText().toString().length()<6){
                        username.setError("USERNAME MINIMUM LENGTH IS 6!");
                        valid = false;
                    }
                    if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
                        email.setError("EMAIL IS NOT VALID!");
                        valid = false;
                    }else if(databaseHelper.checkEmail(email.getText().toString())){
                        email.setError("EMAIL IS NOT UNIQUE!");
                        valid = false;
                    }
                    if(password.getText().toString().length()<6){
                        password.setError("PASSWORD MINIMUM LENGTH IS 6!");
                        valid = false;
                    }else if(!password.getText().toString().equals(repassword.getText().toString())){
                        repassword.setError("PASSWORD CONFIRMATION IS NOT MATCH!");
                        valid = false;
                    }
                    if(valid){
                        register_user();
                    }
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoLogin();
            }
        });
    }
    public void gotoLogin(){
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
    public void register_user(){
        User user = new User();
        user.username = username.getText().toString();
        user.password = password.getText().toString();
        user.email = email.getText().toString();
        new  Register_User_Worker(user).execute();
    }

    class Register_User_Worker extends AsyncTask<Void,Void,Long> {
        private final User item;
        public Register_User_Worker(final User item) {
            this.item = item;
        }
        @Override
        protected Long doInBackground(Void... voids){
            databaseHelper.insertUser(item);
            return null;
        }
        protected void onPostExecute(Long along){
            super.onPostExecute(along);
            Toast.makeText(getBaseContext(), "USER REGISTERED", Toast.LENGTH_LONG).show();
            gotoLogin();
        }
    }
}
