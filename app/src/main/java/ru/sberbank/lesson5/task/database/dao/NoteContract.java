package ru.sberbank.lesson5.task.database.dao;

import android.provider.BaseColumns;

public final class NoteContract {

    private NoteContract() {
    }

    public static class Note implements BaseColumns {
        public static final String TABLE_NAME = "notes";
        public static final String COLUMN_NAME_CONTENT = "content";
    }
}
