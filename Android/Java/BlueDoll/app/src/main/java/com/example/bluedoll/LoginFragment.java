package com.example.bluedoll;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.bluedoll.model.User;

public class LoginFragment extends Fragment {
    EditText email,password;
    Button login;
    TextView register;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        LoginActivity loginact = (LoginActivity) getActivity();
        email = view.findViewById(R.id.loginEmail);
        password = view.findViewById(R.id.loginPassword);
        login = view.findViewById(R.id.loginBtn);
        register = view.findViewById(R.id.register);
        login.setOnClickListener(view1 -> {
            if(!email.getText().toString().isEmpty()&&!password.getText().toString().isEmpty()){
                boolean wrong = true;
                for (User user : loginact.users) {
                    if(email.getText().toString().equals(user.getEmail())&&
                            password.getText().toString().equals(user.getPassword())){
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        Bundle extra = new Bundle();
                        extra.putSerializable("users", loginact.users);
                        extra.putSerializable("dolls", loginact.dolls);
                        extra.putInt("idx_user",loginact.users.indexOf(user));
                        intent.putExtra("extra", extra);
                        wrong = false;
                        startActivity(intent);
                        getActivity().finish();
                    }
                }
                if(wrong){
                    Toast.makeText(getActivity(), "Email or Password is wrong", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(getActivity(), "Input All Field", Toast.LENGTH_LONG).show();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginact.loadFragment(new RegisterFragment());
            }
        });
        return view;
    }
}