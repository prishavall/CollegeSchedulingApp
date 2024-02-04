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

import com.example.emptyactivityapp.Adapter.AssignmentAdapter;
import com.example.emptyactivityapp.Adapter.ToDoAdapter;
import com.example.emptyactivityapp.Model.AssignmentModel;
import com.example.emptyactivityapp.Model.ToDoModel;
import com.example.emptyactivityapp.Utils.DataBaseHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

//<<<<<<< HEAD
import java.util.ArrayList;
import java.util.Collections;
import java.util.List; import java.util.Objects;
import com.example.emptyactivityapp.Utils.AssignmentDataBaseHandler;
//=======
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



public class AssignmentActivity extends AppCompatActivity implements DialogCloseListener{
    private RecyclerView assignmentsRecyclerView;
    private AssignmentAdapter assignmentsAdapter;
    private ExtendedFloatingActionButton fab;
    private List<AssignmentModel> taskList;
    private AssignmentDataBaseHandler db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        db = new AssignmentDataBaseHandler(this);
        db.openDatabase();



        assignmentsRecyclerView = findViewById(R.id.assignmentsRecyclerView);
        assignmentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        assignmentsAdapter = new AssignmentAdapter(this);
        assignmentsRecyclerView.setAdapter(assignmentsAdapter);
        fab = findViewById(R.id.assignmentfab);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(assignmentsAdapter));
        itemTouchHelper.attachToRecyclerView(assignmentsRecyclerView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewAssignment.newInstance().show(getSupportFragmentManager(), AddNewAssignment.TAG);
            }
        });

        loadAssignments();

    }

    private void loadAssignments() {
        List<AssignmentModel> assignmentList = db.getAllAssignments();
        Collections.reverse(assignmentList);
        assignmentsAdapter.setAssignments(assignmentList);
    }

    BottomNavigationView bottomNavigationView;
    @Override
    public void handleDialogClose(DialogInterface dialog) {
        loadAssignments();
    }
}

