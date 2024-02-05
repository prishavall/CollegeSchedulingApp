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
import com.example.emptyactivityapp.Utils.DataBaseHandlerSchedule;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.Collections;
import java.util.List;

public class Schedule extends AppCompatActivity implements DialogCloseListener {

    private RecyclerView schedulesRecyclerView;
    private ScheduleAdapter schedulesAdapter;
    private ExtendedFloatingActionButton efab;
    private DataBaseHandlerSchedule dbs;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setUpDatabases();

        schedulesStuff();

        efab = findViewById(R.id.newScheduleButton);

        itemTouchWork();

        efab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AddNewSchedule.newInstance().show(getSupportFragmentManager(), AddNewSchedule.TAG);
            }
        });
        loadSchedules();
    }

    private void setUpDatabases() {
        dbs = new DataBaseHandlerSchedule(this);
        dbs.openDatabase();
    }

    private void schedulesStuff() {
        schedulesRecyclerView = findViewById(R.id.schedulesRecyclerView);
        schedulesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        schedulesAdapter = new ScheduleAdapter(this);
        schedulesRecyclerView.setAdapter(schedulesAdapter);
    }

    private void itemTouchWork() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelperSchedules(schedulesAdapter));
        itemTouchHelper.attachToRecyclerView(schedulesRecyclerView);
    }
    private void loadSchedules() {
        List<ScheduleModel> scheduleList = dbs.getAllSchedules();
        Collections.reverse(scheduleList);
        schedulesAdapter.setSchedules(scheduleList);
    }

    public void handleDialogClose(DialogInterface dialog) {
        loadSchedules();
    }
}