package ru.sberbank.lesson5.task.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ru.sberbank.lesson5.task.database.adapters.NoteEntry;
import ru.sberbank.lesson5.task.database.dao.NoteContract.Note;

public class NoteService {

    private NoteDbHelper mNoteDbHelper;
    private SQLiteDatabase notesDb;

    private static NoteService noteService;

    private NoteService(Context context) {
        mNoteDbHelper = new NoteDbHelper(context);
        notesDb = mNoteDbHelper.getWritableDatabase();
    }

    public static NoteService getInstance(Context context) {
        return noteService == null ? new NoteService(context) : noteService;
    }

    public List<NoteEntry> getAll() {
        List<NoteEntry> result = new ArrayList<>();
        try (Cursor cursor = notesDb.query(Note.TABLE_NAME, new String[] {Note._ID, Note.COLUMN_NAME_CONTENT},null,null,null,null,null)) {
            while(cursor.moveToNext()) {
                result.add(NoteEntry.of(
                        cursor.getLong(cursor.getColumnIndexOrThrow(Note._ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Note.COLUMN_NAME_CONTENT))));
            }
        }
        return result;
    }

    public void create(String text) {
        ContentValues values = new ContentValues();
        values.put(Note.COLUMN_NAME_CONTENT, text);
        notesDb.beginTransaction();
        notesDb.insert(Note.TABLE_NAME, null, values);
        notesDb.setTransactionSuccessful();
        notesDb.endTransaction();
    }

    public void update(Long id, String text) {
        ContentValues values = new ContentValues();
        values.put(Note.COLUMN_NAME_CONTENT, text);
        notesDb.beginTransaction();
        notesDb.update(Note.TABLE_NAME, values, Note._ID + " LIKE ?", new String[] {id.toString()});
        notesDb.setTransactionSuccessful();
        notesDb.endTransaction();
    }

    public void close() {
        notesDb.close();
        mNoteDbHelper.close();
    }
}
