package com.example.reminderandroidapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Memory {
    @PrimaryKey (autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "date")
    public String date;

    public Memory(String title, String date) {
        this.title = title;
        this.date = date;
    }
}