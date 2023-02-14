package com.telemedicine.activity.pasien;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.telemedicine.R;
import com.telemedicine.activity.auth.LoginActivity;
import com.telemedicine.adapter.RsAdapter;
import com.telemedicine.provider.RumahSakitProvider;

public class PasienActivity extends AppCompatActivity {
    SharedPreferences preferences;
    ListView lvRS;
    androidx.appcompat.widget.SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasien);
        preferences= getSharedPreferences("auth", 0);
        lvRS = findViewById(R.id.lv_rs);
        searchView = findViewById(R.id.sv_rs);
        Cursor c = getContentResolver().query(RumahSakitProvider.CONTENT_URI, null, null, null, null);
        if(!(c != null && c.moveToFirst() && c.getCount() > 0)){
            ContentValues values = new ContentValues();
            values.put(RumahSakitProvider.name,"Pantai Indah Kapus Hospital");
            values.put(RumahSakitProvider.lat,-6.111922401155085);
            values.put(RumahSakitProvider.lng,106.75256853949122);
            getContentResolver().insert(RumahSakitProvider.CONTENT_URI, values);
            values.put(RumahSakitProvider.name,"Siloam Hospitals Kebon Jeruk");
            values.put(RumahSakitProvider.lat,-6.1908441659260145);
            values.put(RumahSakitProvider.lng,106.76345144413186);
            getContentResolver().insert(RumahSakitProvider.CONTENT_URI, values);
            values.put(RumahSakitProvider.name,"Siloam Hospitals Lippo Village");
            values.put(RumahSakitProvider.lat,-6.225465960348857);
            values.put(RumahSakitProvider.lng,106.59782821285069);
            getContentResolver().insert(RumahSakitProvider.CONTENT_URI, values);
            values.put(RumahSakitProvider.name,"Mayapada Hospital Tangerang (MHTG)");
            values.put(RumahSakitProvider.lat,-6.205366080147312);
            values.put(RumahSakitProvider.lng,106.64183491491893);
            getContentResolver().insert(RumahSakitProvider.CONTENT_URI, values);
        }
        lvRS.setAdapter(new RsAdapter(PasienActivity.this,getContentResolver().query(RumahSakitProvider.CONTENT_URI, new String[]{
                "rowid _id",
                RumahSakitProvider.id,
                RumahSakitProvider.name,
                RumahSakitProvider.lat,
                RumahSakitProvider.lng
        }, null, null, null)));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                lvRS.setAdapter(new RsAdapter(PasienActivity.this,getContentResolver().query(RumahSakitProvider.CONTENT_URI, new String[]{
                        "rowid _id",
                        RumahSakitProvider.id,
                        RumahSakitProvider.name,
                        RumahSakitProvider.lat,
                        RumahSakitProvider.lng
                }, "name LIKE '%"+query+"%'", null, null)));
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()){
                    lvRS.setAdapter(new RsAdapter(PasienActivity.this,getContentResolver().query(RumahSakitProvider.CONTENT_URI, new String[]{
                            "rowid _id",
                            RumahSakitProvider.id,
                            RumahSakitProvider.name,
                            RumahSakitProvider.lat,
                            RumahSakitProvider.lng
                    }, null, null, null)));
                }
                return false;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pasien_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                preferences.edit().remove("id").commit();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            case R.id.dokter:
                startActivity(new Intent(this, ListDokterActivity.class));
                break;
            case R.id.admin:
                startActivity(new Intent(this, ListAdminActivity.class));
                break;
            case R.id.obat:
                startActivity(new Intent(this, ObatActivity.class));
                break;
            case R.id.cart:
                startActivity(new Intent(this, CartActivity.class));
                break;
            case R.id.order:
                startActivity(new Intent(this, OrderActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}