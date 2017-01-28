package com.techpalle.databaselistviewexample1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by DELL on 02-Jan-17.
 */
public class MyDataBase {
    MyHelper helper;
    SQLiteDatabase sqLiteDatabase;

    public MyDataBase(Context c){
//        helper = new MyHelper(c, "palleUniversity.db", null,1);
        //3/Jan/2017- RELEASING NEXT VERSION OF DATABASE- CHANGED BY: YUGAL
        helper = new MyHelper(c, "palleUniversity.db", null, 2);
    }

    public void open(){
        sqLiteDatabase  = helper.getWritableDatabase();
    }

    public void insertValue(String name, String subject){
        ContentValues c = new ContentValues();
        c.put("name", name);
        c.put("subject", subject);
        sqLiteDatabase.insert("student", null, c);
    }

    public Cursor queryStudent(){
        Cursor c = null;
        c = sqLiteDatabase.query("student", null, null, null, null, null, null);
        return c;
    }
    public void deleteValue(String name){
        sqLiteDatabase.delete("student", "name =?", new String[]{name});
    }

    public void close(){
        sqLiteDatabase.close();
    }

    private class MyHelper extends SQLiteOpenHelper{

        public MyHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table student(_id integer primary key, name text, subject text);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            switch (newVersion){
                case 2 : sqLiteDatabase.execSQL("create table jobs(_id integer primary key, jdesc text, subject text);");
//                    sqLiteDatabase.execSQL("drop table student if exists;");
                    sqLiteDatabase.execSQL("alter table student add column scomp text;");
                    break;
                case 3 :
                    break;
            }
        }
    }
}

