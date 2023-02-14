package com.moviefavorite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Locale;

public class DetailActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle b = getIntent().getExtras();
        ImageView imageview = findViewById(R.id.imageDetail);
        TextView titleview  = findViewById(R.id.titleDetail);
        TextView avgview  = findViewById(R.id.voteDetail);
        TextView descview  = findViewById(R.id.descDetail);
        Button save = findViewById(R.id.btnSave);
        databaseHelper = new DatabaseHelper(this);
        titleview .setText(b.getString("title"));
        avgview .setText(getResources().getText(R.string.va).toString()+" : "+b.getDouble("avg"));
        descview .setText(b.getString("desc"));
        if(b.getString("fav")!=null){
            save.setVisibility(View.GONE);
        }
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500"+b.getString("img"))
                .into(imageview);
        save.setOnClickListener(view -> {
            if(databaseHelper.insertMovie(
                    b.getString("title"),
                    b.getString("img"),
                    b.getDouble("avg"),
                    b.getString("desc"))){
                Toast.makeText(this, getResources().getText(R.string.add_success).toString(),
                        Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, getResources().getText(R.string.error).toString(),
                        Toast.LENGTH_LONG).show();
            }
        });

    }
}