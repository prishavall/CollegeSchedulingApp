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

import com.example.emptyactivityapp.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;


public class DataBaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 4;
    private static final String NAME = "toDoListDatabase";
    private static final String TODO_TABLE = "todo";
    private static final String ID = "id";
    private static final String TASK = "task";

    private static final String CATEGORY = "category";
    private static final String STATUS = "status";
    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + TODO_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TASK + " TEXT, " + CATEGORY + " TEXT, "
            + STATUS + " INTEGER)";

    private SQLiteDatabase db;

    public DataBaseHandler(Context context) {
        super(context, NAME, null, VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TODO_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        // Create tables again if needed based on old debugging
        onCreate(db);
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    @SuppressLint("Range")
    public List<ToDoModel> getAllTasks() {
        List<ToDoModel> taskList = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.query(TODO_TABLE, null, null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    ToDoModel task = new ToDoModel();
                    task.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                    task.setTask(cursor.getString(cursor.getColumnIndex(TASK)));
                    task.setCategory(cursor.getString(cursor.getColumnIndex(CATEGORY)));
                    task.setStatus(cursor.getInt(cursor.getColumnIndex(STATUS)));
                    taskList.add(task);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return taskList;
    }



    public void updateStatus(int id, int status) {


    public void updateTask(int id, ToDoModel tasky) {

        ContentValues cv = new ContentValues();
        cv.put(TASK, tasky.getTask());
        cv.put(CATEGORY, tasky.getCategory());
        db.update(TODO_TABLE, cv, ID + "=?", new String[]{String.valueOf(id)});
    }

    public void insertTask(ToDoModel task){
        Log.d("InsertTask", "Inserting task: " + task.getTask() + ", Category: " + task.getCategory());
        ContentValues cv = new ContentValues();
        cv.put(TASK, task.getTask());
        cv.put(CATEGORY, task.getCategory());
        cv.put(STATUS, 0);
        db.insert(TODO_TABLE, null, cv);
    }


    private void updateMain(int id, ContentValues cv) {
        db.update(TODO_TABLE, cv, ID + "= ?", new String[]{String.valueOf(id)});
    }
    public void updateStatus(int id, int status){
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        updateMain(id, cv);
    }


    public void deleteTask(int id){
        db.delete(TODO_TABLE, ID + "= ?", new String[]{String.valueOf(id)});
    }

}