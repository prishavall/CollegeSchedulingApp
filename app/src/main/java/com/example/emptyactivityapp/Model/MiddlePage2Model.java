package com.example.emptyactivityapp.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MiddlePage2Model {
    private int id;
    private String examName;
    private String examLocation;
    private String examDate;
    private String examTime;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getExamLocation() {
        return examLocation;
    }

    public void setExamLocation(String examLocation) {
        this.examLocation = examLocation;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public String getExamTime() {
        return examTime;
    }

    public void setExamTime(String examTime) {
        this.examTime = examTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    // ... (other methods)

    // Getter and Setter methods for examDate and examTime

    public String getFormattedDateTime() {
        // Combine date and time into a formatted string
        SimpleDateFormat inputFormat = new SimpleDateFormat("MM-dd-yy hh:mm a", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM d', 'yyyy hh:mm a", Locale.getDefault());

        Date dateTime;

        try {
            dateTime = inputFormat.parse(examDate + " " + examTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return ""; // Handle the exception as needed
        }

        // Format the combined date and time
        return outputFormat.format(dateTime);
    }


}
