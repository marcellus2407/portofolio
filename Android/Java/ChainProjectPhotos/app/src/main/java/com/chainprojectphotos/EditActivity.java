package com.chainprojectphotos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {
    EditText name;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        name = findViewById(R.id.nameEdit);
        save = findViewById(R.id.saveEdit);
        name.setText( getIntent().getStringExtra("name"));
        save.setOnClickListener(view -> {
            if(!name.getText().toString().isEmpty()) {
                Intent intent = new Intent();
                intent.putExtra("name", name.getText().toString());
                intent.putExtra("pos", getIntent().getIntExtra("pos", 0));
                setResult(RESULT_OK, intent);
                finish();
            }else{
                name.setError("name must be filled");
            }
        });

    }
}