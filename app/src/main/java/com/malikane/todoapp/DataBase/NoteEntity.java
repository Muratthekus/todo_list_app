package com.malikane.todoapp.DataBase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "note_table")
public class NoteEntity {
    @PrimaryKey(autoGenerate = true)
    private int Id;
    private String name;
    private String category;
    private String description;
    public NoteEntity(String name, String category, String description) {
        this.name=name;
        this.description = description;
        this.category = category;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }
}
