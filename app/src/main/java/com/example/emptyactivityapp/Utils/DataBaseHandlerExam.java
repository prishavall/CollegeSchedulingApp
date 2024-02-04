package com.example.emptyactivityapp.Utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.emptyactivityapp.Model.MiddlePage2Model;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHandlerExam extends SQLiteOpenHelper {
    private static final int VERSION = 2;
    private static final String NAME = "examDatabase";

    private static final String EXAM_TABLE = "exams";
    private static String ID = "id";
    private static final String EXAM_NAME = "exam_name";
    private static final String EXAM_LOCATION = "exam_location";
    private static final String EXAM_DATE = "exam_date";
    private static final String EXAM_TIME = "exam_time";
    private static final String STATUS = "status";

    private static final String CREATE_EXAM_TABLE = "CREATE TABLE " + EXAM_TABLE + " ( " +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            EXAM_NAME + " TEXT, " +
            EXAM_LOCATION + " TEXT, " +
            EXAM_DATE + " TEXT, " +
            EXAM_TIME + " TEXT, " +
            STATUS + " INTEGER)";

    private SQLiteDatabase db;

    public DataBaseHandlerExam(Context context) {
        super(context, NAME, null, VERSION);
        this.db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_EXAM_TABLE);
        Log.d("DataBaseHandlerExam", "Table created successfully"); //debugging
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EXAM_TABLE);
        onCreate(db);
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    public void insertExam(MiddlePage2Model exam) {
        ContentValues cv = new ContentValues();
        cv.put(EXAM_NAME, exam.getExamName());
        cv.put(EXAM_LOCATION, exam.getExamLocation());
        cv.put(EXAM_DATE, exam.getExamDate());
        cv.put(EXAM_TIME, exam.getExamTime());
        cv.put(STATUS, 0);

        long result = db.insert(EXAM_TABLE, null, cv);

        if (result != -1) {
            Log.d("InsertExam", "Exam inserted sucessfully");
        } else {
            Log.e("InsertExam", "Error inserting exam into the datbase"); //debugging
        }
    }

    @SuppressLint("Range")
    public List<MiddlePage2Model> getAllExams() {
        List<MiddlePage2Model> examList = new ArrayList<>();
        Cursor cur = null;

        try {
            cur = db.query(EXAM_TABLE, null, null, null, null, null, null);

            if (cur != null && cur.moveToFirst()) {
                do {
                    MiddlePage2Model exam = new MiddlePage2Model();
                    exam.setId(cur.getInt(cur.getColumnIndex(ID)));
                    exam.setExamName(cur.getString(cur.getColumnIndex(EXAM_NAME)));
                    exam.setExamLocation(cur.getString(cur.getColumnIndex(EXAM_LOCATION)));
                    exam.setExamDate(cur.getString(cur.getColumnIndex(EXAM_DATE)));
                    exam.setExamTime(cur.getString(cur.getColumnIndex(EXAM_TIME)));
                    exam.setStatus(cur.getInt(cur.getColumnIndex(STATUS)));
                    examList.add(exam);
                } while (cur.moveToNext());
            }
        } catch (Exception e) {
            Log.e("GetAllExams", "Error getting exams from the database", e);
        } finally {
            if (cur != null && !cur.isClosed()) {
                cur.close();
            }
        }

        return examList;
    }

    public void updateStatus(int id, int status) {
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        db.update(EXAM_TABLE, cv, ID + "=?", new String[]{String.valueOf(id)});
    }


    public void updateExam(int id, MiddlePage2Model exam) {
        ContentValues cv = new ContentValues();
        cv.put(EXAM_NAME, exam.getExamName());
        cv.put(EXAM_LOCATION, exam.getExamLocation());
        cv.put(EXAM_DATE, exam.getExamDate());
        cv.put(EXAM_TIME, exam.getExamTime());
        db.update(EXAM_TABLE, cv, ID + "=?", new String[]{String.valueOf(id)});
    }

    public void deleteExam(int id) {
        db.delete(EXAM_TABLE, ID + "=?", new String[]{String.valueOf(id)});
    }


}
