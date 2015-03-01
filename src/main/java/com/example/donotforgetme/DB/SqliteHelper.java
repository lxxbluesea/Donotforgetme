package com.example.donotforgetme.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.donotforgetme.R;
import com.example.donotforgetme.Utils.ApplicationUtil;

/**
 * Created by ZJGJK03 on 2015/2/25.
 */
public class SqliteHelper extends SQLiteOpenHelper {

    static final String db_Name= ApplicationUtil.getContext().getResources().getString(R.string.databaseName);
    static int VERSION=Integer.parseInt(ApplicationUtil.getContext().getResources().getString(R.string.databaseVersion));
    static String[] initSql=ApplicationUtil.getContext().getResources().getStringArray(R.array.initSql);
    static String[] initData=ApplicationUtil.getContext().getResources().getStringArray(R.array.initData);


    public SqliteHelper() {
        super(ApplicationUtil.getContext(), db_Name, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (db == null) return;
        try {
            for (String sql : initSql) {
                db.execSQL(sql);
            }
            for (String sql : initData) {
                db.execSQL(sql);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion)
        {
            case 1:

            case 2:

        }
    }
}
