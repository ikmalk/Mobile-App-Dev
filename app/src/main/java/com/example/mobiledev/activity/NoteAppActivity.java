package com.example.mobiledev.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

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
    private ImageView colorCircle, fontImage, addImage;

    private Note updateNote;
    private String titleT;
    private String textT;
    private boolean onCreate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_app);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        onCreate = true;

        ImageView imageBack = (ImageView) findViewById(R.id.imageBack);
        imageBack.setOnClickListener((v) -> {onBackPressed();});

        inputNoteTitle = (EditText) findViewById(R.id.inputNoteTitle);
        inputNoteText = (EditText) findViewById(R.id.inputNote);
        date = findViewById(R.id.textDateTime);


        fontSize = (EditText) findViewById(R.id.inputFontSize);
        colorCircle = (ImageView) findViewById(R.id.imageColorChange);
        fontImage = (ImageView) findViewById(R.id.imageFont);
        ImageView imageAddSize = (ImageView) findViewById(R.id.imageAddSizeFont);
        ImageView imageMinusSize = (ImageView) findViewById(R.id.imageMinusSizeFont);
        addImage = (ImageView) findViewById(R.id.imageImage);

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
                if(!onCreate){
                    setFontTypeface(i, inputNoteText);
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
                new SimpleDateFormat("dd MMMM yyyy HH:mm a", Locale.getDefault())
                .format(new Date())
        );

        ImageView imageSave = (ImageView) findViewById(R.id.imageSave);
        imageSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!getIntent().getBooleanExtra("isUpdate", false)) {
                    saveNote(MainActivity.REQUEST_ADD_NOTE);
                }
                else{
                    saveNote(MainActivity.REQUEST_OVERWRITE_NOTE);
                }

            }
        });

        setFontTypeface(3, inputNoteText);


        if (getIntent().getBooleanExtra("isUpdate", false)) {
            updateNote = (Note) getIntent().getSerializableExtra("note");
            setUpdateNote();
        }

        onCreate = false;

    }

    private void setFontTypeface(int i, EditText et){
        /*
                <item>Alegreya</item>
                <item>Arvo</item>
                <item>IBM Plex Sans</item>
                <item>Lora</item>
                <item>Merriweather Sans</item>
                <item>Nunito</item>
                <item>Roboto</item>
                <item>Roboto Slab</item>
                <item>Rubik</item>
                <item>Space Mono</item>
                <item>Ubuntu</item>
                <item>Vesper Libre</item>
                 */

        Typeface type;
        switch (i) {
            case 0:
                // Alegreya
                type = ResourcesCompat.getFont(getApplicationContext(), R.font.alegreya);
                et.setTypeface(type);
                break;

            case 1:
                // Arvo
                type = ResourcesCompat.getFont(getApplicationContext(), R.font.arvo);
                et.setTypeface(type);
                break;
            case 2:
                // IBM Plex Sans
                type = ResourcesCompat.getFont(getApplicationContext(), R.font.ibm_plex_sans);
                et.setTypeface(type);
                break;
            case 3:
                // Lora
                type = ResourcesCompat.getFont(getApplicationContext(), R.font.lora);
                et.setTypeface(type);
                break;
            case 4:
                // Merriweather Sans
                type = ResourcesCompat.getFont(getApplicationContext(), R.font.merriweather_sans);
                et.setTypeface(type);
                break;
            case 5:
                // Nunito
                type = ResourcesCompat.getFont(getApplicationContext(), R.font.nunito);
                et.setTypeface(type);
                break;
            case 6:
                // Roboto
                type = ResourcesCompat.getFont(getApplicationContext(), R.font.roboto);
                et.setTypeface(type);
                break;
            case 7:
                // Roboto Slab
                type = ResourcesCompat.getFont(getApplicationContext(), R.font.roboto_slab);
                et.setTypeface(type);
                break;
            case 8:
                // Rubik
                type = ResourcesCompat.getFont(getApplicationContext(), R.font.rubik);
                et.setTypeface(type);
                break;
            case 9:
                // Space Mono
                type = ResourcesCompat.getFont(getApplicationContext(), R.font.space_mono);
                et.setTypeface(type);
                break;
            case 10:
                // Ubuntu
                type = ResourcesCompat.getFont(getApplicationContext(), R.font.ubuntu);
                et.setTypeface(type);
                break;
            case 11:
                // Vesper Libre
                type = ResourcesCompat.getFont(getApplicationContext(), R.font.vesper_libre);
                et.setTypeface(type);
                break;
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

    private void saveNote(int requestCode){
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
        note.setIsDeleted("No");

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
        openMainActivity(requestCode);

    }

    private void openMainActivity(int req){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("noteapp", req+"");
        startActivity(intent);
    }



}