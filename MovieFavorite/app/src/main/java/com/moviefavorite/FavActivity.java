package com.moviefavorite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class FavActivity extends AppCompatActivity {
    ListView lv;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);
        lv = findViewById(R.id.list_fav);
        databaseHelper = new DatabaseHelper(this);
        lv.setAdapter(new MovieSQLiteAdapter(this,databaseHelper.getallMovie(),this));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.kebab_menu,menu);
        menu.findItem(R.id.set).setTitle(getResources().getText(R.string.set));
        menu.findItem(R.id.fav).setTitle(getResources().getText(R.string.fav));
        menu.findItem(R.id.home).setTitle(getResources().getText(R.string.home));
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.set:
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
                break;
            case R.id.home:
                startActivity(new Intent(this,MainActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void delete(int id){
        if(databaseHelper.deleteMovie(id)){
            lv.setAdapter(new MovieSQLiteAdapter(this,databaseHelper.getallMovie(),this));
            Toast.makeText(this, getResources().getText(R.string.delete_success).toString(),
                    Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, getResources().getText(R.string.error).toString(),
                    Toast.LENGTH_LONG).show();
        }

    }
}