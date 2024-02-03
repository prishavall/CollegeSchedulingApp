package com.example.emptyactivityapp.Model;

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

}
