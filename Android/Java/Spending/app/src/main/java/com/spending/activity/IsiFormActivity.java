package com.spending.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.spending.R;
import com.spending.model.Spending;

import java.util.ArrayList;

public class IsiFormActivity extends AppCompatActivity {
    EditText title, nominal;
    Spinner category;
    Button save;
    ImageButton back;
    ArrayList<Spending> spendings;
    TextView toolbartitle;
    Bundle extra;
    final int entertaiment = R.drawable.ic_baseline_celebration_24;
    final int fooddrink = R.drawable.ic_baseline_brunch_dining_24;
    final int transportation = R.drawable.ic_baseline_airport_shuttle_24;
    final int internet = R.drawable.ic_baseline_cell_tower_24;
    String e,t,fd,i;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isi_form);
        title = findViewById(R.id.title_field);
        nominal = findViewById(R.id.nominal_field);
        category = findViewById(R.id.category_spinner);
        save = findViewById(R.id.save_button);
        back = findViewById(R.id.back);
        toolbartitle = findViewById(R.id.toolbar_title);
        extra = getIntent().getBundleExtra("extra");
        spendings = (ArrayList<Spending>) extra.getSerializable("spendings");
        index = extra.getInt("index",-1);
        e = this.getString(R.string.e);
        t = this.getString(R.string.t);
        fd = this.getString(R.string.fd);
        i = this.getString(R.string.i);
        back.setOnClickListener(view -> gotomain());
        Log.d("TAG", "onCreate: "+index);
        if(index!=-1){
            Spending spending = spendings.get(index);
            title.setText(spending.title);
            nominal.setText(String.valueOf(spending.nominal));
            switch (spending.category){
                case entertaiment:
                    category.setSelection(0);
                    break;
                case fooddrink:
                    category.setSelection(1);
                    break;
                case transportation:
                    category.setSelection(2);
                    break;
                case internet:
                    category.setSelection(3);
                    break;
            }
            toolbartitle.setText("Edit Spending");
            save.setOnClickListener(view -> edit_mode());
        }else{
            toolbartitle.setText("Add Spending");
            save.setOnClickListener(view -> add_mode());
        }
    }
    private void add_mode(){
        if(validation()){
            Spending spending = new Spending();
            spending.title = title.getText().toString();
            spending.nominal = Integer.parseInt(nominal.getText().toString());
            spending.category =converttoimage(category.getSelectedItem().toString());
            spendings.add(0, spending);
            gotomain();
        }
    }
    private void edit_mode(){
        if(validation()){
            spendings.get(index).title = title.getText().toString();
            spendings.get(index).nominal = Integer.parseInt(nominal.getText().toString());
            spendings.get(index).category =converttoimage(category.getSelectedItem().toString());
            gotomain();
        }
    }
    public boolean validation(){
        boolean isValid = true;
        if(title.getText().toString().length()<5){
            title.setError("Title should at least 5 characters");
            isValid = false;
        }
        if(nominal.getText().toString().isEmpty()){
            nominal.setError("Input nominal first");
            isValid = false;
        }else if(!nominal.getText().toString().matches("[0-9]+")){
            nominal.setError("Nominal is not number");
            isValid = false;
        }
        return isValid;
    }

    private void gotomain(){
        Intent i = new Intent(this, MainActivity.class);
        Bundle extratomain = new Bundle();
        extratomain.putSerializable("spendings", spendings);
        i.putExtra("extra", extratomain);
        startActivity(i);
        finish();
    }
    private int converttoimage(String s){
        if (s.equals(e)) {
            return entertaiment;
        }else if(s.equals(t)) {
            return transportation;
        }else if(s.equals(fd)) {
            return fooddrink;
        }else if(s.equals(i)) {
            return internet;
        }
        return -1;
    }
}