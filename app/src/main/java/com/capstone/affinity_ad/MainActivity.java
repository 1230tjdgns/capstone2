package com.capstone.affinity_ad;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    SettingList settingList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settingList=new SettingList(getApplicationContext());

        startLoading();



    }
    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(i);
                Log.d(TAG, " :::::::::::::"+settingList.l_brand.get(0));
            }
        }, 3000);
    }
}