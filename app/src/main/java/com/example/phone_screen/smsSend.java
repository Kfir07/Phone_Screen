package com.example.phone_screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class smsSend extends AppCompatActivity {

    EditText phoneNumber, message;
    Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_send);

        phoneNumber = (EditText) findViewById(R.id.PhoneNumber);
        message = (EditText) findViewById(R.id.sms);
        send = (Button) findViewById(R.id.sendBtn);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(smsSend.this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED) {
                    sendSms();
                }
                else{
                    ActivityCompat.requestPermissions(smsSend.this, new String[]{Manifest.permission.SEND_SMS}, 100);
                }
            }
        });
    }

    public void sendSms(){
        String phoneNum = phoneNumber.getText().toString();
        String smsText = message.getText().toString();

        if (!phoneNum.isEmpty() && !smsText.isEmpty()){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNum,null,smsText,null,null);
            Toast.makeText(this,"SMS Sent",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"Enter phone number and message",Toast.LENGTH_SHORT).show();
        }
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length >0&& grantResults[0] == PackageManager.PERMISSION_GRANTED){
                sendSms();
            }
            else{
                Toast.makeText(this, "Permission denied, please allow", Toast.LENGTH_LONG).show();
            }
        }
    }