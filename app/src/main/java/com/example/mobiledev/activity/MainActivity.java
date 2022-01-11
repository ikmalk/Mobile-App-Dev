package com.example.mobiledev.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.ReceiverCallNotAllowedException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.mobiledev.R;
import com.example.mobiledev.database.NoteDatabase;
import com.example.mobiledev.entities.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_NOTE = 1;
    public static final int REQUEST_OVERWRITE_NOTE = 2;

    private RecyclerView noteRecyclerView;
    private NoteAdapter noteAdapter;
    private List<Note> noteList;
    private int create;
    private ActivityResultLauncher<Intent> launchSomeActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        create = 1;

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

        noteRecyclerView = (RecyclerView) findViewById(R.id.mainRecyclerView);
        noteRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        );

        noteList = new ArrayList<>();
        noteAdapter = new NoteAdapter(noteList);
        noteRecyclerView.setAdapter(noteAdapter);

        getNotes();

    }

    private void getNotes(){
        class GetNoteTask extends AsyncTask<Void, Void, List<Note>> {

            @Override
            protected List<Note> doInBackground(Void... voids) {
                return NoteDatabase.getDatabase(getApplicationContext())
                        .noteDao().getAllNotes();
            }

            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);

                if(noteList.size() == 0){
                    noteList.addAll(notes);
                    noteAdapter.notifyDataSetChanged();
                }else{
                    noteList.add(0, notes.get(0));
                    noteAdapter.notifyItemInserted(0);
                }
                noteRecyclerView.smoothScrollToPosition(0);
            }
        }
        new GetNoteTask().execute();
    }


    private void openActivitySign(){
        Intent intent = new Intent(this, SignActivity.class);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            getNotes();
        }

    }
}