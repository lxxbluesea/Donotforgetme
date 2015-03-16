package com.example.donotforgetme.Utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.donotforgetme.DB.MyDbHelper;
import com.example.donotforgetme.Entities.Item;
import com.example.donotforgetme.Entities.ItemNotice;
import com.example.donotforgetme.Entities.ItemStatus;
import com.example.donotforgetme.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZJGJK03 on 2015/3/9.
 */
public class ItemStatusUtil {
//    private static ItemStatusUtil ourInstance;
//
//    public static ItemStatusUtil getInstance(Item item) {
//        if (ourInstance == null)
//            ourInstance = new ItemStatusUtil(item);
//        return ourInstance;
//    }

    String TableName;
    SQLiteDatabase DB;
    String[] columns = {"id", "itemid", "statusid", "opdatetime", "note"};
    String sortBy = "id desc";
    Item item;

    public ItemStatusUtil(Item item) {
        TableName = ApplicationUtil.getContext().getResources().getString(R.string.itemstatustable);
        DB= MyDbHelper.getDBInstance();
        this.item = item;
    }

    /**
     * 获得一个新ItemStatus对象
     * 当前对象的状态是EXECUTE
     * @return
     */
    public ItemStatus getItemStatus()
    {
        ItemStatus status=new ItemStatus();
        status.setItemID(item.getID());
        status.setStatusID(StatusUtil.getInstance().getStatusByID(StatusUtil.EXECUTE).getID());//获各一条执行状态的对象
        return status;
    }
    /**
     * 获得一个指定类型的ItemStatus对象
     * 参数则传入指定的对象
     * 类型定义在StatusUtil中
     * EXECUTE=1,FINISH=2,RELAY=3,DELETE=4,NOTE=5;
     * @return
     */
    public ItemStatus getItemStatus(int type)
    {
        ItemStatus status=new ItemStatus();
        status.setItemID(item.getID());
        status.setStatusID(StatusUtil.getInstance().getStatusByID(type).getID());//获各一条执行状态的对象
        return status;
    }

    /**
     * 获得Item的当前状态
     * 也就是最新的一条状态
     * @return
     */
    public ItemStatus getCurrentStatus() {

        List<ItemStatus> statusList = new ArrayList<ItemStatus>();
        Cursor cursor = DB.query(TableName, columns, "itemid=?", new String[]{item.getID() + ""}, null, null, sortBy, "1");

        getItemStatus(cursor, statusList);
        if (statusList.isEmpty())
            return null;
        else
            return statusList.get(0);
    }

    /**
     * 获取所有的状态
     * 即获得状态列表，包括有以前的所有状态
     * @return
     */
    public List<ItemStatus> getAllStatus()
    {
        List<ItemStatus> statusList = new ArrayList<ItemStatus>();
        Cursor cursor = DB.query(TableName, columns, "itemid=?", new String[]{item.getID() + ""}, null, null, "id asc");
        getItemStatus(cursor, statusList);
        return statusList;
    }

    public boolean AddItemStatus(ItemStatus status)
    {
        boolean flag;
        ContentValues values=new ContentValues();
        values.put("itemid",status.getItemID());
        values.put("statusid",status.getStatusID());
        values.put("opdatetime",status.getOpDateTime());
        values.put("note",status.getNote());

        long result=DB.insert(TableName,null,values);
        if (result>0)
            flag=true;
        else
            flag=false;
        return flag;

    }

    /**
     * 从数据中删除状态，此删除为物理删除，不可恢复
     * 仅在测试时使用，正式的代码不使用
     * @return
     */
    public boolean DeleteItemStatus()
    {
        boolean flag=false;
        long result=0;
        try
        {
            result=DB.delete(TableName,"itemid=?",new String[]{item.getID()+""});
            if(result>0)
            {
                flag=true;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 获得与Item相关联的状态的数量
     * @return 返回状态的数量
     */
    public  int getItemStatusCount()
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
    void getItemStatus(Cursor cursor, List<ItemStatus> statusList) {
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    ItemStatus status = getItemStatus();
                    status.setID(cursor.getInt(0));
                    status.setItemID(cursor.getInt(1));
                    status.setStatusID(cursor.getInt(2));
                    status.setOpDateTime(cursor.getLong(3));

                    status.setNote(cursor.getString(4));

                    statusList.add(status);

                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
    }
}
