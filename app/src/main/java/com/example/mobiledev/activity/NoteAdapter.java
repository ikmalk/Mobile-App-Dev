package com.example.mobiledev.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobiledev.R;
import com.example.mobiledev.entities.Note;

import org.w3c.dom.Text;

import java.util.List;


public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder>{

    private List<Note> notes;

    public NoteAdapter(List<Note> notes){
        this.notes = notes;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.note_view,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        holder.setNote(notes.get(position));
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

        public NoteHolder(@NonNull View itemView) {
            super(itemView);

            textTitle = (TextView)itemView.findViewById(R.id.noteTextTitle);
            textOverview = (TextView) itemView.findViewById(R.id.noteTextOverview);
            textDate = (TextView) itemView.findViewById(R.id.noteDate);

        }

        public void setNote(Note note){
            textTitle.setText(note.getTitle());

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

            textDate.setText(note.getDate());

        }

    }

}
