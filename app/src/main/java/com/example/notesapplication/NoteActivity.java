package com.example.notesapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.notesapplication.data.DatabaseHelper;
import com.example.notesapplication.model.Note;

public class NoteActivity extends AppCompatActivity {

    Note note;
    EditText noteText;
    Button saveNoteButton, updateNoteButton, deleteNoteButton;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        db = new DatabaseHelper(this,null,null, 1);
        Intent intent = getIntent();
        note = (Note) intent.getSerializableExtra("NOTE");
        noteText = findViewById(R.id.note_edit_text);
        saveNoteButton = findViewById(R.id.save_note_button);
        updateNoteButton = findViewById(R.id.update_note_button);
        deleteNoteButton = findViewById(R.id.delete_note_button);
        if(note != null && note.getId() > -1) {
            noteText.setText(note.getText());
            saveNoteButton.setVisibility(View.GONE);
        }else {
            updateNoteButton.setVisibility(View.GONE);
            deleteNoteButton.setVisibility(View.GONE);
        }

    }

    public void saveNote(View view) {
        db.insertNote(noteText.getText().toString());
        finish();
    }

    public void updateNote(View view) {
        db.updateNote(new Note(note.getId(), noteText.getText().toString()));
        finish();
    }

    public void deleteNote(View view) {
        db.deleteNote(String.valueOf(note.getId()));
        finish();
    }
}