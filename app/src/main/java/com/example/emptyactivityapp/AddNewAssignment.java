package com.example.emptyactivityapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emptyactivityapp.Adapter.ToDoAdapter;
import com.example.emptyactivityapp.Model.AssignmentModel;
import com.example.emptyactivityapp.Model.ToDoModel;
import com.example.emptyactivityapp.Utils.AssignmentDataBaseHandler;
import com.example.emptyactivityapp.Utils.DataBaseHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class AddNewAssignment extends BottomSheetDialogFragment {
    public static final String TAG = "ActionBottomDialog";
    private EditText newAssignmentNameText, newAssignmentCourseText, newAssignmentDateText;
    private Button newAssignmentSaveButton;
    private AssignmentDataBaseHandler db;


    public static AddNewAssignment newInstance() {
        return new AddNewAssignment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_assignment, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newAssignmentNameText = getView().findViewById(R.id.assignmentNameText);
        newAssignmentCourseText = getView().findViewById(R.id.assignmentCourseText);
        newAssignmentDateText = getView().findViewById(R.id.assignmentDateText);
        newAssignmentSaveButton = getView().findViewById(R.id.newAssignmentButton);

        boolean isUpdate = false;
        final Bundle bundle = getArguments();
        if(bundle != null) {
            isUpdate = true;
            String assignmentName = bundle.getString("title");
            String assignmentDate = bundle.getString("date");
            String assignmentCourse = bundle.getString("course");

            newAssignmentNameText.setText(assignmentName);
            newAssignmentDateText.setText(assignmentDate);
            newAssignmentCourseText.setText(assignmentCourse);
            if(assignmentName != null && assignmentName.length() > 0 && assignmentDate != null && assignmentDate.length() > 0 &&
            assignmentCourse != null && assignmentCourse.length() > 0) {
                newAssignmentSaveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
            }
        }
        db = new AssignmentDataBaseHandler(getActivity());
        db.openDatabase();
        newAssignmentNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")) {
                    newAssignmentSaveButton.setEnabled(false);
                    newAssignmentSaveButton.setTextColor(Color.GRAY);
                } else {
                    newAssignmentSaveButton.setEnabled(true);
                    newAssignmentSaveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        newAssignmentDateText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")) {
                    newAssignmentSaveButton.setEnabled(false);
                    newAssignmentSaveButton.setTextColor(Color.GRAY);
                } else {
                    newAssignmentSaveButton.setEnabled(true);
                    newAssignmentSaveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        newAssignmentCourseText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")) {
                    newAssignmentSaveButton.setEnabled(false);
                    newAssignmentSaveButton.setTextColor(Color.GRAY);
                } else {
                    newAssignmentSaveButton.setEnabled(true);
                    newAssignmentSaveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        boolean finalIsUpdate = isUpdate;
        newAssignmentSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                String assignmentName = newAssignmentNameText.getText().toString();
                String assignmentCourse = newAssignmentCourseText.getText().toString();
                String assignmentDate = newAssignmentDateText.getText().toString();
                AssignmentModel assignment = null;

                if(finalIsUpdate) {
                    db.updateTask(bundle.getInt("id"), "", "", "");
                } else {
                    assignment = new AssignmentModel();
                    assignment.setAssignmentName(assignmentName);
                    assignment.setDate(assignmentDate);
                    assignment.setCourse(assignmentCourse);
                    AssignmentModel task = new AssignmentModel();
                    task.setStatus(0);
                    db.insertTask(task);
                }
                dismiss();
            }
        });
    }



    @Override
    public void onDismiss(DialogInterface dialog) {
        Activity activity = getActivity();
        if (activity instanceof DialogCloseListener) {
            ((DialogCloseListener)activity).handleDialogClose(dialog);
        }
    }



}

