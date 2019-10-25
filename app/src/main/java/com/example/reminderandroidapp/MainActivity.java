package com.example.reminderandroidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button bt_add;
    RecyclerView recyclerView;
    AppDatabase db;
    RecyclerView.LayoutManager layoutManager;
    MemoryAdapter memoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_add = findViewById(R.id.bt_add);

        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        memoryAdapter = new MemoryAdapter();
        recyclerView.setAdapter(memoryAdapter);

        memoryAdapter.setOnClick(new MemoryAdapter.OnClick() {
            @Override
            public void onDelete(final int position) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        db.memoryDao().delete(memoryAdapter.memories.get(position));
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        memoryAdapter.memories.remove(position);
                        memoryAdapter.notifyDataSetChanged();
                    }
                }.execute();
            }
        });

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddScreen();
            }
        });
    }

    void openAddScreen() {
        Intent intent = new Intent(MainActivity.this, AddMemories.class);
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getMemory();
    }

    @SuppressLint("StaticFieldLeak")
    void getMemory() {
        new AsyncTask<Void, Void, List<Memory>>() {
            @Override
            protected List<Memory> doInBackground(Void... voids) {
                db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "database-name").build();

                List<Memory> memories = db.memoryDao().getAll();
                return memories;
            }

            @Override
            protected void onPostExecute(List<Memory> memories) {
                super.onPostExecute(memories);
                memoryAdapter.memories = memories;
                memoryAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}
