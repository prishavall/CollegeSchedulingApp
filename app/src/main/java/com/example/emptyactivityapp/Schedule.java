package com.example.emptyactivityapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.emptyactivityapp.Adapter.ScheduleAdapter;
import com.example.emptyactivityapp.Model.ScheduleModel;
import com.example.emptyactivityapp.RecyclerItemTouchHelperSchedules;
import com.example.emptyactivityapp.Utils.DataBaseHandlerSchedule;
import com.example.emptyactivityapp.Utils.DataBaseHandlerSchedule;
import com.example.emptyactivityapp.Utils.DataBaseHandlerSchedule;
import com.example.emptyactivityapp.Utils.DataBaseHandlerSchedule;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.Collections;
import java.util.List;

public class Schedule extends AppCompatActivity implements DialogCloseListener {

    private RecyclerView schedulesRecyclerView;
    private ScheduleAdapter schedulesAdapter;
    private ExtendedFloatingActionButton fab;
    private DataBaseHandlerSchedule db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        db = new DataBaseHandlerSchedule(this);
        db.openDatabase();

        schedulesRecyclerView = findViewById(R.id.schedulesRecyclerView);
        schedulesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        schedulesAdapter = new ScheduleAdapter(this); // pass the listener
        schedulesRecyclerView.setAdapter(schedulesAdapter);

        fab = findViewById(R.id.newScheduleButton);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelperSchedules(schedulesAdapter));
        itemTouchHelper.attachToRecyclerView(schedulesRecyclerView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewSchedule.newInstance().show(getSupportFragmentManager(), AddNewSchedule.TAG);
            }
        });

        loadSchedules();  // Load schedules when the activity starts
    }

    private void loadSchedules() {
        List<ScheduleModel> scheduleList = db.getAllSchedules();
        Collections.reverse(scheduleList);
        schedulesAdapter.setSchedules(scheduleList);
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        // Reload schedules after adding a new schedule
        loadSchedules();
    }
}