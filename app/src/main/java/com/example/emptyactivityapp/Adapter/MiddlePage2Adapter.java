package com.example.emptyactivityapp.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.emptyactivityapp.AddNewExam;
import com.example.emptyactivityapp.MiddlePage2;
import com.example.emptyactivityapp.Model.MiddlePage2Model;
import com.example.emptyactivityapp.R;
import com.example.emptyactivityapp.Utils.DataBaseHandlerExam;

import java.util.List;

public class MiddlePage2Adapter extends RecyclerView.Adapter<MiddlePage2Adapter.ViewHolder> {
    private List<MiddlePage2Model> examList;
    private MiddlePage2 activity;
    private DataBaseHandlerExam db;

    public MiddlePage2Adapter(MiddlePage2 activity) {
        this.activity = activity;
        this.db = new DataBaseHandlerExam(activity);
        this.db.openDatabase();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MiddlePage2Model exam = examList.get(position);

        holder.textExamName.setText(exam.getExamName());
        holder.textExamLocation.setText(exam.getExamLocation());

        holder.textExamDateTime.setText(exam.getFormattedDateTime());
    }


    public void setExams(List<MiddlePage2Model> exams) {
        this.examList = exams;
        notifyDataSetChanged();
    }

    public int getItemCount() {
        if (examList == null) {
            return 0;
        }
        return examList.size();
    }

    public Context getContext() {
        return activity;
    }

    public void deleteItem(int position) {
        if (db != null) {
        MiddlePage2Model exam = examList.get(position);
        db.deleteExam(exam.getId());
        examList.remove(position);
        notifyItemRemoved(position);
    }
    }

    public void editItem(int position) {
        MiddlePage2Model exam = examList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", (Integer) exam.getId());
        bundle.putString("examName", exam.getExamName());
        bundle.putString("location", exam.getExamLocation());
        bundle.putString("date", exam.getExamDate());
        bundle.putString("time", exam.getExamTime());

        AddNewExam fragment = new AddNewExam();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), AddNewExam.TAG);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textExamName;
        private TextView textExamLocation;
        private TextView textExamDateTime;

        ViewHolder(View view) {
            super(view);
            textExamName = view.findViewById(R.id.textExamName);
            textExamLocation = view.findViewById(R.id.textExamLocation);
            textExamDateTime = view.findViewById(R.id.textExamDateTime);
        }
    }
}
