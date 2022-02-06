package com.example.mobiledev.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.mobiledev.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        TextView homeButton = (TextView) findViewById(R.id.HomeBtnLogin);
        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                openActivityHome();
            }
        });

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Boolean booleanValue = sharedPreferences.getBoolean("night_mode", true);

        if(booleanValue){
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//            SWDarkMode.setChecked(true);
        }
    }

    public void openActivityHome(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}