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

    public MiddlePage2Adapter(MiddlePage2 mainactivity) {
        this.activity = mainactivity;
        this.db = new DataBaseHandlerExam(mainactivity);
        this.db.openDatabase();
    }

    public void onBindViewHolder(ViewHolder vh, int positione) {
        MiddlePage2Model exam = examList.get(positione);
        vh.textExamName.setText(exam.getExamName());
        vh.textExamLocation.setText(exam.getExamLocation());
        vh.textExamDateTime.setText(exam.getFormattedDateTime());
    }


    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context contexto = parent.getContext();
        LayoutInflater inflatable = LayoutInflater.from(contexto);
        View view = inflatable.inflate(R.layout.exam_card, parent, false);
        return new ViewHolder(view);
    }


    public int getItemCount() {
        if (examList == null) {
            return 0;
        }
        else {
            return examList.size();
        }
    }

    public void setExams(List<MiddlePage2Model> exams) {
        if (exams != null) {
            this.examList = exams;
            notifyDataSetChanged();
        }
    }
    public void deleteItem(int position) {
        if (db != null) {
            MiddlePage2Model exam = examList.get(position);
            int examID = exam.getId();
            db.deleteExam(examID);
            examList.remove(position);
            notifyItemRemoved(position);
        }
    }


    public Context getContext() {
        return activity;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textExamName;
        private TextView textExamLocation;
        private TextView textExamDateTime;

        ViewHolder(View view) {
            super(view);
            textExamName = (TextView) view.findViewById(R.id.textExamName);
            textExamLocation = (TextView) view.findViewById(R.id.textExamLocation);
            textExamDateTime = (TextView) view.findViewById(R.id.textExamDateTime);
        }
    }

    public void editItem(int position) {
        MiddlePage2Model exam = examList.get(position);
        Bundle bundy = new Bundle();
        bundy.putInt("id", (Integer) exam.getId());
        bundy.putString("examName", exam.getExamName());
        bundy.putString("location", exam.getExamLocation());
        bundy.putString("date", exam.getExamDate());
        bundy.putString("time", exam.getExamTime());

        AddNewExam fragment = new AddNewExam();
        fragment.setArguments(bundy);
        fragment.show(activity.getSupportFragmentManager(), AddNewExam.TAG);
    }



}
