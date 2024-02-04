package com.example.emptyactivityapp.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import com.example.emptyactivityapp.AddNewTask;
import com.example.emptyactivityapp.Assignment;
import com.example.emptyactivityapp.AssignmentActivity;
import com.example.emptyactivityapp.MainActivity;
import com.example.emptyactivityapp.Model.AssignmentModel;
import com.example.emptyactivityapp.Model.ToDoModel;
import com.example.emptyactivityapp.R;
import com.example.emptyactivityapp.Utils.AssignmentDataBaseHandler;
import com.example.emptyactivityapp.Utils.DataBaseHandler;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder> {
    private List<AssignmentModel> assignmentList;
    private AssignmentActivity activity;
    private AssignmentDataBaseHandler db;

    public AssignmentAdapter(AssignmentActivity activity) {

        this.activity = activity;
        this.db = new AssignmentDataBaseHandler(activity);
        this.db.openDatabase();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent. getContext())
                .inflate(R.layout.assignment_card, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AssignmentModel item  = assignmentList.get(position);
        holder.textAssignmentName.setText(item.getAssignmentName());
        holder.textAssignmentCourse.setText(item.getCourse());
        holder.textAssignmentDate.setText(item.getDate());


    }

    @Override
    public int getItemCount() {
        return assignmentList.size();
    }
    private boolean toBoolean (int n) {
        return n!= 0;
    }



    public void setAssignments(List<AssignmentModel> todoList) {
        this.assignmentList = todoList;
        notifyDataSetChanged();
    }

    public Context getContext() {
        return activity;
    }

    public void deleteItem(int position) {
        AssignmentModel item = assignmentList.get(position);
        db.deleteTAsk(item.getId());
        assignmentList.remove(position);
        notifyItemRemoved(position);
    }


    public void editItem(int position) {
        AssignmentModel item = assignmentList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getAssignmentName());
        AddNewTask fragment = new AddNewTask();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), AddNewTask.TAG);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textAssignmentName;
        private TextView textAssignmentCourse;
        private TextView textAssignmentDate;
        ViewHolder(View view) {
            super(view);
            textAssignmentName = view.findViewById(R.id.textAssignmentName);
            textAssignmentCourse = view.findViewById(R.id.textAssignmentCourse);
            textAssignmentDate = view.findViewById(R.id.assignmentDateText);

        }
    }
}

