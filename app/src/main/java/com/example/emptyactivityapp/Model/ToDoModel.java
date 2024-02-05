package com.example.emptyactivityapp.Model;

public class ToDoModel {
    private int id, status;
    private String task;
    private String category;

    public void setCategory(String string) {
        this.category = string;

    }
    public String getCategory() {
        return category;
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
    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }


}
