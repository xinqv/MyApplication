package com.example.administrator.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/8/1.
 */

public class MyDBOpenHelper extends SQLiteOpenHelper {

    private static MyDBOpenHelper myDBOpenHelper;

    public static MyDBOpenHelper newInstance(Context context){
        if(myDBOpenHelper == null){
            myDBOpenHelper = new MyDBOpenHelper(context,"my.db",null,1);
        }
        return  myDBOpenHelper;
    }

    public MyDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version) {super(context, name, factory, version); }
    @Override
    //数据库第一次创建时被调用
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE person(personid INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(20))");

    }
    //软件版本号发生改变时调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE person ADD phone VARCHAR(12) NULL");
    }
}
