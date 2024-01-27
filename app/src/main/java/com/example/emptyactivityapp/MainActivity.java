package com.example.emptyactivityapp;

import androidx.appcompat.app.AppCompatActivity; import androidx.recyclerview.widget.LinearLayoutManager; import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.emptyactivityapp.Adapter.ToDoAdapter; import com.example.emptyactivityapp.Model.ToDoModel;
import java.util.ArrayList; import java.util.List; import java.util.Objects;

public class MainActivity extends AppCompatActivity { private RecyclerView tasksRecyclerView; private ToDoAdapter tasksAdapter; private List<ToDoModel> taskList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
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
}
