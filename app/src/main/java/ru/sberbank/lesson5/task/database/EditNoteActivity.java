package ru.sberbank.lesson5.task.database;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ru.sberbank.lesson5.task.database.dao.NoteContract.Note;
import ru.sberbank.lesson5.task.database.dao.NoteService;

import static ru.sberbank.lesson5.task.database.dao.NoteService.getInstance;

public class EditNoteActivity extends Activity {
    private NoteService noteService;
    private TextView editNoteTextView;
    private Long currentNoteId;
    private View saveNoteBtn;
    private View editModeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        noteService = getInstance(this.getApplicationContext());

        editNoteTextView = findViewById(R.id.editNoteText);

        Intent intent = getIntent();
        editNoteTextView.setText(intent.getStringExtra(Note.COLUMN_NAME_CONTENT));
        currentNoteId = intent.getLongExtra(Note._ID, 0);

        saveNoteBtn = findViewById(R.id.saveNoteBtn);
        editModeBtn = findViewById(R.id.editModeBtn);

        findViewById(R.id.saveNoteBtn).setOnClickListener((v) -> {
            String text = editNoteTextView.getText().toString();
            if (text.length() > 0) {
                noteService.update(currentNoteId, text);
                finish();
            }
            Toast.makeText(v.getContext(), R.string.empty_text, Toast.LENGTH_SHORT).show();
        });
        findViewById(R.id.editModeBtn).setOnClickListener((v) -> {
            editNoteTextView.setFocusableInTouchMode(true);
            saveNoteBtn.setEnabled(true);
            editModeBtn.setEnabled(false);
        });
        findViewById(R.id.cancelEditNoteBtn).setOnClickListener((v) -> {
            finish();
        });
    }
}
