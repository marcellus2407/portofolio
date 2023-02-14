package com.sewain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    ListView listitem;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new DatabaseHelper(this);
        listitem = findViewById(R.id.listItem);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Cursor allitem = databaseHelper.getItems();
        listitem.setAdapter(new ItemAdapter(this,allitem));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.kebab_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.editor:
                startActivity(new Intent(this, EditorActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}