package com.example.notesapplication.data;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.notesapplication.model.Note;
import com.example.notesapplication.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, Util.DATABASE_NAME, factory, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTE_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "(" + Util.ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + Util.TEXT + " TEXT)";
        db.execSQL(CREATE_NOTE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_NOTE_TABLE = String.format("DROP TABLE IF EXISTS");
        db.execSQL(DROP_NOTE_TABLE, new String[]{Util.TABLE_NAME});

        onCreate(db);
    }

    public long insertNote (String text){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.TEXT, text);
        long newRowId = db.insert(Util.TABLE_NAME, null, contentValues);
        db.close();
        return newRowId;
    }

    public long updateNote (Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.TEXT, note.getText());
        long newRowId = db.update(Util.TABLE_NAME, contentValues, Util.ID + "=?",new String[] {String.valueOf(note.getId())});
        db.close();
        return newRowId;
    }

    public Note fetchNote (String id){

        Note note = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.TABLE_NAME, new String[] {Util.ID}, Util.ID + "=?", new String[] {id},null, null,null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast() && note == null) {
                String noteId = cursor.getString(cursor.getColumnIndex(Util.ID));
                String text = cursor.getString(cursor.getColumnIndex(Util.TEXT));
                note = new Note(Integer.parseInt(noteId), text);
                cursor.moveToNext();
            }
        }
        db.close();

        return note;
    }

    public List<Note> fetchAllNotes (){

        List<Note> notes = new ArrayList<Note>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.TABLE_NAME, null, null, null,null, null,null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String id = cursor.getString(cursor.getColumnIndex(Util.ID));
                String text = cursor.getString(cursor.getColumnIndex(Util.TEXT));
                Note tempNote = new Note(Integer.parseInt(id), text);
                notes.add(tempNote);
                cursor.moveToNext();
            }
        }
        db.close();

        return notes;
    }

    public boolean deleteNote (String id){

        SQLiteDatabase db = this.getReadableDatabase();
        int rowsDeleted = 0;
        rowsDeleted = db.delete(Util.TABLE_NAME, Util.ID + "=?", new String[] {id});

        db.close();

        if(rowsDeleted > 0){
            return true;
        }else{
            return false;
        }
    }
}
