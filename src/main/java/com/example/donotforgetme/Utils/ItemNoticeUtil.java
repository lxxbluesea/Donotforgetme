package com.example.donotforgetme.Utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.donotforgetme.DB.MyDbHelper;
import com.example.donotforgetme.Entities.Item;
import com.example.donotforgetme.Entities.ItemNotice;
import com.example.donotforgetme.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZJGJK03 on 2015/3/9.
 */
public class ItemNoticeUtil {
//    private static ItemNoticeUtil ourInstance = null;
//
//    public static ItemNoticeUtil getInstance(Item item) {
//        if (ourInstance == null)
//            ourInstance = new ItemNoticeUtil(item);
//        return ourInstance;
//    }

    String TableName;
    SQLiteDatabase DB;
    String[] columns = {"id", "itemid", "noticeid", "noticetime"};
    String sortBy = "id asc";
    Item item;

    public ItemNoticeUtil(Item item) {
        TableName = ApplicationUtil.getContext().getResources().getString(R.string.itemnoticetable);
        DB = MyDbHelper.getDBInstance();
        this.item = item;
    }

    public List<ItemNotice> getAllNotice() {
        List<ItemNotice> noticeList = new ArrayList<ItemNotice>();

        if (item != null) {
            int itemid = item.getID();
            int times = item.getNoticeTime();
            Cursor cursor = DB.query(TableName, columns, "itemid=?", new String[]{itemid + ""}, null, null, sortBy, times + "");
            getItemNotice(cursor, noticeList);
        }

        return noticeList;
    }

    /**
     * 获取ItemNotice对象，默认是铃声
     * @return
     */
    public ItemNotice getItemNotice() {
        ItemNotice notice=new ItemNotice();
        notice.setItemID(item.getID());
        notice.setType(ItemNotice.ADD);
        notice.setNoticeID(NoticeUtil.RING);
        return notice;
    }
    /**
     * 获取ItemNotice对象
     * @param type 通过传入不同的类型，来获取不同的对象
     * @return
     */
    public ItemNotice getItemNotice(int type)
    {
        ItemNotice notice=new ItemNotice();
        notice.setItemID(item.getID());
        notice.setType(NoticeUtil.VOICE);
        notice.setNoticeID(NoticeUtil.getInstance().getNoticeByID(type).getID());
        return notice;
    }


    public boolean AddItemNotices(List<ItemNotice> noticeList) {
        boolean flag = false;

        if (noticeList != null && !noticeList.isEmpty()) {
            boolean result;
            for (ItemNotice notice : noticeList) {
                result = AddItemNotice(notice);
                if (result) {
                    flag = true;
                } else {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    public boolean AddItemNotice(ItemNotice notice) {
        boolean flag ;

        ContentValues values = new ContentValues();
        values.put("itemid", item.getID());
        values.put("noticeid", notice.getNoticeID());
        values.put("noticetime", notice.getNoticeTime());

        long result = DB.insert(TableName, null, values);
        if (result > 0)
            flag = true;
        else flag = false;
        return flag;
    }

    public boolean ModifyItemNotice(ItemNotice notice) {
        boolean flag ;
        ContentValues values = new ContentValues();
        values.put("itemid", item.getID());
        values.put("noticeid", notice.getNoticeID());
        values.put("noticetime", notice.getNoticeTime());

        long result = DB.update(TableName, values, "id=?", new String[]{notice.getID() + ""});
        if (result > 0)
            flag = true;
        else flag = false;
        return flag;
    }

    /**
     *删除一个提醒，根据提醒的ID来删除
     * @param notice
     * @return
     */
    public boolean DeleteItemNotice(ItemNotice notice) {
        boolean flag;

        flag = DeleteItemNotice(notice.getID());

        return flag;
    }

    /**
     *删除一个Item对应的所有提醒
     * @return
     */
    boolean DeleteItemNotices() {
        boolean flag ;
        int result = DB.delete(TableName, "itemid=?", new String[]{item.getID() + ""});
        if (result > 0)
            flag = true;
        else
            flag = false;

        return flag;
    }

    /**
     * 删除一个提醒
     * @param id 传入该提醒的ID
     * @return
     */
    boolean DeleteItemNotice(int id) {
        boolean flag ;

        int result = DB.delete(TableName, "id=?", new String[]{id + ""});
        if (result > 0)
            flag = true;
        else
            flag = false;

        return flag;
    }

    /**
     * 获得与Item相关联的提醒的数量
     * @return 返回提醒的数量
     */
    public  int getItemNoticeCount()
    {
        int count=0;
        Cursor cursor=DB.query(TableName,new String[]{"count(id)"},"itemid=?",new String[]{item.getID()+""},null,null,null);
        try
        {
            if(cursor!=null && cursor.moveToFirst())
            {
                count=cursor.getInt(0);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            cursor.close();

        }

        return count;
    }

    void getItemNotice(Cursor cursor, List<ItemNotice> noticeList) {
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    ItemNotice notice = getItemNotice();
                    notice.setID(cursor.getInt(0));
                    notice.setItemID(cursor.getInt(1));
                    notice.setNoticeID(cursor.getInt(2));
                    notice.setNoticeTime(cursor.getLong(3));

                    noticeList.add(notice);

                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
    }
}
