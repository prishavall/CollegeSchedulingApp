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
import java.security.cert.Extension;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List; import java.util.Objects;
import com.example.emptyactivityapp.Utils.AssignmentDataBaseHandler;
//=======
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



public class Assignment extends AppCompatActivity implements DialogCloseListener{
    private RecyclerView assignmentsRecyclerView;
    private AssignmentAdapter assignmentsAdapter;

    private List<AssignmentModel> assignmentsList;
    private ExtendedFloatingActionButton fab;

    private ExtendedFloatingActionButton byCourseFab;

    private ExtendedFloatingActionButton byDateFab;

    private AssignmentDataBaseHandler db;

    BottomNavigationView bottomNavigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments);
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.assignments);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.simpleschedule) {
                    startActivity(new Intent(getApplicationContext(), Schedule.class));
                    overridePendingTransition(0, 0);
                    return true;

                } else if (itemId == R.id.todolist) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0, 0);
                    return true;

                } else if (itemId == R.id.assignments) {
                    startActivity(new Intent(getApplicationContext(), Assignment.class));
                    overridePendingTransition(0, 0);
                    return true;
       } else if (itemId == R.id.todolist) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0, 0);
                    return true;

                } else if (itemId == R.id.exams) {
                    startActivity(new Intent(getApplicationContext(), MiddlePage2.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });


        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        db = new AssignmentDataBaseHandler(this);
        db.openDatabase();

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        assignmentsList = new ArrayList<>();
        assignmentsRecyclerView = findViewById(R.id.assignmentsRecylerView);
        assignmentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        assignmentsAdapter = new AssignmentAdapter(this);
        assignmentsRecyclerView.setAdapter(assignmentsAdapter);

        fab = findViewById(R.id.assignmentfab);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelperAssignments(assignmentsAdapter));
        itemTouchHelper.attachToRecyclerView(assignmentsRecyclerView);
        loadAssignments();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewAssignment.newInstance().show(getSupportFragmentManager(), AddNewAssignment.TAG);
            }
        });
        byCourseFab = findViewById(R.id.bycoursefab);
        byCourseFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(assignmentsList, new Comparator<AssignmentModel>() {
                    @Override
                    public int compare(AssignmentModel o1, AssignmentModel o2) {
                        if (o1 != null && o1.getCourse() != null && o2 != null && o2.getCourse() !=null) {
                            return o1.getCourse().compareToIgnoreCase(o2.getCourse());
                        }
                        return 0;
                    }
                });
                assignmentsAdapter.notifyDataSetChanged();
            }
        });

        byDateFab = findViewById(R.id.bydatefab);
        byDateFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Collections.sort(assignmentsList, new Comparator<AssignmentModel>() {
                    @Override
                    public int compare(AssignmentModel o1, AssignmentModel o2) {
                        if (o1 != null && o1.getFormattedDate() != null && o2 != null && o2.getFormattedDate() !=null) {
                            return o1.getFormattedDate().compareTo(o2.getFormattedDate());
                        }
                        return 0;
                    }
                });
                assignmentsAdapter.notifyDataSetChanged();
            }

        });

        loadAssignments();

    }

    private void loadAssignments() {
        assignmentsList = db.getAllAssignments();
        //Collections.reverse(assignmentsList);
        assignmentsAdapter.setAssignments(assignmentsList);

    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        loadAssignments();
    }
}



