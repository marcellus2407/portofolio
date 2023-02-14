package com.chainprojectphotos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    EditText email,name,password,phone,address;
    Button bod;
    TextView login;
    Button register;
    RadioGroup gender;
    CheckBox terms;
    Calendar calendar = Calendar.getInstance();
    ArrayList<User> users;
    Random rn = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if(getIntent().getBundleExtra("extra")!=null){
            Bundle extra = getIntent().getBundleExtra("extra");
            users = (ArrayList<User>) extra.getSerializable("users");
        }
        email=findViewById(R.id.emailReg);
        name=findViewById(R.id.nameReg);
        password=findViewById(R.id.passwordReg);
        address=findViewById(R.id.addressReg);
        phone=findViewById(R.id.phoneReg);
        gender=findViewById(R.id.genderReg);
        bod=findViewById(R.id.bodReg);
        terms=findViewById(R.id.termsReg);
        register=findViewById(R.id.registerReg);
        login=findViewById(R.id.loginReg);
        bod.setOnClickListener(v -> Show_Calendar());

        register.setOnClickListener(view -> {
            if(validation()){
                        users.add(new User("US"+rn.nextInt(10)+rn.nextInt(10)+rn.nextInt(10),
                                email.getText().toString(),
                                name.getText().toString(),
                                password.getText().toString(),
                                ((RadioButton)findViewById(gender.getCheckedRadioButtonId())).getText().toString(),
                                address.getText().toString(),
                                phone.getText().toString(),
                                bod.getText().toString())
                        );

                gotoLogin();
            }
        });
        login.setOnClickListener(view -> gotoLogin());
    }
    private void gotoLogin(){
        Bundle extra = new Bundle();
        extra.putSerializable("users", users);
        startActivity(new Intent(this,LoginActivity.class).putExtra("extra", extra));
        finish();
    }
    private boolean validation(){
        boolean isValid = true;
        if(name.getText().toString().length()<4){
            name.setError("name must be longer than 4 characters");
            isValid = false;
        }
        if(!Pattern.matches("[^@]+@[^@]+\\.[^@]+", email.getText().toString())){
            email.setError("email is not valid!");
            isValid = false;
        }
        if(password.getText().toString().length()<4){
            password.setError("password must be longer than 4 characters");
            isValid = false;
        }
        if(gender.getCheckedRadioButtonId() == -1){
            Toast.makeText(this, "gender must be chosen", Toast.LENGTH_LONG).show();
            isValid = false;
        }
        if(address.getText().toString().length()<8){
            address.setError("address must be longer than 8 characters");
            isValid = false;
        }else if(!Pattern.matches(".+street", address.getText().toString())){
            isValid = false;
            address.setError("address must end with “<<street>>street”.");
        }

        if(phone.getText().toString().isEmpty()){
            phone.setError("phone must not be empty");
            isValid = false;
        }else{
            try {
                Double.parseDouble(phone.getText().toString());
            } catch (NumberFormatException e) {
                phone.setError("phone number must be numeric");
                isValid = false;
            }
        }

        if(bod.getText().toString().equalsIgnoreCase("Date of Birth")) {
            Toast.makeText(this, "Birth of date must be choosen", Toast.LENGTH_LONG).show();
            isValid = false;
        }else{
            int age = (calendar.getTime().getYear()+1900)-(Integer.parseInt(bod.getText().toString().substring(6,10)));
            if(Integer.parseInt(bod.getText().toString().substring(3,5))<(calendar.getTime().getMonth()+1)){
                age-=1;
            }else if(
                    (Integer.parseInt(bod.getText().toString().substring(3,5))==(calendar.getTime().getMonth()+1))&&
                            (Integer.parseInt(bod.getText().toString().substring(0,2))<(calendar.getTime().getDate()))
            ){
                age-=1;
            }
            if(age<=17){
                Toast.makeText(this, "user must be older than 17 years old", Toast.LENGTH_LONG).show();
                isValid = false;
            }
        }
        if(!terms.isChecked()){
            Toast.makeText(this, "You must check the Terms of Service", Toast.LENGTH_LONG).show();
            isValid = false;
        }
        return isValid;
    }

    private void Show_Calendar(){
        bod.setShowSoftInputOnFocus(false);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view1, year, month, dayOfMonth) -> {
                    month = month + 1;
                    String date;
                    String m,d;
                    if (month < 10) {
                        m="0"+month;
                    } else {
                        m= String.valueOf(month);
                    }
                    if (dayOfMonth < 10) {
                        d="0"+dayOfMonth;
                    } else {
                        d= String.valueOf(dayOfMonth);
                    }
                    date = d + "-" + m + "-" + year;
                    bod.setText(date);

                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        if(!datePickerDialog.isShowing()) {
            datePickerDialog.show();
        }
    }
}