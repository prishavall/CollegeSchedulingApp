package com.example.emptyactivityapp.Model;

public class MiddlePage2Model {
    private String examName;
    private String date;
    private String time;
    private String course;

    // Constructors, getters, and setters
    public MiddlePage2Model(String examName, String date, String time, String course) {
        this.examName = examName;
        this.date = date;
        this.time = time;
        this.course = course;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

}

