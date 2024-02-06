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
import com.example.emptyactivityapp.Utils.DataBaseHandlerExam;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewExam extends BottomSheetDialogFragment {
    public static final String TAG = "ActionBottomDialog";
    private EditText newExamNameText, newExamLocationText, newExamDateText, newExamTimeText;
    private Button newExamSaveButton;
    private DataBaseHandlerExam db;

    public static AddNewExam newInstance() {
        return new AddNewExam();
    }


    public void onCreate(Bundle savedInstance) { //done
        super.onCreate(savedInstance);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { //maybe donw
        View view = inflater.inflate(R.layout.new_exam, container, false);
        Window newDialogWindow = getDialog().getWindow();
        newDialogWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }


    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newExamNameText = getView().findViewById(R.id.examNameText);
        newExamLocationText = getView().findViewById(R.id.examLocationText);
        newExamDateText = getView().findViewById(R.id.examDateText);
        newExamTimeText = getView().findViewById(R.id.examTimeText);
        newExamSaveButton = getView().findViewById(R.id.newExamButton);

        boolean isUpdate = false;
        final Bundle bundtcake = getArguments();
        if (bundtcake != null) {
            isUpdate = true;
            String examName = bundtcake.getString("examName");
            String examDate = bundtcake.getString("examDate");
            String examLocation = bundtcake.getString("examLocation");
            String examTime = bundtcake.getString("examTime");

            newExamNameText.setText(examName);
            newExamDateText.setText(examDate);
            newExamLocationText.setText(examLocation);
            newExamTimeText.setText(examTime);

            if (examName != null && examName.length() > 0 && examLocation != null && examLocation.length() > 0 &&
                    examDate != null && examDate.length() > 0 && examTime != null && examTime.length() > 0) {
                int color = ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark);
                newExamSaveButton.setTextColor(color);
            }
        }

        db = new DataBaseHandlerExam(getActivity());
        db.openDatabase();

        newExamNameText.addTextChangedListener(createTextWatcher());
        newExamLocationText.addTextChangedListener(createTextWatcher());
        newExamDateText.addTextChangedListener(createTextWatcher());
        newExamTimeText.addTextChangedListener(createTextWatcher());

        boolean IsUpdatePart2 = isUpdate; //need to set again

        newExamSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String examName = newExamNameText.getText().toString();
                String examLocation = newExamLocationText.getText().toString();
                String examDate = newExamDateText.getText().toString();
                String examTime = newExamTimeText.getText().toString();

                MiddlePage2Model exam = null;
                if (IsUpdatePart2) {
                    db.updateExam(bundtcake.getInt("id"), exam);
                } else {
                    exam = new MiddlePage2Model();
                    exam.setExamName(examName);
                    exam.setExamDate(examDate);
                    exam.setExamLocation(examLocation);
                    exam.setExamTime(examTime);
                    db.insertExam(exam);
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
                newExamSaveButton.setEnabled(!s.toString().isEmpty());
                newExamSaveButton.setTextColor(s.toString().isEmpty() ? Color.GRAY : ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
            }

            public void afterTextChanged(Editable s) {
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        };
    }


}
