package com.example.smartpantry;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
public class SettingsActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle b){
        super.onCreate(b);setContentView(R.layout.activity_settings);
        Switch s=findViewById(R.id.switchExpiry);
        SharedPreferences p=getSharedPreferences("settings",MODE_PRIVATE);
        s.setChecked(p.getBoolean("expiryAlerts",true));
        s.setOnCheckedChangeListener((button,checked)->p.edit().putBoolean("expiryAlerts",checked).apply());
    }
}