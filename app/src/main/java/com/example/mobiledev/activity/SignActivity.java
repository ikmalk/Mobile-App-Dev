package com.example.mobiledev.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mobiledev.R;

import androidx.appcompat.app.AppCompatActivity;

public class SignActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        TextView homeButton = (TextView) findViewById(R.id.HomeBtnSignedIn);
        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                openActivityHome();
            }
        });

        Button logoutButton = (Button)findViewById(R.id.LogoutBtn);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    public void openActivityHome(){
        Intent intent = new Intent(this, com.example.mobiledev.activity.MainActivity.class);
        startActivity(intent);
    }

    public void openActivityLogin() {
        Intent intent = new Intent (this, LoginActivity.class);
        startActivity(intent);
    }
}