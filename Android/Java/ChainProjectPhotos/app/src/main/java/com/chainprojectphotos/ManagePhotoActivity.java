package com.chainprojectphotos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class ManagePhotoActivity extends AppCompatActivity {
    ArrayList<Tempat> tempats;
    private final static int EDIT_CODE = 1;
    RecyclerView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_photo);
        list =findViewById(R.id.listManage);
        if(getIntent().getBundleExtra("extra")!=null){
            Bundle extra = getIntent().getBundleExtra("extra");
            tempats = (ArrayList<Tempat>) extra.getSerializable("tempats");
        }
        list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        list.setAdapter(new ManageAdapter(this,tempats));
    }
    public void edit(int pos){
        startActivityForResult(new Intent(this, EditActivity.class)
                .putExtra("name",tempats.get(pos).name)
                .putExtra("pos",pos), EDIT_CODE);
    }
    public void delete(int pos){
        tempats.remove(pos);
        list.setAdapter(new ManageAdapter(this,tempats));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == EDIT_CODE) {
                if (data != null) {
                    tempats.get(data.getIntExtra("pos",-1)).setName(data.getStringExtra("name"));
                    list.setAdapter(new ManageAdapter(this,tempats));
                }
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_manage,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout_manage:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            case R.id.home_manage:
                Bundle extra = new Bundle();
                extra.putSerializable("tempats", tempats);
                startActivity(new Intent(this, MainActivity.class).putExtra("extra", extra));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}