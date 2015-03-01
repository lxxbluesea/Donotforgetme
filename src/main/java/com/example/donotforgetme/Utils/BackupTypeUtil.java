package com.example.donotforgetme.Utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.donotforgetme.DB.MyDbHelper;
import com.example.donotforgetme.Entities.BackupType;
import com.example.donotforgetme.Entities.InterfaceEntity;
import com.example.donotforgetme.Entities.Notice;
import com.example.donotforgetme.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZJGJK03 on 2015/2/28.
 */
public class BackupTypeUtil{

    private static BackupTypeUtil ourInstance = new BackupTypeUtil();
    public static BackupTypeUtil getInstance() {
        return ourInstance;
    }
    String TableName;
    SQLiteDatabase DB;
    String[] columns={"id","name"};
    String sortBy="id asc";
    private BackupTypeUtil() {
        TableName = ApplicationUtil.getContext().getResources().getString(R.string.backuptypetable);
        DB = MyDbHelper.getDBInstance();
    }
    public List<BackupType> getAllBackupType()
    {
        List<BackupType> backupTypeList=new ArrayList<BackupType>();
        Cursor cursor=DB.query(TableName,columns,null,null,null,null,sortBy);
        getBackupType(cursor,backupTypeList);
        return backupTypeList;
    }

    public BackupType getBackupTypeByID(int id)
    {
        List<BackupType> backupTypeList=new ArrayList<BackupType>();
        Cursor cursor=DB.query(TableName,columns,"id=?",new String[]{id+""},null,null,sortBy);
        getBackupType(cursor,backupTypeList);
        if(backupTypeList.isEmpty())
            return null;
        else
            return backupTypeList.get(0);
    }

    public BackupType getBackupTypeByName(String name) {
        List<BackupType> backupTypeList=new ArrayList<BackupType>();
        Cursor cursor = DB.query(TableName, columns, "name=?", new String[]{name}, null, null, sortBy);
        getBackupType(cursor, backupTypeList);
        if (backupTypeList.isEmpty())
            return null;
        else
            return backupTypeList.get(0);
    }

    public BackupType getNotice()
    {
        return new BackupType();
    }

    void getBackupType(Cursor cursor,List<BackupType> backupTypeList)
    {
        try
        {
            if(cursor!=null && cursor.moveToFirst())
            {
                do {
                    BackupType backupType=getNotice();
                    backupType.setID(cursor.getInt(0));
                    backupType.setName(cursor.getString(1));

                    backupTypeList.add(backupType);

                }while (cursor.moveToNext());
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            cursor.close();
        }
    }
}
