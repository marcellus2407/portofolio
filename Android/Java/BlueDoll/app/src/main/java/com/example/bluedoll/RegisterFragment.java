package com.example.bluedoll;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.bluedoll.model.User;

import java.util.Calendar;

public class RegisterFragment extends Fragment {
    EditText email, name, birthday, password, passwordConfirm;
    RadioGroup gender;
    CheckBox agreement;
    Button register;
    TextView login;
    int gendernumber = -1;
    Calendar calendar = Calendar.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        email = view.findViewById(R.id.registerEmail);
        name = view.findViewById(R.id.registerName);
        birthday = view.findViewById(R.id.registerBirthday);
        password = view.findViewById(R.id.registerPassword);
        passwordConfirm = view.findViewById(R.id.registerPasswordConfirmation);
        gender = view.findViewById(R.id.registerGender);
        agreement = view.findViewById(R.id.registerAgreement);
        register = view.findViewById(R.id.registerBtn);
        login = view.findViewById(R.id.login);
        LoginActivity loginact = (LoginActivity) getActivity();
        birthday.setOnClickListener(v -> Show_Calendar());
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.registerMale:
                        gendernumber = 0;
                        break;
                    case R.id.registerFemale:
                        gendernumber = 1;
                        break;
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(agreement.isChecked()){
                    if(email.getText().toString().isEmpty()||
                            name.getText().toString().isEmpty()||
                            birthday.getText().toString().isEmpty()||
                            password.getText().toString().isEmpty()){
                        Toast.makeText(getActivity(), "Input all the field", Toast.LENGTH_LONG).show();
                    }else {
                        if(!Patterns.EMAIL_ADDRESS.matcher((CharSequence) email.getText().toString()).matches()){
                            Toast.makeText(getActivity(), "Email is not valid", Toast.LENGTH_LONG).show();
                            return;
                        }

                        if(password.getText().toString().length()<6){
                            Toast.makeText(getActivity(), "Password at least 6 character", Toast.LENGTH_LONG).show();
                            return;
                        }else if(!password.getText().toString().equals(passwordConfirm.getText().toString())){
                            Toast.makeText(getActivity(), "Password is not match", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(gendernumber==-1){
                            Toast.makeText(getActivity(), "Please choose the gender", Toast.LENGTH_LONG).show();
                            return;
                        }
                        for (User user:loginact.users) {
                            if(email.getText().toString().equals(user.getEmail())){
                                Toast.makeText(getActivity(), "Email must be unique", Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                        loginact.users.add(
                                new User(
                                        "US"+loginact.formatter.format(loginact.users.size()+1),
                                        name.getText().toString(),
                                        password.getText().toString(),
                                        email.getText().toString(),
                                        gendernumber,
                                        "member"));
                        Toast.makeText(getActivity(), "User Registered", Toast.LENGTH_LONG).show();
                        loginact.loadFragment(new LoginFragment());

                    }
                }else{
                    Toast.makeText(getActivity(), "Please check the agreement", Toast.LENGTH_LONG).show();
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginact.loadFragment(new LoginFragment());
            }
        });
        return view;
    }
    private void Show_Calendar(){
        birthday.setShowSoftInputOnFocus(false);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                style,
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
                    birthday.setText(date);
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