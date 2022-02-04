package com.example.mobiledev.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobiledev.R;
import com.example.mobiledev.database.NoteDatabase;
import com.example.mobiledev.entities.Note;

import java.util.List;


public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder>{

    private List<Note> notes;
    private MainActivity mainAct;
    private DeletedNotes delNote;
    private int textSize;
    private int titleSize;

    public NoteAdapter(List<Note> notes, MainActivity mainAct, DeletedNotes delNote){

        if(mainAct != null){
            this.mainAct = mainAct;
        }
        if(delNote != null){
            this.delNote = delNote;
        }
        this.notes = notes;
        setSize();
    }


    private void setSize(){
        String textSizeState;

        if(mainAct != null){
             textSizeState = mainAct.getStateTextSize();
            switch (textSizeState.toLowerCase()){
                case "small":
                    textSize = mainAct.getResources().getInteger(R.integer.text_font_size_small);
                    titleSize = mainAct.getResources().getInteger(R.integer.title_font_size_small);
                    break;
                case "medium":
                    textSize = mainAct.getResources().getInteger(R.integer.text_font_size_medium);
                    titleSize = mainAct.getResources().getInteger(R.integer.title_font_size_medium);
                    break;

                case "large":
                    textSize = mainAct.getResources().getInteger(R.integer.text_font_size_large);
                    titleSize = mainAct.getResources().getInteger(R.integer.title_font_size_large);
                    break;
            }
        }else{
             textSizeState = delNote.getStateTextSize();
            switch (textSizeState.toLowerCase()){
                case "small":
                    textSize = delNote.getResources().getInteger(R.integer.text_font_size_small);
                    titleSize = delNote.getResources().getInteger(R.integer.title_font_size_small);
                    break;
                case "medium":
                    textSize = delNote.getResources().getInteger(R.integer.text_font_size_medium);
                    titleSize = delNote.getResources().getInteger(R.integer.title_font_size_medium);
                    break;

                case "large":
                    textSize = delNote.getResources().getInteger(R.integer.text_font_size_large);
                    titleSize = delNote.getResources().getInteger(R.integer.title_font_size_large);
                    break;
            }
        }

    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.note_view,
                        parent,
                        false

                ), parent.getContext(), mainAct, delNote,  titleSize, textSize
        );
    }





    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        holder.setNote(notes.get(position), position);
//        holder.layoutNote.setOnClickListener(view -> noteListener.onNoteClicked(notes.get(position), position));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class NoteHolder extends RecyclerView.ViewHolder{

        private TextView textTitle, textOverview, textDate;
        private AlertDialog deleteView;
        private ImageView deleteImg;
        private Context context;
        private View item;
        private MainActivity mainAct;
        private DeletedNotes delNote;
        LinearLayout layoutNote;
        private int titleSize;
        private int textSize;

        public NoteHolder(@NonNull View itemView, Context ctt, MainActivity mAct,
                          DeletedNotes dNote, int ttSize,
                          int txtSize) {
            super(itemView);

            textTitle = (TextView)itemView.findViewById(R.id.noteTextTitle);
            textOverview = (TextView) itemView.findViewById(R.id.noteTextOverview);
            textDate = (TextView) itemView.findViewById(R.id.noteDate);
            deleteImg = (ImageView) itemView.findViewById(R.id.note_delete);
            layoutNote = (LinearLayout) itemView.findViewById(R.id.noteLayout);

            context = ctt;
            item = itemView;
            mainAct = mAct;
            delNote = dNote;


            titleSize = ttSize;
            textSize = txtSize;

        }

        private void showDeletedNoteDialog(Note note, int position){
            if(deleteView == null){
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = LayoutInflater.from(context).inflate(
                        R.layout.delete_note_view,
                        (ViewGroup) item.findViewById(R.id.dlv_layout)
                );
                builder.setView(view);
                deleteView = builder.create();
                if(deleteView.getWindow() != null){
                    deleteView.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                view.findViewById(R.id.dlv_delete).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        class DeleteNote extends AsyncTask<Void, Void, Void> {

                            @Override
                            protected Void doInBackground(Void... voids) {
                                note.setIsDeleted("Yes");

                                NoteDatabase.getDatabase(context).noteDao()
                                        .insertNote(note);


                                return null;
                            }

                        }

                        new DeleteNote().execute();
                        deleteView.dismiss();
                        mainAct.setNoteClickedPos(position);

                        mainAct.getNotes(MainActivity.REQUEST_DELETE_NOTE, true);

                    }
                });

                view.findViewById(R.id.dlv_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteView.dismiss();
                    }
                });
            }

            deleteView.show();
        }

        public void setNote(Note note, int position){
            textTitle.setText(note.getTitle());
            textTitle.setOnClickListener(view -> mainAct.onNoteClicked(note, position));
            textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleSize);

            String text = note.getText();

            String overview = "";

            if(text.length() > 13){
                for(int i = 0; i < 13; i++){
                    overview += text.charAt(i);
                }
                overview += "...";
            }else{
                overview = text;
            }

            textOverview.setText(overview);
            textOverview.setOnClickListener(view -> mainAct.onNoteClicked(note, position));
            textOverview.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);

            textDate.setText(note.getDate());

            deleteImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDeletedNoteDialog(note, position);
                }
            });

        }

    }

}
