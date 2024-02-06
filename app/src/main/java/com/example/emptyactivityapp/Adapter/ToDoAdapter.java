package com.example.emptyactivityapp.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.emptyactivityapp.AddNewExam;
import com.example.emptyactivityapp.AddNewTask;
import com.example.emptyactivityapp.MainActivity;
import com.example.emptyactivityapp.Model.MiddlePage2Model;
import com.example.emptyactivityapp.Model.ToDoModel;
import com.example.emptyactivityapp.R;
import com.example.emptyactivityapp.Utils.DataBaseHandler;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private List<ToDoModel> todoList;
    private DataBaseHandler db;
    private MainActivity activity;

    public ToDoAdapter(DataBaseHandler db, MainActivity activity) {
        this.db = db;
        this.activity = activity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context contexto = parent.getContext();
        LayoutInflater inflatable = LayoutInflater.from(contexto);
        View view = inflatable.inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(final ViewHolder vh, int position) {
        db.openDatabase();
        final ToDoModel item = todoList.get(position);

        if (item.getCategory() != null && !item.getCategory().isEmpty()) {
            vh.taskCategorytext.setVisibility(View.VISIBLE); //testing if its gonna show up
            vh.taskCategorytext.setText(item.getCategory());
        } else {

            vh.taskCategorytext.setVisibility(View.GONE);
        }

        vh.newcheckbox.setText(item.getTask());
        vh.newcheckbox.setChecked(toBoolean(item.getStatus()));

        vh.newcheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton button, boolean Checked) {
                if (Checked) {
                    db.updateStatus(item.getId(), 1);
                } else {
                    db.updateStatus(item.getId(), 0);
                }
            }
        });
    }

    private boolean toBoolean(int j) {

        if (j != 0) {
            return true;
        }
        else {
            return false;
        }
    }


    public int getItemCount() {
        if (todoList == null) {
            return 0;
        }
        else {
            return todoList.size();
        }
    }

    public Context getContext() {
        return activity;
    }
    public void deleteItem(int position) {
        if (db != null) {
            ToDoModel todo = todoList.get(position);
            int todoID = todo.getId();
            db.deleteTask(todoID);
            todoList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void setTasks(List<ToDoModel> todoList) {
        if (todoList != null) {
            this.todoList = todoList;
            notifyDataSetChanged();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView taskCategorytext;
        CheckBox newcheckbox;
        ViewHolder(View view) {
            super(view);
            newcheckbox = view.findViewById(R.id.todoCheckBox);
            taskCategorytext = (TextView) view.findViewById(R.id.taskCategoryText);
        }
    }
    public void editItem(int position) {
        ToDoModel item = todoList.get(position);
        Bundle bundy = new Bundle();
        bundy.putInt("id", item.getId());
        bundy.putString("task", item.getTask());
        bundy.putString("category", item.getCategory());

        AddNewTask fragment = new AddNewTask();
        fragment.setArguments(bundy);
        fragment.show(activity.getSupportFragmentManager(), AddNewTask.TAG);
    }

}