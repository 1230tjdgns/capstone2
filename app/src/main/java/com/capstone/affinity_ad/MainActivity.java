package com.capstone.affinity_ad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    SettingList settingList;
    ProductLoadHandler productLoadHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settingList = new SettingList(getApplicationContext());
        productLoadHandler = settingList.productLoadHandler;
        Intent i = new Intent(getApplicationContext(), HomeActivity.class);

        Thread ProductLoadThread = new Thread() {
            @Override
            public void run() {
                while(true) {
                    if (productLoadHandler.done) {
                        startActivity(i);
                        finish();
                        break;
                    }
                }
            }
        };

        ProductLoadThread.start();


    }


}