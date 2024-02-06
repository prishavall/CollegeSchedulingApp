package com.example.emptyactivityapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.example.emptyactivityapp.Model.MiddlePage2Model;
import com.example.emptyactivityapp.Model.ScheduleModel;
import com.example.emptyactivityapp.Utils.DataBaseHandlerExam;
import com.example.emptyactivityapp.Utils.DataBaseHandlerSchedule;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewSchedule extends BottomSheetDialogFragment {
    public static final String TAG = "ActionBottomDialog";
    private EditText newScheduleNameText, newScheduleLocationText, newScheduleDateText, newScheduleTimeText;
    private Button newScheduleSaveButton;
    private DataBaseHandlerSchedule db;

    public static AddNewSchedule newInstance() {
        return new AddNewSchedule();
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_schedule, container, false);
        Window newDialogWindow = getDialog().getWindow();
        newDialogWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }


    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newScheduleNameText = getView().findViewById(R.id.scheduleNameText);
        newScheduleLocationText = getView().findViewById(R.id.scheduleLocationText);
        newScheduleDateText = getView().findViewById(R.id.scheduleDateText);
        newScheduleTimeText = getView().findViewById(R.id.scheduleTimeText);
        newScheduleSaveButton = getView().findViewById(R.id.newScheduleButton);

        boolean isUpdate = false;
        final Bundle bundles = getArguments();
        if (bundles != null) {
            isUpdate = true;
            String scheduleName = bundles.getString("scheduleName");
            String scheduleLocation = bundles.getString("scheduleLocation");
            String scheduleDate = bundles.getString("scheduleDate");
            String scheduleTime = bundles.getString("scheduleTime");

            newScheduleNameText.setText(scheduleName);
            newScheduleLocationText.setText(scheduleLocation);
            newScheduleDateText.setText(scheduleDate);
            newScheduleTimeText.setText(scheduleTime);

            if (scheduleName != null && scheduleName.length() > 0 && scheduleLocation != null && scheduleLocation.length() > 0 &&
                    scheduleDate != null && scheduleDate.length() > 0 && scheduleTime != null && scheduleTime.length() > 0) {
                int color = ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark);
                newScheduleSaveButton.setTextColor(color);
            }
        }

        db = new DataBaseHandlerSchedule(getActivity());
        db.openDatabase();

        newScheduleNameText.addTextChangedListener(createTextWatcher());
        newScheduleLocationText.addTextChangedListener(createTextWatcher());
        newScheduleDateText.addTextChangedListener(createTextWatcher());
        newScheduleTimeText.addTextChangedListener(createTextWatcher());

        boolean IsUpdatept2 = isUpdate;

        newScheduleSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String scheduleName = newScheduleNameText.getText().toString();
                String scheduleLocation = newScheduleLocationText.getText().toString();
                String scheduleDate = newScheduleDateText.getText().toString();
                String scheduleTime = newScheduleTimeText.getText().toString();

                ScheduleModel schedule = null;
                if (IsUpdatept2) {
                    db.updateSchedule(bundles.getInt("id"), schedule);
                } else {
                    schedule = new ScheduleModel();
                    schedule.setScheduleName(scheduleName);
                    schedule.setScheduleLocation(scheduleLocation);
                    schedule.setScheduleDate(scheduleDate);
                    schedule.setScheduleTime(scheduleTime);
                    db.insertSchedule(schedule);
                }
                dismiss();
            }
        });
    }



    private TextWatcher createTextWatcher() { //done
        return new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newScheduleSaveButton.setEnabled(!s.toString().isEmpty());
                newScheduleSaveButton.setTextColor(s.toString().isEmpty() ? Color.GRAY : ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
            }

            public void afterTextChanged(Editable s) {
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        };
    }


    public void onDismiss(DialogInterface dialog) {
        Activity activity = getActivity();
        DialogCloseListener clistener = null;
        if (activity instanceof DialogCloseListener) {
            clistener = (DialogCloseListener) activity;
        }
        if (clistener != null) {
            clistener.handleDialogClose(dialog);
        }
    }
}