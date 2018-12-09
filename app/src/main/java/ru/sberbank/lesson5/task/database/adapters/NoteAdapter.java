package ru.sberbank.lesson5.task.database.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.sberbank.lesson5.task.database.EditNoteActivity;
import ru.sberbank.lesson5.task.database.R;
import ru.sberbank.lesson5.task.database.dao.NoteContract.Note;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private static final int MAX_NOTE_LENGTH = 140;

    private List<NoteEntry> notes;

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        public Long id;
        public String allText;
        public TextView note;
        public NoteViewHolder(View v) {
            super(v);
            note = v.findViewById(R.id.notes_item_content);
            note.setOnClickListener((item) -> {
                Context context = item.getRootView().getContext();
                Intent intent = new Intent(context, EditNoteActivity.class);
                intent.putExtra(Note._ID, id);
                intent.putExtra(Note.COLUMN_NAME_CONTENT, allText);
                context.startActivity(intent);
            });
        }
    }

    public NoteAdapter(List<NoteEntry> notes) {
        this.notes = notes;
    }

    @Override
    public NoteAdapter.NoteViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        return new NoteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.note, parent, false));
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        NoteEntry entry = notes.get(position);
        holder.id = entry.getId();
        String allText = entry.getContent();
        holder.allText = allText;
        holder.note.setText(allText.length() > MAX_NOTE_LENGTH ? allText.substring(0, MAX_NOTE_LENGTH) + "..." : allText);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}
