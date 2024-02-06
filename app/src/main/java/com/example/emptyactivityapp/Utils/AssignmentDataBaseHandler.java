package com.example.emptyactivityapp.Utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.util.Log;

import com.example.emptyactivityapp.Model.AssignmentModel;
import com.example.emptyactivityapp.Model.MiddlePage2Model;


import com.example.emptyactivityapp.Model.AssignmentModel;
import com.example.emptyactivityapp.Model.ToDoModel;


import java.util.ArrayList;
import java.util.List;

public class AssignmentDataBaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 4;
    private static final String NAME = "assignmentDatabase";

    private static final String ASSIGNMENT_TABLE = "assignments";
    private static String ID = "id";
    private static final String TITLE = "title";
    private static final String COURSE = "course";
    private static final String DATE = "date";
    private static final String STATUS = "status";

    private static final String CREATE_ASSIGNMENT_TABLE = "CREATE TABLE " + ASSIGNMENT_TABLE + " ( " +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TITLE + " TEXT, " +
            COURSE + " TEXT, " +
            DATE + " TEXT, " +
            STATUS + " INTEGER)";

    private SQLiteDatabase db;

    public AssignmentDataBaseHandler(Context context) {
        super(context, NAME, null, VERSION);
        this.db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ASSIGNMENT_TABLE);
        Log.d("DataBaseHandlerAssignment", "Table created successfully"); //debugging
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ASSIGNMENT_TABLE);
        onCreate(db);
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    public void insertAssignment(AssignmentModel assignment) {
        ContentValues cv = new ContentValues();
        cv.put(TITLE, assignment.getAssignmentName());
        cv.put(COURSE, assignment.getCourse());
        cv.put(DATE, assignment.getDate());
        cv.put(STATUS, 0);

        long result = db.insert(ASSIGNMENT_TABLE, null, cv);

        if (result != -1) {
            Log.d("InsertAssignment", "Exam inserted sucessfully");
        } else {
            Log.e("InsertAssignment", "Error inserting exam into the datbase"); //debugging
        }


    }

    @SuppressLint("Range")
    public List<AssignmentModel> getAllAssignments() {
        List<AssignmentModel> assignmentList = new ArrayList<>();
        Cursor cur = null;

        try {
            cur = db.query(ASSIGNMENT_TABLE, null, null, null, null, null, null);

            if (cur != null && cur.moveToFirst()) {
                do {
                    AssignmentModel assignment = new AssignmentModel();
                    assignment.setId(cur.getInt(cur.getColumnIndex(ID)));
                    assignment.setAssignmentName(cur.getString(cur.getColumnIndex(TITLE)));
                    assignment.setCourse(cur.getString(cur.getColumnIndex(COURSE)));
                    assignment.setDate(cur.getString(cur.getColumnIndex(DATE)));
                    assignment.setStatus(cur.getInt(cur.getColumnIndex(STATUS)));
                    assignmentList.add(assignment);
                } while (cur.moveToNext());
            }
        }  finally {
            if (cur != null && !cur.isClosed()) {
                cur.close();
            }
        }

        return assignmentList;
    }


    public void updateAssignment(int id, AssignmentModel assignment) {
        ContentValues cv = new ContentValues();
        cv.put(TITLE, assignment.getAssignmentName());
        cv.put(COURSE, assignment.getCourse());
        cv.put(DATE, assignment.getDate());
        db.update(ASSIGNMENT_TABLE, cv, ID + "=?", new String[]{String.valueOf(id)});
    }

    public void deleteAssignment(int id) {
        db.delete(ASSIGNMENT_TABLE, ID + "=?", new String[]{String.valueOf(id)});
    }


}



