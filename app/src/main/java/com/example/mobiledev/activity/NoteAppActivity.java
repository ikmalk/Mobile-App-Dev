package com.example.mobiledev.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobiledev.database.NoteDatabase;
import com.example.mobiledev.R;
import com.example.mobiledev.entities.Note;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NoteAppActivity extends AppCompatActivity {

    private EditText inputNoteTitle, inputNoteText, fontSize;
    private TextView date;
    private ImageView colorCircle, fontImage;

    public static String state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_app);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }



        ImageView imageBack = (ImageView) findViewById(R.id.imageBack);
        imageBack.setOnClickListener((v) -> {onBackPressed();});

        inputNoteTitle = (EditText) findViewById(R.id.inputNoteTitle);
        inputNoteText = (EditText) findViewById(R.id.inputNote);
        date = findViewById(R.id.textDateTime);
        Typeface typeface = inputNoteText.getTypeface();


        fontSize = (EditText) findViewById(R.id.inputFontSize);
        colorCircle = (ImageView) findViewById(R.id.imageColorChange);
        fontImage = (ImageView) findViewById(R.id.imageFont);



        date.setText(
                new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault())
                .format(new Date())
        );

        ImageView imageSave = (ImageView) findViewById(R.id.imageSave);
        imageSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
                openMainActivity();
            }
        });

    }

    private void saveNote(){
        if(inputNoteTitle.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Title can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }else if(inputNoteText.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Note can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        final Note note = new Note();
        note.setTitle(inputNoteTitle.getText().toString());
        note.setText(inputNoteText.getText().toString());
        note.setDate(date.getText().toString());
        note.setFont_size(Integer.parseInt(fontSize.getText().toString()));


        class SaveNoteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                NoteDatabase.getDatabase(getApplicationContext()).noteDao().insertNote(note);
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);

                finish();
            }
        }

        new SaveNoteTask().execute();

    }

    private void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}