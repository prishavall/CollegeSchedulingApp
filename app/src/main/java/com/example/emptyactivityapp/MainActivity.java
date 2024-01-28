package com.example.emptyactivityapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity; import androidx.recyclerview.widget.LinearLayoutManager; import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.emptyactivityapp.Adapter.ToDoAdapter; import com.example.emptyactivityapp.Model.ToDoModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList; import java.util.List; import java.util.Objects;

public class MainActivity extends AppCompatActivity { private RecyclerView tasksRecyclerView; private ToDoAdapter tasksAdapter; private List<ToDoModel> taskList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.todolist);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.simpleschedule) {
                    startActivity(new Intent(getApplicationContext(), Schedule.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.todolist) {
                    return true;
                } else if (itemId == R.id.assignentsText) {
                    startActivity(new Intent(getApplicationContext(), Assignments.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.examsText) {
                    startActivity(new Intent(getApplicationContext(), MiddlePage2.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });
        taskList = new ArrayList<>();
        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new ToDoAdapter(this);
        tasksRecyclerView.setAdapter(tasksAdapter);
        ToDoModel task = new ToDoModel();
        task.setTask("This is a Test Task");
        task.setStatus(0);
        task.setId(1);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        tasksAdapter.setTasks(taskList);

    }
    BottomNavigationView bottomNavigationView;
}
