package com.example.phone_screen;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.core.content.PackageManagerCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_CODE = 1;
    ImageButton camSensor, smsSensor;
    ImageView screen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screen = (ImageView) findViewById(R.id.screen);
        camSensor = (ImageButton) findViewById(R.id.cameraBtn);
        camSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    checkCameraPermissionAndOpenCamera();
                    intentAndActivity();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        smsSensor = (ImageButton) findViewById(R.id.smsBtn);
        smsSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent();
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivity(intent);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    private void checkCameraPermissionAndOpenCamera(){
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        }
        else{
            intentAndActivity();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 12&& data !=null) {
            screen.setImageBitmap((Bitmap) data.getExtras().get("data"));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE){
            if (grantResults.length>0&& grantResults[0] == PackageManager.PERMISSION_GRANTED){
                intentAndActivity();
            }
            else{
                Toast.makeText(this, "Camera permission denied, please allow permission to take picture", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void intentAndActivity(){
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 12);
    }
}