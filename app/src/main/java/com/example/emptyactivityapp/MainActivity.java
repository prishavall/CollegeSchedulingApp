package com.example.emptyactivityapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager; import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.emptyactivityapp.Adapter.ToDoAdapter; import com.example.emptyactivityapp.Model.ToDoModel;
import com.example.emptyactivityapp.Utils.DataBaseHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List; import java.util.Objects;



public class MainActivity extends AppCompatActivity implements DialogCloseListener{
    private RecyclerView tasksRecyclerView;
    private ToDoAdapter tasksAdapter;
    private FloatingActionButton fab;
    private List<ToDoModel> taskList;
    private DataBaseHandler db;

    BottomNavigationView bottomNavigationView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).hide();
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.todolist);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.simpleschedule) {
                    startActivity(new Intent(getApplicationContext(), Schedule.class));
                    return true;
                } else if (itemId == R.id.todolist) {
                    if (!MainActivity.this.getClass().equals(MainActivity.class)) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                    return true;
                } else if (itemId == R.id.assignments) {

                    startActivity(new Intent(getApplicationContext(), Assignment.class));
                    overridePendingTransition(0, 0);

                    startActivity(new Intent(getApplicationContext(), Assignments.class));


                    return true;
                } else if (itemId == R.id.exams) {
                    startActivity(new Intent(getApplicationContext(), MiddlePage2.class));
                    return true;
                }
                return false;
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        db = new DataBaseHandler(this);
        db.openDatabase();



        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }




        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }





        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        taskList = new ArrayList<>();
        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new ToDoAdapter(db, this);
        tasksRecyclerView.setAdapter(tasksAdapter);
        fab = findViewById(R.id.fab);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);
        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        fab.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });

    }


    @Override

    BottomNavigationView bottomNavigationView;


    public void handleDialogClose(DialogInterface dialog) {
        taskList = db.getAllTasks();
        // might modify when sorting dates
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();
    }
}
