package com.example.emptyactivityapp.Utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.emptyactivityapp.Model.ScheduleModel;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHandlerSchedule extends SQLiteOpenHelper {
    private static final int VERSION = 2;
    private static final String NAME = "scheduleDatabase";

    private static final String SCHEDULE_TABLE = "schedules";
    private static String ID = "id";
    private static final String SCHEDULE_NAME = "schedule_name";
    private static final String SCHEDULE_LOCATION = "schedule_location";
    private static final String SCHEDULE_DATE = "schedule_date";
    private static final String SCHEDULE_TIME = "schedule_time";
    private static final String STATUS = "status";

    private static final String CREATE_SCHEDULE_TABLE = "CREATE TABLE " + SCHEDULE_TABLE + " ( " +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            SCHEDULE_NAME + " TEXT, " +
            SCHEDULE_LOCATION + " TEXT, " +
            SCHEDULE_DATE + " TEXT, " +
            SCHEDULE_TIME + " TEXT, " +
            STATUS + " INTEGER)";

    private SQLiteDatabase db;

    public DataBaseHandlerSchedule(Context context) {
        super(context, NAME, null, VERSION);
        this.db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SCHEDULE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SCHEDULE_TABLE);
        onCreate(db);
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    public void insertSchedule(ScheduleModel schedule) {
        ContentValues cv = new ContentValues();
        cv.put(SCHEDULE_NAME, schedule.getScheduleName());
        cv.put(SCHEDULE_LOCATION, schedule.getScheduleLocation());
        cv.put(SCHEDULE_DATE, schedule.getScheduleDate());
        cv.put(SCHEDULE_TIME, schedule.getScheduleTime());
        cv.put(STATUS, 0);

        long result = db.insert(SCHEDULE_TABLE, null, cv);

        if (result != -1) {
            Log.d("InsertSchedule", "Schedule was inserted successfully");
        } else {
            Log.e("InsertSchedule", "Theres an error inserting schedule into the database");
        }
    }

    @SuppressLint("Range")
    public List<ScheduleModel> getAllSchedules() {
        List<ScheduleModel> scheduleList = new ArrayList<>();
        Cursor cur = null;

        try {
            cur = db.query(SCHEDULE_TABLE, null, null, null, null, null, null);

            if (cur != null && cur.moveToFirst()) {
                do {
                    ScheduleModel schedule = new ScheduleModel();
                    schedule.setId(cur.getInt(cur.getColumnIndex(ID)));
                    schedule.setScheduleName(cur.getString(cur.getColumnIndex(SCHEDULE_NAME)));
                    schedule.setScheduleLocation(cur.getString(cur.getColumnIndex(SCHEDULE_LOCATION)));
                    schedule.setScheduleDate(cur.getString(cur.getColumnIndex(SCHEDULE_DATE)));
                    schedule.setScheduleTime(cur.getString(cur.getColumnIndex(SCHEDULE_TIME)));
                    schedule.setStatus(cur.getInt(cur.getColumnIndex(STATUS)));
                    scheduleList.add(schedule);
                } while (cur.moveToNext());
            }
        } catch (Exception e) {
            Log.e("GetAllSchedules", "Theres an error getting schedules from the database", e);
        } finally {
            if (cur != null && !cur.isClosed()) {
                cur.close();
            }
        }

        return scheduleList;
    }



    public void updateSchedule(int id, ScheduleModel schedule) {
        ContentValues cv = new ContentValues();
        cv.put(SCHEDULE_NAME, schedule.getScheduleName());
        cv.put(SCHEDULE_LOCATION, schedule.getScheduleLocation());
        cv.put(SCHEDULE_DATE, schedule.getScheduleDate());
        cv.put(SCHEDULE_TIME, schedule.getScheduleTime());
        db.update(SCHEDULE_TABLE, cv, ID + "=?", new String[]{String.valueOf(id)});
    }

    public void deleteSchedule(int id) {
        db.delete(SCHEDULE_TABLE, ID + "=?", new String[]{String.valueOf(id)});
    }


}
