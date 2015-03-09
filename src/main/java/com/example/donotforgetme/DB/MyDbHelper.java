package com.example.donotforgetme.DB;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ZJGJK03 on 2015/2/25.
 */
public class MyDbHelper {
    //static MyDbHelper ourInstance;

    static SQLiteDatabase db;

    /**
     * 获得数据库实例
     * @return
     */
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

    /**
     * 返回指定表的最大ID号
     * @param tablename
     * @return
     */

    public static int getMaxID(String tablename) {
        int maxid = 0;
        Cursor cursor = db.query(tablename, new String[]{"max(id)"}, null, null, null, null, null);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                maxid = cursor.getInt(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return maxid ;
    }

    private MyDbHelper() {
    }

}
