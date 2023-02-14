package com.sewain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class AddActivity extends AppCompatActivity implements LocationListener {
    ImageView img;
    EditText nama,lb,lt,kt,km,listrik,lantai,harga,deskripsi;
    Button upload;

    boolean noLocation=false;
    public double startlongitude, startlatitude;
    DatabaseHelper databaseHelper;
    LocationManager locationManager;

    SharedPreferences.Editor e;
    Bitmap mResultsBitmap;
    String mTempPhotoPath;

    final int REQUEST_STORAGE_PERMISSION = 1;
    final int REQUEST_IMAGE_CAPTURE = 1;
    final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2;
    final String FILE_PROVIDER_AUTHORITY = "com.example.android.fileprovider";

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        databaseHelper = new DatabaseHelper(this);
        nama = findViewById(R.id.namaAdd);
        img = findViewById(R.id.imgAdd);
        lb=findViewById(R.id.lbAdd);
        lt=findViewById(R.id.ltAdd);
        kt=findViewById(R.id.ktAdd);
        km=findViewById(R.id.kmAdd);
        listrik=findViewById(R.id.listrikAdd);
        lantai=findViewById(R.id.lantaiAdd);
        harga=findViewById(R.id.hargaAdd);
        deskripsi=findViewById(R.id.deskripsiAdd);
        upload=findViewById(R.id.uploadAdd);
        e = getPreferences(MODE_PRIVATE).edit();
        img.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_STORAGE_PERMISSION);
            } else {
                launchCamera();
            }
        });
        upload.setOnClickListener(view -> {
            if (validasi()) {
                if (databaseHelper.insert(
                        nama.getText().toString(),
                        imageViewToByte(img),
                        Integer.parseInt(lb.getText().toString()),
                        Integer.parseInt(lt.getText().toString()),
                        Integer.parseInt(kt.getText().toString()),
                        Integer.parseInt(km.getText().toString()),
                        Integer.parseInt(listrik.getText().toString()),
                        Integer.parseInt(lantai.getText().toString()),
                        Integer.parseInt(harga.getText().toString()),
                        deskripsi.getText().toString(),
                        startlatitude,
                        startlongitude
                )) {
                    e.remove("img").commit();
                    img.setImageResource(R.drawable.noimage);
                    nama.setText(null);
                    lb.setText(null);
                    lt.setText(null);
                    kt.setText(null);
                    km.setText(null);
                    listrik.setText(null);
                    lantai.setText(null);
                    harga.setText(null);
                    deskripsi.setText(null);
                    Toast.makeText(this, "Data berhasil disimpan",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Data gagal disimpan",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        }
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,AddActivity.this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,AddActivity.this);
        }catch (Exception e){
            e.printStackTrace();
            noLocation=true;
        }
    }
    boolean validasi(){
        boolean isValid = true;
        if(nama.getText().toString().isEmpty()){
            nama.setError("Isi nama!");
            isValid = false;
        }
        if(lb.getText().toString().isEmpty()){
            lb.setError("Isi Field!");
            isValid = false;
        }
        if(lt.getText().toString().isEmpty()){
            lt.setError("Isi Field!");
            isValid = false;
        }
        if(kt.getText().toString().isEmpty()){
            kt.setError("Isi Field!");
            isValid = false;
        }
        if(km.getText().toString().isEmpty()){
            km.setError("Isi Field!");
            isValid = false;
        }
        if(listrik.getText().toString().isEmpty()){
            listrik.setError("Isi Field!");
            isValid = false;
        }
        if(lantai.getText().toString().isEmpty()){
            lantai.setError("Isi Field!");
            isValid = false;
        }
        if(harga.getText().toString().isEmpty()){
            harga.setError("Isi Field!");
            isValid = false;
        }
        if(deskripsi.getText().toString().isEmpty()){
            deskripsi.setError("Isi Field!");
            isValid = false;
        }
        if(startlatitude==0&&startlongitude==0){
            Toast.makeText(this, "Lokasi tidak dapat diakses",
                    Toast.LENGTH_LONG).show();
            //isValid = false;
        }
        return isValid;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_STORAGE_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    launchCamera();
                } else {
                    Toast.makeText(this, "DITOLAK", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION : {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    startActivity(getIntent());
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            processAndSetImage();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void launchCamera() {
        try {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = BitmapUtils.createTempImageFile(this);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if (photoFile != null) {
                    mTempPhotoPath = photoFile.getAbsolutePath();
                    Uri photoURI = FileProvider.getUriForFile(this,
                            FILE_PROVIDER_AUTHORITY,
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        }catch (Exception e){
            Toast.makeText(this, "Mohon nyalakan permission kamera",
                    Toast.LENGTH_LONG).show();
        }
    }
    public byte[] imageViewToByte(ImageView image){
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    private void processAndSetImage() {
        mResultsBitmap = BitmapUtils.resamplePic(this, mTempPhotoPath);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        mResultsBitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
        byte[] byteArray = stream.toByteArray();
        e.putString("img", Base64.encodeToString(byteArray, Base64.DEFAULT));
        e.apply();
    }
    @Override
    protected void onStart() {
        super.onStart();
        String encodedimg = getPreferences(MODE_PRIVATE).getString("img", "-");
        String namadt = getPreferences(MODE_PRIVATE).getString("nama", "-");
        String lbdt = getPreferences(MODE_PRIVATE).getString("lb", "-");
        String ltdt = getPreferences(MODE_PRIVATE).getString("lt", "-");
        String ktdt = getPreferences(MODE_PRIVATE).getString("kt", "-");
        String kmdt = getPreferences(MODE_PRIVATE).getString("km", "-");
        String listrikdt = getPreferences(MODE_PRIVATE).getString("listrik", "-");
        String lantaidt = getPreferences(MODE_PRIVATE).getString("lantai", "-");
        String hargadt = getPreferences(MODE_PRIVATE).getString("harga", "-");
        String deskdt = getPreferences(MODE_PRIVATE).getString("deskripsi", "-");
        if(!encodedimg.equals("-")){
            byte[] byteArray = Base64.decode(encodedimg,Base64.DEFAULT);
            mResultsBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            Bitmap.createScaledBitmap(mResultsBitmap, 120, 120, false);
            img.setImageBitmap(mResultsBitmap);
        }
        if(!namadt.equals("-")){
            nama.setText(namadt);
        }
        if(!lbdt.equals("-")){
            lb.setText(lbdt);
        }
        if(!ltdt.equals("-")){
            lt.setText(ltdt);
        }
        if(!ktdt.equals("-")){
            kt.setText(ktdt);
        }
        if(!kmdt.equals("-")){
            km.setText(kmdt);
        }
        if(!listrikdt.equals("-")){
            listrik.setText(listrikdt);
        }
        if(!lantaidt.equals("-")){
            lantai.setText(lantaidt);
        }
        if(!hargadt.equals("-")){
            harga.setText(hargadt);
        }
        if(!deskdt.equals("-")){
            deskripsi.setText(deskdt);
        }
    }
    @Override
    protected void onStop() {
        e.putString("nama", nama.getText().toString());
        e.putString("lb", lb.getText().toString());
        e.putString("lt", lt.getText().toString());
        e.putString("kt", kt.getText().toString());
        e.putString("km", km.getText().toString());
        e.putString("listrik", listrik.getText().toString());
        e.putString("lantai", lantai.getText().toString());
        e.putString("harga", harga.getText().toString());
        e.putString("deskripsi", deskripsi.getText().toString());
        e.apply();
        super.onStop();
    }
    @Override
    public void onLocationChanged(@NonNull Location location) {
        startlongitude = location.getLongitude();
        startlatitude = location.getLatitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}