package com.example.mobiledev.activity;

import androidx.appcompat.app.AppCompatActivity;
import com.example.mobiledev.R;
import android.os.Bundle;
import android.widget.ImageView;

public class DeletedNotes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleted_notes);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        ImageView imageBack = (ImageView) findViewById(R.id.DeleteBackButton);
        imageBack.setOnClickListener((v -> {onBackPressed();}));

    }
}