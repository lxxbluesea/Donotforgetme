package com.example.donotforgetme.Utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.donotforgetme.DB.MyDbHelper;
import com.example.donotforgetme.Entities.Notice;
import com.example.donotforgetme.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZJGJK03 on 2015/2/28.
 */
public class NoticeUtil {
    private static NoticeUtil ourInstance = new NoticeUtil();
    public static NoticeUtil getInstance() {
        return ourInstance;
    }
    String TableName;
    SQLiteDatabase DB;
    String[] columns={"id","name","enable","ringpath","zhendongnum"};
    String sortBy="id asc";
    private NoticeUtil() {
        TableName = ApplicationUtil.getContext().getResources().getString(R.string.noticetable);
        DB = MyDbHelper.getDBInstance();
    }
    public List<Notice> getAllNotice()
    {
        List<Notice> noticeList=new ArrayList<Notice>();
        Cursor cursor=DB.query(TableName,columns,null,null,null,null,sortBy);
        getNotice(cursor,noticeList);
        return noticeList;
    }

    public Notice getNoticeByID(int id)
    {
        List<Notice> noticeList=new ArrayList<Notice>();
        Cursor cursor=DB.query(TableName,columns,"id=?",new String[]{id+""},null,null,sortBy);
        getNotice(cursor,noticeList);
        if(noticeList.isEmpty())
            return null;
        else
            return noticeList.get(0);
    }

    public Notice getNoticeByName(String name) {
        List<Notice> noticeList = new ArrayList<Notice>();
        Cursor cursor = DB.query(TableName, columns, "name=?", new String[]{name}, null, null, sortBy);
        getNotice(cursor, noticeList);
        if (noticeList.isEmpty())
            return null;
        else
            return noticeList.get(0);
    }

    public Notice getNotice()
    {
        return new Notice();
    }

    void getNotice(Cursor cursor,List<Notice> noticeList)
    {
        try
        {
            if(cursor!=null && cursor.moveToFirst())
            {
                do {
                    Notice notice=getNotice();
                    notice.setID(cursor.getInt(0));
                    notice.setName(cursor.getString(1));
                    notice.setEnable(cursor.getInt(2));
                    notice.setRingPath(cursor.getString(3));
                    notice.setZhenDongNum(cursor.getInt(4));

                    noticeList.add(notice);

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
