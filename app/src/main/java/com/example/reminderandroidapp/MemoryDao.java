package com.example.reminderandroidapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MemoryDao {
    @Query("SELECT * FROM memory")
    List<Memory> getAll();

    @Insert
    void insert(Memory memory);

    @Delete
    void delete(Memory memory);
}