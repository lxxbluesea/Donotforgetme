package com.example.donotforgetme.Utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.donotforgetme.DB.MyDbHelper;
import com.example.donotforgetme.Entities.InterfaceEntity;
import com.example.donotforgetme.R;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZJGJK03 on 2015/2/28.
 */
public abstract class AbstractUtil {


    protected String TableName;
    protected SQLiteDatabase DB;

    protected AbstractUtil(int id)
    {
        TableName=ApplicationUtil.getContext().getResources().getString(id);
        DB= MyDbHelper.getDBInstance();
    }

    protected AbstractUtil() {

    }

    public static AbstractUtil getInstance() {

        return null;
    }

    public List<InterfaceEntity> getAllEntity() {
        return null;
    }

    public InterfaceEntity getEntityByID() {
        return null;
    }

    public InterfaceEntity getEntityByName() {
        return null;
    }


    InterfaceEntity getEntity()
    {
        return null;
    }

    void getEntity(Cursor cursor, List<InterfaceEntity> list) {
        try
        {
            if(cursor!=null && cursor.moveToFirst())
            {
                do {
                    InterfaceEntity entity=getEntity();


                }
                while (cursor.moveToNext());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            cursor.close();
        }
    }
}
