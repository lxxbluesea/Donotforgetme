package com.example.donotforgetme.Utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.donotforgetme.DB.MyDbHelper;
import com.example.donotforgetme.Entities.Level;
import com.example.donotforgetme.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZJGJK03 on 2015/3/1.
 */
public class LevelUtil {
    private static LevelUtil ourInstance = new LevelUtil();

    public static LevelUtil getInstance() {
        return ourInstance;
    }

    String TableName;
    SQLiteDatabase DB;
    String[] columns = {"id", "name"};
    String sortBy = "id asc";

    private LevelUtil() {
        TableName = ApplicationUtil.getContext().getResources().getString(R.string.leveltable);
        DB = MyDbHelper.getDBInstance();
    }

    public List<Level> getAllLevel() {
        List<Level> levelList = new ArrayList<Level>();
        Cursor cursor = DB.query(TableName, columns, null, null, null, null, sortBy);
        getLevel(cursor, levelList);
        return levelList;
    }

    public Level getLevelByID(int id) {
        List<Level> levelList = new ArrayList<Level>();
        Cursor cursor = DB.query(TableName, columns, "id=?", new String[]{id + ""}, null, null, sortBy);
        getLevel(cursor, levelList);
        if (levelList.isEmpty())
            return null;
        else
            return levelList.get(0);
    }

    public Level getBackupTypeByName(String name) {
        List<Level> levelList = new ArrayList<Level>();
        Cursor cursor = DB.query(TableName, columns, "name=?", new String[]{name}, null, null, sortBy);
        getLevel(cursor, levelList);
        if (levelList.isEmpty())
            return null;
        else
            return levelList.get(0);
    }

    public Level getLevel() {
        return new Level();
    }

    void getLevel(Cursor cursor, List<Level> levelList) {
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Level level = getLevel();
                    level.setID(cursor.getInt(0));
                    level.setName(cursor.getString(1));

                    levelList.add(level);

                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
    }
}
