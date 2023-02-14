package com.chainprojectphotos;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    String url = "https://raw.githubusercontent.com/acad600/JSONRepository/master/ISYS6203/O203-ISYS6203-CP01-01%20ChainProject%20Photos";
    ArrayList<Tempat> tempats;
    RequestQueue requestQueue;
    RecyclerView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list =findViewById(R.id.listMain);
        requestQueue = Volley.newRequestQueue(this);
        list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        if(getIntent().getBundleExtra("extra")!=null){
            Bundle extra = getIntent().getBundleExtra("extra");
            tempats = (ArrayList<Tempat>) extra.getSerializable("tempats");
            list.setAdapter(new MainAdapter(this,tempats));
        }else{
            get_all_data();
        }
    }
    public void gotoLoc(int pos){
        Bundle extra = new Bundle();
        extra.putSerializable("tempats", tempats);
        extra.putInt("pos",pos);
        startActivity(new Intent(this,LocationActivity.class).putExtra("extra", extra));
    }
    private void get_all_data(){
        tempats = new ArrayList<>();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray results = (JSONArray) response.get("data");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject jsonObject = results.getJSONObject(i);
                        tempats.add(new Tempat(
                                jsonObject.getString("imageurl"),
                                jsonObject.getString("name"),
                                jsonObject.getDouble("lat"),
                                jsonObject.getDouble("lng"))
                        );
                    }
                    list.setAdapter(new MainAdapter(MainActivity.this,tempats));
                } catch (Exception w) {
                    Toast.makeText(getBaseContext(), w.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout_main:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            case R.id.manage_main:
                Bundle extra = new Bundle();
                extra.putSerializable("tempats", tempats);
                startActivity(new Intent(this, ManagePhotoActivity.class).putExtra("extra", extra));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}