package com.capstone.affinity_ad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    SettingList settingList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settingList=new SettingList(getApplicationContext());
        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(i);
    }
}