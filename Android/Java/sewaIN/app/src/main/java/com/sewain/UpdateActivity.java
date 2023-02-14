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
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.LocationListener;
import android.location.LocationManager;
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
import java.text.NumberFormat;

public class UpdateActivity extends AppCompatActivity {
    ImageView img;
    EditText nama, lb, lt, kt, km, listrik, lantai, harga, deskripsi;
    Button upload;

    Bitmap mResultsBitmap;
    String mTempPhotoPath;
    int id;
    byte[] imgdata;
    String namadt, deskripsidt;
    int lbdt,ltdt,ktdt,kmdt,listrikdt,lantaidt,hargadt;

    DatabaseHelper databaseHelper;
    final int REQUEST_STORAGE_PERMISSION = 1;
    final int REQUEST_IMAGE_CAPTURE = 1;
    final String FILE_PROVIDER_AUTHORITY = "com.example.android.fileprovider";

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        databaseHelper = new DatabaseHelper(this);
        nama = findViewById(R.id.namaUpdate);
        img = findViewById(R.id.imgUpdate);
        lb = findViewById(R.id.lbUpdate);
        lt = findViewById(R.id.ltUpdate);
        kt = findViewById(R.id.ktUpdate);
        km = findViewById(R.id.kmUpdate);
        listrik = findViewById(R.id.listrikUpdate);
        lantai = findViewById(R.id.lantaiUpdate);
        harga = findViewById(R.id.hargaUpdate);
        deskripsi = findViewById(R.id.deskripsiUpdate);
        upload = findViewById(R.id.uploadUpdate);

        Bundle b = getIntent().getExtras();
        id = b.getInt("id");

        Cursor csr = databaseHelper.getItem(id);
        csr.moveToFirst ();

        namadt = csr.getString(2);
        imgdata = csr.getBlob(3);
        lbdt = csr.getInt(4);
        ltdt = csr.getInt(5);
        ktdt = csr.getInt(6);
        kmdt = csr.getInt(7);
        listrikdt = csr.getInt(8);
        lantaidt = csr.getInt(9);
        hargadt = csr.getInt(10);
        deskripsidt = csr.getString(11);

        nama.setText(namadt);
        img.setImageBitmap(BitmapFactory.decodeByteArray(imgdata, 0, imgdata.length));
        lb.setText(String.valueOf(lbdt));
        lt.setText(String.valueOf(ltdt));
        kt.setText(String.valueOf(ktdt));
        km.setText(String.valueOf(kmdt));
        listrik.setText(String.valueOf(listrikdt));
        lantai.setText(String.valueOf(lantaidt));
        harga.setText(String.valueOf(hargadt));
        deskripsi.setText(deskripsidt);

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
                if (databaseHelper.update(
                        id,
                        nama.getText().toString(),
                        imageViewToByte(img),
                        Integer.parseInt(lb.getText().toString()),
                        Integer.parseInt(lt.getText().toString()),
                        Integer.parseInt(kt.getText().toString()),
                        Integer.parseInt(km.getText().toString()),
                        Integer.parseInt(listrik.getText().toString()),
                        Integer.parseInt(lantai.getText().toString()),
                        Integer.parseInt(harga.getText().toString()),
                        deskripsi.getText().toString()
                )) {
                    Toast.makeText(this, "Data berhasil diubah",
                            Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(this, "Data gagal diubah",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    boolean validasi() {
        boolean isValid = true;
        if (nama.getText().toString().isEmpty()) {
            nama.setError("Isi nama!");
            isValid = false;
        }
        if (lb.getText().toString().isEmpty()) {
            lb.setError("Isi Field!");
            isValid = false;
        }
        if (lt.getText().toString().isEmpty()) {
            lt.setError("Isi Field!");
            isValid = false;
        }
        if (kt.getText().toString().isEmpty()) {
            kt.setError("Isi Field!");
            isValid = false;
        }
        if (km.getText().toString().isEmpty()) {
            km.setError("Isi Field!");
            isValid = false;
        }
        if (listrik.getText().toString().isEmpty()) {
            listrik.setError("Isi Field!");
            isValid = false;
        }
        if (lantai.getText().toString().isEmpty()) {
            lantai.setError("Isi Field!");
            isValid = false;
        }
        if (harga.getText().toString().isEmpty()) {
            harga.setError("Isi Field!");
            isValid = false;
        }
        if (deskripsi.getText().toString().isEmpty()) {
            deskripsi.setError("Isi Field!");
            isValid = false;
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
        } catch (Exception e) {
            Toast.makeText(this, "Mohon nyalakan permission kamera",
                    Toast.LENGTH_LONG).show();
        }
    }

    public byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
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
        mResultsBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        Bitmap.createScaledBitmap(mResultsBitmap, 120, 120, false);
        img.setImageBitmap(mResultsBitmap);
    }
}