package com.example.mobiledev.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.mobiledev.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;


public class SettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        ImageView imageBack = (ImageView) findViewById(R.id.SettingBackButton);
        imageBack.setOnClickListener((v) -> {onBackPressed();});

        Spinner SPFontSize = findViewById(R.id.SPFontSize);
        ArrayAdapter<CharSequence> FSadapter = ArrayAdapter.createFromResource(
                getBaseContext(),
                R.array.fontsize,
                android.R.layout.simple_spinner_item);
        SPFontSize.setAdapter(FSadapter);

        Spinner SPSort = findViewById(R.id.SPSort);
        ArrayAdapter<CharSequence> Sadapter = ArrayAdapter.createFromResource(
                this.getBaseContext(),
                R.array.sort,
                android.R.layout.simple_spinner_item);
        SPSort.setAdapter(Sadapter);

        ImageButton IMGBTNDeleted = findViewById(R.id.IMGBTNDeleted);
        View.OnClickListener OCLDeleted = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openDeletedActivity();
            }
        };
        IMGBTNDeleted.setOnClickListener(OCLDeleted);

        Switch SWDarkMode = findViewById(R.id.SWDarkMode);
        SharedPreferences sharedPreferences = null;
        sharedPreferences = this.getSharedPreferences("night",0);
        Boolean booleanValue = sharedPreferences.getBoolean("night_mode", true);

        if(booleanValue){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            SWDarkMode.setChecked(true);
        }

        SharedPreferences finalSharedPreferences = sharedPreferences;
        SWDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    SWDarkMode.setChecked(true);
                    SharedPreferences.Editor editor = finalSharedPreferences.edit();
                    editor.putBoolean("night_mode",true);
                    editor.commit();
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    SWDarkMode.setChecked(false);
                    SharedPreferences.Editor editor = finalSharedPreferences.edit();
                    editor.putBoolean("night_mode",false);
                    editor.commit();
                }
            }
        });

    }

    private void openDeletedActivity(){
        Intent intent = new Intent(this, DeletedNotes.class);
        startActivity(intent);
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        try {
//            Navigation.findNavController(this, R.id.NHFMain).navigate(item.getItemId());
//            return true;
//        }catch (Exception ex){
//            return super.onOptionsItemSelected(item);
//        }
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        return Navigation.findNavController(this, R.id.NHFMain).navigateUp();
//    }
}

