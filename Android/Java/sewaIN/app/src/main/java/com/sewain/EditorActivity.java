package com.sewain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditorActivity extends AppCompatActivity {
    FloatingActionButton FAB;
    ListView listitem;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        databaseHelper = new DatabaseHelper(this);
        FAB = findViewById(R.id.addItem);
        FAB.setOnClickListener(view -> {
            startActivity(new Intent(this,AddActivity.class));
        });
        databaseHelper = new DatabaseHelper(this);
        listitem = findViewById(R.id.listItemEditor);
    }
    void setAdapter(){
        Cursor allitem = databaseHelper.getItems();
        listitem.setAdapter(new ItemEditorAdapter(this,allitem,this));
    }
    @Override
    protected void onStart() {
        super.onStart();
        setAdapter();
    }

    public void deleteItem(int id){
        if(databaseHelper.delete(id)){
            Toast.makeText(this, "Data berhasil dihapus",
                    Toast.LENGTH_LONG).show();
            setAdapter();
        }else{
            Toast.makeText(this, "Data gagal dihapus",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void updateItem(int id){
        startActivity(new Intent(this,UpdateActivity.class).putExtra("id",id));
    }
}