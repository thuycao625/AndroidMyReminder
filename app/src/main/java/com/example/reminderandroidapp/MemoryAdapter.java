package com.example.reminderandroidapp;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MemoryAdapter extends RecyclerView.Adapter<MemoryAdapter.MyViewHolder> {
    List<Memory> memories;
    AppDatabase db;
    OnClick onClick;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_memory, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.tv_title.setText(memories.get(position).title);
        holder.tv_date.setText(memories.get(position).date);

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        String memoryDate = memories.get(position).date;
        String[] dates = memoryDate.split("/");
        int memoryDay = Integer.parseInt(dates[0]);
        int memoryMonth = Integer.parseInt(dates[1]);
        int memoryYear = Integer.parseInt(dates[2]);




        if (currentDay == memoryDay && currentMonth + 1 == memoryMonth) {
            holder.tv_check.setText("Hom nay");
            holder.tv_check.setTextColor(Color.parseColor("#00FF00"));
        }
        if (currentDay < memoryDay && currentMonth + 1 == memoryMonth) {
            holder.tv_check.setText("Sap toi");
            holder.tv_check.setTextColor(Color.parseColor("#00FF00"));
        }
        if (currentMonth + 1 < memoryMonth) {
            holder.tv_check.setText("Sap toi");
            holder.tv_check.setTextColor(Color.parseColor("#0000ff"));
        }
        if (currentMonth + 1 > memoryMonth) {
            holder.tv_check.setText("Da qua");
            holder.tv_check.setTextColor(Color.parseColor("#A4A4A4"));
        }
        if (currentDay > memoryDay && currentMonth + 1 == memoryMonth) {
            holder.tv_check.setText("Da qua");
            holder.tv_check.setTextColor(Color.parseColor("#A4A4A4"));
        }

        holder.tv_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onDelete(position);
            }
        });
    }

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    @Override
    public int getItemCount() {
        if (memories == null) {
            return 0;

        }
        return memories.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_date;
        TextView tv_check;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_date = itemView.findViewById(R.id.tv_date);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_check = itemView.findViewById(R.id.tv_check);


        }


    }

    public interface OnClick{
        void onDelete(int position);
    }

}
