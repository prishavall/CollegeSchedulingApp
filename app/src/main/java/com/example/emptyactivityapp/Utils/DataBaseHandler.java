package com.example.emptyactivityapp.Utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.emptyactivityapp.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;


public class DataBaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 3;
    private static final String NAME = "toDoListDatabase";
    private static final String TODO_TABLE = "todo";
    private static final String ID = "id";
    private static final String TASK = "task";
    private static final String STATUS = "status";
    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + TODO_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TASK + " TEXT, "
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

    public List<ToDoModel> getAllTasks(){
        List<ToDoModel> taskList = new ArrayList<>();
        Cursor cur = null;
        try{
            cur = db.query(TODO_TABLE, null, null, null, null, null, null, null);
            if(cur.moveToFirst() && cur != null){
                do{
                    ToDoModel task = new ToDoModel();
                    task.setId(cur.getInt(cur.getColumnIndex(ID)));
                    task.setTask(cur.getString(cur.getColumnIndex(TASK)));
                    task.setStatus(cur.getInt(cur.getColumnIndex(STATUS)));
                    taskList.add(task);
                }
                while(cur.moveToNext());
            }
        }
        finally {
            if (cur != null) {
                cur.close();
            }
        }
        return taskList;
    }
    public void insertTask(ToDoModel task){
        ContentValues cv = new ContentValues();
        cv.put(TASK, task.getTask());
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
    public void updateTask(int id, String task) {
        ContentValues cv = new ContentValues();
        cv.put(TASK, task);
        updateMain(id, cv);
    }
}