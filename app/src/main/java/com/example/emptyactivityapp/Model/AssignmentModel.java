package com.example.emptyactivityapp.Model;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class AssignmentModel {

    // title, due date, associated class
    private String title;
    private String date;
    private String course;
    private int id, status;


    // Constructors, getters, and setters
    public AssignmentModel(String title, String date, String course) {
        this.title = title;
        this.date = date;
        this.course = course;
    }

    public AssignmentModel() {
        this.title = "hi";
        this.date = "hi";
        this.course = "hi";
    }


    public String getAssignmentName() {
        return title;
    }

    public void setAssignmentName(String examName) {
        this.title = examName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }


    public String getFormattedDate() {
        SimpleDateFormat inputFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()); // Example output format: "yyyy-MM-dd"
        Date dueDate;
        try {
            dueDate = inputFormat.parse(date);
        } catch (ParseException e) {
            // Handle the exception more gracefully, e.g., log it
            e.printStackTrace();
            return ""; // or return null, throw an exception, etc., depending on your requirements
        }
        return outputFormat.format(dueDate);


    }


}
