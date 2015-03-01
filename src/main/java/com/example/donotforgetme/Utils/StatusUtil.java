package com.example.donotforgetme.Utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.donotforgetme.DB.MyDbHelper;
import com.example.donotforgetme.Entities.Status;
import com.example.donotforgetme.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZJGJK03 on 2015/2/28.
 */
public class StatusUtil {
    static StatusUtil statusUtil;
    String TableName;
    SQLiteDatabase DB;
    String[] columns={"id","name"};
    String sortBy="id asc";

    private StatusUtil()
    {
        TableName=ApplicationUtil.getContext().getResources().getString(R.string.statustable);
        DB=MyDbHelper.getDBInstance();
    }

    /**
     * 返回StatusUtil的实例
     * @return
     */
    public static StatusUtil getInstance() {
        if (statusUtil == null)
            statusUtil = new StatusUtil();
        return statusUtil;
    }

    public Status getStatus()
    {
        return new Status();
    }

    /**
     * 获得所有的状态，并返回集合
     * @return
     */
    public List<Status> getAllStatus()
    {
        List<Status> statusList=new ArrayList<Status>();
        Cursor cursor=DB.query(TableName,columns,null,null,null,null,sortBy);
        getStatus(cursor, statusList);
        return statusList;
    }

    /**
     * 通过ID返回单个状态对象，如果不存在，返回空
     * @param id
     * @return
     */
    public Status getStatusByID(int id) {
        List<Status> statusList = new ArrayList<Status>();
        Cursor cursor = DB.query(TableName, columns, "id=?", new String[]{id + ""}, null, null, sortBy);
        getStatus(cursor, statusList);
        if (statusList.isEmpty())
            return null;
        else
            return statusList.get(0);
    }

    /**
     * 通过name返回状态结象，如果不存在，返回空
     * @param name
     * @return
     */
    public Status getStatusByName(String name) {
        List<Status> statusList = new ArrayList<Status>();
        Cursor cursor = DB.query(TableName, columns, "name=?", new String[]{name}, null, null, sortBy);
        getStatus(cursor, statusList);
        if (statusList.isEmpty())
            return null;
        else
            return statusList.get(0);
    }

    /**
     * 将Cursor内包含的数据填充到集合中
     * @param statusList
     * @param cursor
     */
    void getStatus(Cursor cursor,List<Status> statusList)
    {
        try
        {
            if(cursor!=null && cursor.moveToFirst())
            {
                do {
                    Status status=getStatus();
                    status.setID(cursor.getInt(0));
                    status.setName(cursor.getString(1));
                    statusList.add(status);
                }while (cursor.moveToNext());
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
