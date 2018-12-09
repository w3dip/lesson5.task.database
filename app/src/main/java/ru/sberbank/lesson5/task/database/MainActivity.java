package ru.sberbank.lesson5.task.database;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.sberbank.lesson5.task.database.adapters.NoteAdapter;
import ru.sberbank.lesson5.task.database.adapters.NoteEntry;
import ru.sberbank.lesson5.task.database.dao.NoteService;

import static ru.sberbank.lesson5.task.database.adapters.NoteAdapter.noteColor;
import static ru.sberbank.lesson5.task.database.adapters.NoteAdapter.textColor;
import static ru.sberbank.lesson5.task.database.adapters.NoteAdapter.textSize;
import static ru.sberbank.lesson5.task.database.dao.NoteService.getInstance;
import static ru.sberbank.lesson5.task.database.utils.Helpers.getRealProgress;
import static ru.sberbank.lesson5.task.database.utils.Helpers.getSettingName;

public class MainActivity extends Activity {
    private SharedPreferences settings;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<NoteEntry> notes = new ArrayList<>();
    private NoteService noteService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteService = getInstance(this.getApplicationContext());

        findViewById(R.id.createNoteBtn).setOnClickListener((v) -> start(CreateNoteActivity.class));
        findViewById(R.id.settingsBtn).setOnClickListener((v) -> start(SettingsActivity.class));

        mRecyclerView = findViewById(R.id.notes);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        settings = getApplicationContext().getSharedPreferences(getSettingName(getResources(), R.string.settings_filename), MODE_PRIVATE);
        mAdapter = new NoteAdapter(notes);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        textSize = Integer.valueOf(getRealProgress(settings.getInt(getSettingName(getResources(), R.string.text_size_setting), 0)));
        textColor = settings.getInt(getSettingName(getResources(), R.string.text_color_setting), getResources().getColor(R.color.text_color, null));
        noteColor = settings.getInt(getSettingName(getResources(), R.string.note_color_setting), getResources().getColor(R.color.note_color, null));
        notes.clear();
        notes.addAll(noteService.getAll());
        mRecyclerView.setAdapter(null);
        mRecyclerView.setLayoutManager(null);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter.notifyDataSetChanged();
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        noteService.close();
        super.onDestroy();
    }

    public void start(Class<?> cls) {
        startActivity(new Intent(MainActivity.this, cls));
    }
}
