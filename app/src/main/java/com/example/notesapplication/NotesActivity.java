package com.example.notesapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.notesapplication.data.DatabaseHelper;
import com.example.notesapplication.model.Note;

import java.util.Arrays;
import java.util.List;

public class NotesActivity extends AppCompatActivity implements NotesAdapter.NoteClickListener {

    NotesAdapter adapter;
    DatabaseHelper db;
    //get this from DB.
    List<Note> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        db = new DatabaseHelper(this,null, null, 1);
        items = db.fetchAllNotes();
        RecyclerView notesRecyclerView = findViewById(R.id.notes_list);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NotesAdapter(this, items);
        adapter.setClickListener((NotesAdapter.NoteClickListener)this);
        notesRecyclerView.setAdapter(adapter);

    }

    @Override
    protected void onResume(){
        items = db.fetchAllNotes();

        // To be honest, i think this is wrong. There should be a way to redraw the recyclerView without having to completely reset the adapter, but i couldnt figure it out.
        RecyclerView notesRecyclerView = findViewById(R.id.notes_list);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NotesAdapter(this, items);
        adapter.setClickListener((NotesAdapter.NoteClickListener)this);
        notesRecyclerView.setAdapter(adapter);

        super.onResume();

    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
        Note note = items.get(position);
        intent.putExtra("NOTE", note);
        startActivity(intent);
    }
}