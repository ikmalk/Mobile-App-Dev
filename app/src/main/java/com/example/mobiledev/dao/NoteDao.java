package com.example.mobiledev.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mobiledev.entities.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM notes WHERE isDeleted ='No' ORDER BY date DESC")
    List<Note> getAllNotesByDate();

    @Query("SELECT * FROM notes WHERE isDeleted ='No' ORDER BY title")
    List<Note> getAllNotesByName();

    @Query("SELECT * FROM notes WHERE isDeleted ='Yes' ORDER BY date DESC")
    List<Note> getAllDeletedNotes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(Note note);

    @Delete
    void deleteNote(Note note);

}
