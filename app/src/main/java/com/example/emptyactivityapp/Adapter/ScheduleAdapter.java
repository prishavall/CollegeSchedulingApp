package com.example.emptyactivityapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emptyactivityapp.AddNewSchedule;
import com.example.emptyactivityapp.AddNewSchedule;
import com.example.emptyactivityapp.Model.ScheduleModel;
import com.example.emptyactivityapp.Schedule;
import com.example.emptyactivityapp.Model.ScheduleModel;
import com.example.emptyactivityapp.R;
import com.example.emptyactivityapp.Utils.DataBaseHandler;
import com.example.emptyactivityapp.Utils.DataBaseHandlerSchedule;
import com.example.emptyactivityapp.Utils.DataBaseHandlerSchedule;

import java.util.List;
import android.util.Log;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
    private List<ScheduleModel> scheduleList;
    private Schedule activity;
    private DataBaseHandlerSchedule db;

    public ScheduleAdapter(Schedule activity) {
        this.activity = activity;
        this.db = new DataBaseHandlerSchedule(activity);
        this.db.openDatabase();
    }


    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context contexto = parent.getContext();
        LayoutInflater inflatable = LayoutInflater.from(contexto);
        View view = inflatable.inflate(R.layout.schedule_card, parent, false);
        return new ViewHolder(view);
    }


    public void onBindViewHolder(ViewHolder vh, int position) {
        ScheduleModel schedule = scheduleList.get(position);
        vh.textScheduleName.setText(schedule.getScheduleName());
        vh.textScheduleLocation.setText(schedule.getScheduleLocation());
        vh.textScheduleDateTime.setText(schedule.getFormattedDateTime());
    }


    public void setSchedules(List<ScheduleModel> schedules) {
        if (schedules != null) {
            this.scheduleList = schedules;
            notifyDataSetChanged();
        }
    }

    public int getItemCount() {
        if (scheduleList == null) {
            return 0;
        }
        return scheduleList.size();
    }

    public Context getContext() {
        return activity;
    }

    public void deleteItem(int position) {
        if (db != null) {
            ScheduleModel schedule = scheduleList.get(position);
            int sID = schedule.getId();
            db.deleteSchedule(sID);
            scheduleList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void editItem(int position) {
        ScheduleModel schedule = scheduleList.get(position);
        Bundle bundles = new Bundle();
        bundles.putInt("id", (Integer) schedule.getId());
        bundles.putString("scheduleName", schedule.getScheduleName());
        bundles.putString("location", schedule.getScheduleLocation());
        bundles.putString("date", schedule.getScheduleDate());
        bundles.putString("time", schedule.getScheduleTime());

        AddNewSchedule fragment = new AddNewSchedule();
        fragment.setArguments(bundles);
        fragment.show(activity.getSupportFragmentManager(), AddNewSchedule.TAG);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textScheduleName;
        private TextView textScheduleLocation;
        private TextView textScheduleDateTime;

        ViewHolder(View view) {
            super(view);
            textScheduleName = view.findViewById(R.id.textScheduleName);
            textScheduleLocation = view.findViewById(R.id.textScheduleLocation);
            textScheduleDateTime = view.findViewById(R.id.textScheduleDateTime);
        }
    }
}