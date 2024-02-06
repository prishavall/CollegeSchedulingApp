package com.example.emptyactivityapp;

import android.app.Activity;
import android.content.DialogInterface;

import android.content.Intent;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;


import androidx.core.content.ContextCompat;

import com.example.emptyactivityapp.Model.AssignmentModel;
import com.example.emptyactivityapp.Model.MiddlePage2Model;
import com.example.emptyactivityapp.Utils.AssignmentDataBaseHandler;
import com.example.emptyactivityapp.Utils.DataBaseHandlerExam;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

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



    public void onCreate(Bundle savedInstance) { //done
        super.onCreate(savedInstance);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { //maybe donw
        View view = inflater.inflate(R.layout.new_assignment, container, false);
        Window newDialogWindow = getDialog().getWindow();
        newDialogWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
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

        final Bundle bundtcake = getArguments();
        if (bundtcake != null) {
            isUpdate = true;
            String assignmentName = bundtcake.getString("title");
            String assignmentCourse = bundtcake.getString("course");
            String assignmentDate = bundtcake.getString("date");

            newAssignmentNameText.setText(assignmentName);
            newAssignmentCourseText.setText(assignmentCourse);
            newAssignmentDateText.setText(assignmentDate);

            if (assignmentName != null && assignmentName.length() > 0 &&
                    assignmentDate != null && assignmentDate.length() > 0 && assignmentCourse != null && assignmentCourse.length() > 0) {
                int color = ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark);
                newAssignmentSaveButton.setTextColor(color);
            }
        }

        db = new AssignmentDataBaseHandler(getActivity());
        db.openDatabase();

        newAssignmentNameText.addTextChangedListener(createTextWatcher());
        newAssignmentCourseText.addTextChangedListener(createTextWatcher());
        newAssignmentDateText.addTextChangedListener(createTextWatcher());

        boolean IsUpdatePart2 = isUpdate; //need to set again

        newAssignmentSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String assignmentName = newAssignmentNameText.getText().toString();
                String assignmentCourse = newAssignmentCourseText.getText().toString();
                String assignmentDate = newAssignmentDateText.getText().toString();

                AssignmentModel assignment = null;
                if (IsUpdatePart2) {
                    assignment = new AssignmentModel();
                    assignment.setAssignmentName(assignmentName);
                    assignment.setCourse(assignmentCourse);
                    assignment.setDate(assignmentDate);
                    db.updateAssignment(bundtcake.getInt("id"), assignment);
                } else {
                    assignment = new AssignmentModel();
                    assignment.setAssignmentName(assignmentName);
                    assignment.setCourse(assignmentCourse);
                    assignment.setDate(assignmentDate);
                    db.insertAssignment(assignment);
                }
                dismiss();
            }
        });
    }


    public void onDismiss(DialogInterface dialog) { //done
        Activity activity = getActivity();
        DialogCloseListener clistener = null;
        if (activity instanceof DialogCloseListener) {
            clistener = (DialogCloseListener) activity;
        }
        if (clistener != null) {
            clistener.handleDialogClose(dialog);
        }
    }
    private TextWatcher createTextWatcher() { //done
        return new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newAssignmentSaveButton.setEnabled(!s.toString().isEmpty());
                newAssignmentSaveButton.setTextColor(s.toString().isEmpty() ? Color.GRAY : ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
            }

            public void afterTextChanged(Editable s) {
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        };
    }


}
