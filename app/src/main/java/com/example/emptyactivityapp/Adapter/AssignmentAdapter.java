package com.example.emptyactivityapp.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emptyactivityapp.AddNewAssignment;
import com.example.emptyactivityapp.AddNewExam;
import com.example.emptyactivityapp.Assignment;
import com.example.emptyactivityapp.MiddlePage2;
import com.example.emptyactivityapp.Model.AssignmentModel;
import com.example.emptyactivityapp.Model.MiddlePage2Model;
import com.example.emptyactivityapp.R;
import com.example.emptyactivityapp.Utils.AssignmentDataBaseHandler;
import com.example.emptyactivityapp.Utils.DataBaseHandlerExam;

import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder> {
    private List<AssignmentModel> assignmentList;
    private Assignment activity;
    private AssignmentDataBaseHandler db;

    public AssignmentAdapter(Assignment mainactivity) {
        this.activity = mainactivity;
        this.db = new AssignmentDataBaseHandler(mainactivity);
        this.db.openDatabase();
    }




    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context contexto = parent.getContext();
        LayoutInflater inflatable = LayoutInflater.from(contexto);
        View view = inflatable.inflate(R.layout.assignment_card, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder vh, int positione) {
        AssignmentModel assignment = assignmentList.get(positione);
        vh.textAssignmentName.setText(assignment.getAssignmentName());
        vh.textAssignmentCourse.setText(assignment.getCourse());
        vh.textAssignmentDate.setText(assignment.getDate());
    }


    public int getItemCount() {
        if (assignmentList == null) {
            return 0;
        }
        else {
            return assignmentList.size();
        }
    }

    public void setAssignments(List<AssignmentModel> assignments) {
        if (assignments != null) {
            this.assignmentList = assignments;
            notifyDataSetChanged();
        }
    }
    public void deleteItem(int position) {
        if (db != null) {
            AssignmentModel assignment = assignmentList.get(position);
            int assignmentID = assignment.getId();
            db.deleteAssignment(assignmentID);
            assignmentList.remove(position);
            notifyItemRemoved(position);
        }
    }




    public Context getContext() {
        return activity;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textAssignmentName;
        private TextView textAssignmentCourse;
        private TextView textAssignmentDate;

        ViewHolder(View view) {
            super(view);
            textAssignmentName = (TextView) view.findViewById(R.id.textAssignmentName);
            textAssignmentCourse = (TextView) view.findViewById(R.id.textAssignmentCourse);
            textAssignmentDate = (TextView) view.findViewById(R.id.textAssignmentDate);
        }
    }

    public void editItem(int position)  {
        AssignmentModel assignment = assignmentList.get(position);
        AssignmentModel newAssignment = new AssignmentModel();
        newAssignment.setId((Integer) assignment.getId());
        newAssignment.setStatus(assignment.getStatus());
        newAssignment.setAssignmentName(assignment.getAssignmentName());
        newAssignment.setCourse(assignment.getCourse());

        AddNewAssignment fragment = new AddNewAssignment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", assignment.getId());
        bundle.putString("title", assignment.getAssignmentName());
        bundle.putString("course", assignment.getCourse());
        bundle.putString("date", assignment.getDate());

        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), AddNewAssignment.TAG);
        //db.updateAssignment((Integer) assignment.getId(), newAssignment);
        //db.deleteAssignment((Integer) assignment.getId());
        //notifyDataSetChanged();

    }



}

