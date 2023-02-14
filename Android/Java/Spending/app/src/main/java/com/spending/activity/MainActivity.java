package com.spending.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.spending.R;
import com.spending.adapter.RecyclerViewAdapter;
import com.spending.model.Spending;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView Recyclerview;
    FloatingActionButton FAB;
    ArrayList<Spending> spendings = new ArrayList<>();
    RecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Recyclerview = findViewById(R.id.Recyclerview);
        FAB = findViewById(R.id.Floatbutton);
        adapter = new RecyclerViewAdapter(this);
        Recyclerview.setLayoutManager(new LinearLayoutManager(this));
        Recyclerview.setAdapter(adapter);
        if(getIntent().getBundleExtra("extra")!=null){
            Bundle extra = getIntent().getBundleExtra("extra");
            spendings = (ArrayList<Spending>) extra.getSerializable("spendings");
        }
        update_recyclerview();
        FAB.setOnClickListener(view -> add_spending());
    }
    private void add_spending(){
        Intent i = new Intent(MainActivity.this, IsiFormActivity.class);
        Bundle extra = new Bundle();
        extra.putSerializable("spendings", spendings);
        i.putExtra("extra", extra);
        startActivity(i);
        finish();
    }
    public void edit_spending(int index){
        Intent i = new Intent(MainActivity.this, IsiFormActivity.class);
        Bundle extra = new Bundle();
        extra.putInt("index",index);
        extra.putSerializable("spendings", spendings);
        i.putExtra("extra", extra);
        startActivity(i);
        finish();
    }
    public void delete_spending(int index){
        spendings.remove(index);
        update_recyclerview();
    }
    private void update_recyclerview(){
        adapter.spendings = spendings;
        adapter.notifyDataSetChanged();
    }
}