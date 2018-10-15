package com.example.ahmed.msp6;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBConnection extends SQLiteOpenHelper {

    public DBConnection(Context context)
    {
        super(context, "members.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS admins(id INTEGER primary key, name TEXT, age INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS students(id INTEGER primary key, sname TEXT, sage INTEGER)");

        db.execSQL("CREATE VIEW IF NOT EXISTS myview AS SELECT admins.id, admins.name, admins.age, students.sname, students.sage FROM admins LEFT JOIN students ON admins.id = students.id");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS triger AFTER insert ON admins BEGIN insert INTO students (id, sname, sage) VALUES (new.id, 'null', 0); END");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS admins");
            db.execSQL("DROP TABLE IF EXISTS students");
            onCreate(db);
    }

    public ArrayList<String> getALL() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> data = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM myview", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            data.add(cursor.getString(cursor.getColumnIndex("id")) + " : "
                    + cursor.getString(cursor.getColumnIndex("name")) + " : "
                    + cursor.getString(cursor.getColumnIndex("age")) + " : "
                    + cursor.getString(cursor.getColumnIndex("sname")) + " : "
                    + cursor.getString(cursor.getColumnIndex("sage")));
            cursor.moveToNext();
        }
        return data;
    }

    public void insertStudent(String name, int age){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put("sname", name);
        data.put("sage", age);
        db.insert("students", null, data);
    }

    public ArrayList<String> getStudents() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM students", null);
        cursor.moveToFirst();
        ArrayList<String> data = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            data.add(cursor.getString(cursor.getColumnIndex("id")) + " : "
                    + cursor.getString(cursor.getColumnIndex("sname")) + " : "
                    + cursor.getString(cursor.getColumnIndex("sage")));
            cursor.moveToNext();
        }
        return data;
    }

    public void insertData(String name, int age)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put("name", name);
        data.put("age", age);
        db.insert("admins", null, data);
    }

    public ArrayList<String> getData()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> data = new ArrayList<String>();
        Cursor cursor = db.rawQuery("SELECT * FROM admins", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            data.add(cursor.getString(cursor.getColumnIndex("id")) + " : "
                    + cursor.getString(cursor.getColumnIndex("name")) + " : "
                    + cursor.getString(cursor.getColumnIndex("age")) + "\n");
            cursor.moveToNext();
        }
        return data;
    }

    public ArrayList<String> getSearch(String searchWord)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> data = new ArrayList<String>();
        Cursor cursor = db.rawQuery("SELECT * FROM admins WHERE name LIKE '%" + searchWord + "%'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            data.add(cursor.getString(cursor.getColumnIndex("id")) + " : " +
                    cursor.getString(cursor.getColumnIndex("name")) + " : " +
                    cursor.getString(cursor.getColumnIndex("age")) + "\n");
            cursor.moveToNext();
        }
        return data;
    }

    public void update(int id, String newName)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE admins SET name = '" + newName + "' WHERE id = " + id);
    }

    public void delete(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM admins WHERE id = " + id);
    }

}
