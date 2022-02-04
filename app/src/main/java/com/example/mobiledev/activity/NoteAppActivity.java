package com.example.mobiledev.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

    private Note updateNote;
    private String titleT;
    private String textT;

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
        ImageView imageAddSize = (ImageView) findViewById(R.id.imageAddSizeFont);
        ImageView imageMinusSize = (ImageView) findViewById(R.id.imageMinusSizeFont);

        Spinner fontSpinner = (Spinner) findViewById(R.id.noteFontSpinner);
        ArrayAdapter<CharSequence> fsAdapter = ArrayAdapter.createFromResource(
                getBaseContext(),
                R.array.font,
                R.layout.spinner_layout

        );
        fontSpinner.setAdapter(fsAdapter);

        fontSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        // Ubuntu
                        break;

                    case 2:
                        // Times New Roman
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        fontImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                fontSpinner.setVisibility(Spinner.VISIBLE);
                fontSpinner.performClick();
            }
        });

        fontSize.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(!fontSize.getText().toString().equals("")){
                    float size = Float.parseFloat(fontSize.getText().toString());
                    inputNoteText.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);

                }else{
                    fontSize.setText("1");
                    inputNoteText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 1);
                }
            }
        });

        imageAddSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fontSize.getText().toString().equals("")){
                    fontSize.setText("1");
                }else{
                    int size = Integer.parseInt(fontSize.getText().toString()) + 1;
                    fontSize.setText(Integer.toString(size));
                }
            }
        });

        imageMinusSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fontSize.getText().toString().equals("")){
                    fontSize.setText("1");
                }else{
                    int size = Integer.parseInt(fontSize.getText().toString()) - 1;
                    fontSize.setText(Integer.toString(size));
                }
            }
        });

        date.setText(
                new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault())
                .format(new Date())
        );

        ImageView imageSave = (ImageView) findViewById(R.id.imageSave);
        imageSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
                openMainActivity(MainActivity.REQUEST_ADD_NOTE);
            }
        });


        if (getIntent().getBooleanExtra("isUpdate", false)) {
            updateNote = (Note) getIntent().getSerializableExtra("note");
            setUpdateNote();
        }

    }

    private void setUpdateNote(){
        inputNoteTitle.setText(updateNote.getTitle());
        inputNoteText.setText(updateNote.getText());
        date.setText(updateNote.getDate());
        fontSize.setText(updateNote.getFont_size()+"");

        textT = updateNote.getText();
        titleT = updateNote.getText();

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

        if(updateNote != null){
            note.setId(updateNote.getId());

            if(!textT.equals(inputNoteText.getText().toString()) &&
                    !titleT.equals(inputNoteTitle.getText().toString())){
                note.setDate(date.getText().toString());
            }

        }

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

    private void openMainActivity(int req){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("noteapp", req+"");
        startActivity(intent);
    }



}