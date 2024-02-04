package com.example.emptyactivityapp.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ScheduleModel {
    private int id;
    private String scheduleName;
    private String scheduleLocation;
    private String scheduleDate;
    private String scheduleTime;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    public String getScheduleLocation() {
        return scheduleLocation;
    }

    public void setScheduleLocation(String scheduleLocation) {
        this.scheduleLocation = scheduleLocation;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(String scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    // ... (other methods)

    // Getter and Setter methods for scheduleDate and scheduleTime

    public String getFormattedDateTime() {
        // Combine date and time into a formatted string
        SimpleDateFormat inputFormat = new SimpleDateFormat("MM-dd-yy hh:mm a", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM d', 'yyyy hh:mm a", Locale.getDefault());

        Date dateTime;

        try {
            dateTime = inputFormat.parse(scheduleDate + " " + scheduleTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return ""; // Handle the exception as needed
        }

        // Format the combined date and time
        return outputFormat.format(dateTime);
    }


}
