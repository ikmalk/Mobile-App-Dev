package com.example.mobiledev.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.mobiledev.R;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        TextView accountButton = (TextView) findViewById(R.id.AccountBtn);

        accountButton.setOnClickListener(new View.OnClickListener(){
            /**
             *
             * If statement, if signed go to sign activity
             * if not go to login activity
             */
            @Override
            public void onClick(View V){
                openActivitySign();
            }
        });

        ImageView createNoteButton = (ImageView) findViewById(R.id.imageAdd);
        createNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityNote();
            }
        });

        ImageView settingButton = (ImageView) findViewById(R.id.settingBtn);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingActivity();
            }
        });

    }

    private void openActivitySign(){
        Intent intent = new Intent(this, Sign.class);
        startActivity(intent);
    }

    private void openActivityNote(){
        Intent intent = new Intent(this, NoteAppActivity.class);
        startActivity(intent);
    }

    private void openSettingActivity(){
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

}