package ru.sberbank.lesson5.task.database.adapters;

public class NoteEntry {
    private Long id;
    private String content;

    private NoteEntry(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public static NoteEntry of(Long id, String content) {
        return new NoteEntry(id, content);
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
