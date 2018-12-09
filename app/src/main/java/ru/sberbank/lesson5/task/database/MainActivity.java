package ru.sberbank.lesson5.task.database;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

import java.util.ArrayList;
import java.util.List;

import ru.sberbank.lesson5.task.database.adapters.NoteAdapter;
import ru.sberbank.lesson5.task.database.adapters.NoteEntry;
import ru.sberbank.lesson5.task.database.dao.NoteDbHelper;
import ru.sberbank.lesson5.task.database.dao.NoteService;

import static ru.sberbank.lesson5.task.database.dao.NoteService.getInstance;

public class MainActivity extends Activity {

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

        mAdapter = new NoteAdapter(notes);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        notes.clear();
        notes.addAll(noteService.getAll());
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
