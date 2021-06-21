package com.pratico4.activdad2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pratico4.activdad2.services.ServiceOne;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void iniciarService(View v){
        Intent intentService= new Intent(MainActivity.this,ServiceOne.class);
        startService(intentService);
    }

    public void detenerService(View v){
        Intent intentService= new Intent(MainActivity.this,ServiceOne.class);
        stopService(intentService);
    }



}