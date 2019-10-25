package com.example.reminderandroidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class AddMemories extends AppCompatActivity {
    Button bt_save,bt_date;
    EditText edt_title;
    TextView tv_date;
    AppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memories);

        bt_date = findViewById(R.id.bt_date);
        bt_save = findViewById(R.id.bt_save);
        edt_title = findViewById(R.id.edt_title);
        tv_date = findViewById(R.id.tv_date);

        bt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimePicker();
            }
        });
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveMemoryToDatabase();
                finish();
            }
        });

    }

    void saveMemoryToDatabase(){
        String title = edt_title.getText().toString();
        String date = tv_date.getText().toString();
        final Memory memory = new Memory(title,date);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "database-name").build();
                db.memoryDao().insert(memory);
                return null;
            }
        }.execute();



    }




    void showDateTimePicker() {

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tv_date.setText(dayOfMonth + "/" + (month+1) + "/" + year);
            }
        }, currentYear, currentMonth, currentDay);
        dialog.show();
    }
}
