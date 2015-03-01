package com.example.donotforgetme.DB;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ZJGJK03 on 2015/2/25.
 */
public class MyDbHelper {
    //static MyDbHelper ourInstance;

    static SQLiteDatabase db;

    public static SQLiteDatabase getDBInstance() {

        if (db == null) {
            SqliteHelper helper = new SqliteHelper();
            try {
                db = helper.getWritableDatabase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return db;
    }

    private MyDbHelper() {
    }

}
