package com.example.emptyactivityapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.emptyactivityapp.MainActivity;
import com.example.emptyactivityapp.MiddlePage2;
import com.example.emptyactivityapp.Model.MiddlePage2Model;
import com.example.emptyactivityapp.R;

import java.util.List;

public class MiddlePage2Adapter extends RecyclerView.Adapter<MiddlePage2Adapter.ViewHolder> {
    private List<MiddlePage2Model> itemList; // Fix the data type here
    private MiddlePage2 activity; // Fix the activity type here

    public MiddlePage2Adapter(MiddlePage2 activity) {
        this.activity = activity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exam_layout, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        MiddlePage2Model item = itemList.get(position);
        holder.examName.setText(item.getExamName());
        holder.date.setText(item.getDate());
        holder.time.setText(item.getTime());
        holder.course.setText(item.getCourse());
        // Add other properties specific to exams
    }

    public int getItemCount() {
        return itemList.size();
    }

    public void setItems(List<MiddlePage2Model> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView examName;
        TextView date;
        TextView time;
        TextView course;
        // Other exam-specific UI components

        ViewHolder(View view) {
            super(view);
            examName = view.findViewById(R.id.examNameTextView);
            date = view.findViewById(R.id.examDateTextView);
            time = view.findViewById(R.id.examTimeTextView);
            course = view.findViewById(R.id.examCourseTextView);
            // Initialize other exam-specific UI components
        }
    }
}
