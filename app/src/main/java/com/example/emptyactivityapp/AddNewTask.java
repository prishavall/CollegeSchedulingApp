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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.example.emptyactivityapp.Model.MiddlePage2Model;
import com.example.emptyactivityapp.Model.ToDoModel;
import com.example.emptyactivityapp.Utils.DataBaseHandler;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;




public class AddNewTask extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";
    private EditText newTaskText, newCategoryText;
    private Button newTaskSaveButton;

    private DataBaseHandler db;

    public static AddNewTask newInstance(){
        return new AddNewTask();
    }

    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.new_task, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return view;
    }


    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newTaskText = requireView().findViewById(R.id.newTaskText);
        newCategoryText = getView().findViewById(R.id.taskCategoryText);
        newTaskSaveButton = getView().findViewById(R.id.newTaskButton);

        boolean isUpdate = false;

        final Bundle bundle = getArguments();
        if(bundle != null){
            isUpdate = true;
            String task = bundle.getString("task");
            newTaskText.setText(task);
            assert task != null;
            if(task.length()>0)
                newTaskSaveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark));
        }

        db = new DataBaseHandler(getActivity());
        db.openDatabase();

        newTaskText.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }


            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newTaskSaveButton.setEnabled(!s.toString().isEmpty());
                newTaskSaveButton.setTextColor(s.toString().isEmpty() ? Color.GRAY : ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
            }



            public void afterTextChanged(Editable s) {
            }
        });

        final boolean finalIsUpdate = isUpdate;
        newTaskSaveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String text = newTaskText.getText().toString();
                String category = newCategoryText.getText().toString();

                if(finalIsUpdate){
                    ToDoModel tmodel = new ToDoModel();
                    tmodel.setTask(text);
                    tmodel.setCategory(category);
                    db.updateTask(bundle.getInt("id"), tmodel);
                }
                else {
                    ToDoModel task = new ToDoModel();
                    task.setTask(text);
                    task.setCategory(category);
                    task.setStatus(0);
                    db.insertTask(task);
                }
                dismiss();
            }
        });
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
