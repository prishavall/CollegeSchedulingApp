package com.example.emptyactivityapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.emptyactivityapp.Adapter.MiddlePage2Adapter;
import com.example.emptyactivityapp.AddNewExam;
import com.example.emptyactivityapp.DialogCloseListener;
import com.example.emptyactivityapp.Model.MiddlePage2Model;
import com.example.emptyactivityapp.R;
import com.example.emptyactivityapp.RecyclerItemTouchHelperExams;
import com.example.emptyactivityapp.Utils.DataBaseHandlerExam;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.Collections;
import java.util.List;

public class MiddlePage2 extends AppCompatActivity implements DialogCloseListener {

    private RecyclerView examsRecyclerView;
    private MiddlePage2Adapter examsAdapter;
    private ExtendedFloatingActionButton fab;
    private DataBaseHandlerExam db;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle_page2);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        db = new DataBaseHandlerExam(this);
        db.openDatabase();

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.exams);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.simpleschedule) {
                    startActivity(new Intent(getApplicationContext(), Schedule.class));
                    finish();
                    return true;
                } else if (itemId == R.id.todolist) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                    return true;
                } else if (itemId == R.id.assignments) {
                    startActivity(new Intent(getApplicationContext(), Assignments.class));
                    finish();
                    return true;
                } else if (itemId == R.id.exams) {
                    return true; // Already in the exams page
                }
                return false;
            }
        });

        examsRecyclerView = findViewById(R.id.examsRecyclerView);
        examsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        examsAdapter = new MiddlePage2Adapter(this);
        examsRecyclerView.setAdapter(examsAdapter);

        fab = findViewById(R.id.newExamButton);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelperExams(examsAdapter));
        itemTouchHelper.attachToRecyclerView(examsRecyclerView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewExam.newInstance().show(getSupportFragmentManager(), AddNewExam.TAG);
            }
        });

        loadExams();
    }

    private void loadExams() {
        List<MiddlePage2Model> examList = db.getAllExams();
        Collections.reverse(examList);
        examsAdapter.setExams(examList);
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        loadExams();
    }
}
