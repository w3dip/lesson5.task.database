package ru.sberbank.lesson5.task.database;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import ru.sberbank.lesson5.task.database.dao.NoteService;

import static ru.sberbank.lesson5.task.database.dao.NoteService.getInstance;

public class CreateNoteActivity extends Activity {
    private NoteService noteService;

    private TextView newNoteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        noteService = getInstance(this.getApplicationContext());

        newNoteTextView = findViewById(R.id.newNoteText);
        findViewById(R.id.newNoteBtn).setOnClickListener((v) -> {
            String text = newNoteTextView.getText().toString();
            if (text.length() > 0) {
                noteService.create(text);
                finish();
            }
            Toast.makeText(v.getContext(), R.string.empty_text, Toast.LENGTH_SHORT).show();
        });
        findViewById(R.id.cancelNewNoteBtn).setOnClickListener((v) -> {
            finish();
        });
    }
}
