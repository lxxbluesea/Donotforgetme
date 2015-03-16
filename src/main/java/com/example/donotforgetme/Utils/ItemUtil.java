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
import java.util.Date;
import java.util.List;

/**
 * Created by ZJGJK03 on 2015/3/8.
 */
public class ItemUtil {

    public static final int ADD = 1, UPDATE = 2;

    //private static ItemUtil ourInstance = new ItemUtil();

//    public static ItemUtil getInstance() {
//        return ourInstance;
//    }

    String TableName= ApplicationUtil.getContext().getResources().getString(R.string.itemtable);;//,ItemStatusTableName,ItemNoticeTableName;
    SQLiteDatabase DB = MyDbHelper.getDBInstance();
    String[] columns = {"id", "content", "level", "createdatetime", "begindatetime", "enddatetime", "noticetime"};
    String sortBy = "id desc";
    //获得可用ID
    int MaxID;
    ItemStatusUtil itemStatusUtil;
    ItemNoticeUtil itemNoticeUtil;
    //实例对象
    Item item;

    public List<ItemNotice> getNoticeList() {
        return noticeList;
    }
    public ItemStatus getStatus() {
        return status;
    }

    public List<ItemStatus> getStatusList() {
        return statusList;
    }



    List<ItemNotice> noticeList;
    ItemStatus status;
    List<ItemStatus> statusList;


    public ItemUtil() {

        MaxID = MyDbHelper.getMaxID(TableName) + 1;
        //实例化对象，并设置ID
        item = new Item();
        item.setID(MaxID);
        item.setCreateDateTime(new Date().getTime());
        item.setBeginDateTime(new Date().getTime());
        item.setEndDateTime(new Date().getTime() + DateUtil.Onehour);
        //实例化两个对象

        itemNoticeUtil = new ItemNoticeUtil(item);
        itemStatusUtil = new ItemStatusUtil(item);
        //生成3个默认的提醒和1个状态
        getNewItemNotices();
        //noticeList=new ArrayList<ItemNotice>();
        setNoticeTimes(item.getNoticeTime());
        getNewItemStatus();

    }

    public ItemUtil(int id) {
        item = getItemByID(id);
    }

    /**
     * 返回Item的新对象
     *
     * @return
     */
    public Item getNewItem() {
        return item;
    }
    /**
     * 返回Item对象
     *
     * @return
     */
    public Item getItem() {
        return item;
    }

    /**
     * 设置Item对象，在即将保存前进行设置，并作为保存的对象
     * @param item
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * 通过ID获得Item对象
     * @param id
     * @return
     */
    public Item getItemByID(int id) {
        List<Item> itemList = new ArrayList<Item>();
        Cursor cursor = DB.query(TableName, columns, "id=?", new String[]{id + ""}, null, null, sortBy);
        getItem(cursor, itemList);
        if (itemList.isEmpty())
            item = null;
        else {
            item = itemList.get(0);
            itemNoticeUtil=new ItemNoticeUtil(item);
            itemStatusUtil=new ItemStatusUtil(item);
            noticeList=itemNoticeUtil.getAllNotice();
            status=itemStatusUtil.getCurrentStatus();
            statusList=itemStatusUtil.getAllStatus();
        }

        return item;
    }

    /**
     * 获得3个新提醒对象
     *
     * @return
     */
    public List<ItemNotice> getNewItemNotices() {

        noticeList = new ArrayList<ItemNotice>();
        noticeList.add(itemNoticeUtil.getItemNotice());
        noticeList.add(itemNoticeUtil.getItemNotice(NoticeUtil.SMS));
        noticeList.add(itemNoticeUtil.getItemNotice(NoticeUtil.VOICE));
        return noticeList;
    }

    /**
     * 获得一个新状态对象
     *
     * @return
     */
    public ItemStatus getNewItemStatus() {
        status = itemStatusUtil.getItemStatus();
        return status;
    }

    /**
     * 获得多个已存在的提醒对象
     *
     * @return
     */
    public List<ItemNotice> getItemNotices() {
        noticeList = itemNoticeUtil.getAllNotice();
        return noticeList;
    }

    /**
     * 获得当前状态对象
     *
     * @return
     */
    public ItemStatus getItemStatus() {
        status = itemStatusUtil.getCurrentStatus();
        return status;
    }

    /**
     * 获得多个状态对象
     *
     * @return
     */
    public List<ItemStatus> getAllItemStatus() {
        statusList = itemStatusUtil.getAllStatus();
        return statusList;
    }

    /**
     * 设置提醒的次数,用于新添加，还没有保存到数据库中时
     * 不建议直接使用Item.SetNoticeTime来设置
     * 设计上的问题
     * 后续再优化吧
     * @param times
     */
    public void setNoticeTimes(int times)
    {
        if(times<=0)
            return;
        if(noticeList!=null && !noticeList.isEmpty())
        {
            int size=noticeList.size();
            //设置的次数大于已存在的次数
            //则需要再添加提醒的数量
            if(times>size)
            {
                int tmp=times-size;
                for(int i=0;i<tmp;i++)
                {
                    noticeList.add(itemNoticeUtil.getItemNotice());
                }
            }
            else if(times<size)//如果提醒的次数小于已存在的次数，需要删除
            {
                int tmp=size-times;
                for(int i=0;i<tmp;i++) {
                    noticeList.remove(noticeList.size() - 1);//删除最后一个元素
                }
            }
            else//相等时就什么都不做
            {

            }
            item.setNoticeTime(times);//设置提醒的次数
            //这里处理提醒时间
            //先获得两个时间差，再除以提醒次数，得出每次提醒的时间段
            long timeoffset=item.getEndDateTime()-item.getBeginDateTime();
            long offset=timeoffset/times;
            //这里不设置最后一次提醒，放在下面的代码里处理，因为作除法操作时会涉及到四舍五入，很有可以最后一次提醒不太准，但也有可能只是差几十或几百毫秒
            //所以这里需要实际的测试才有结果
            for(int i=0;i<times-1;i++) {
                noticeList.get(i).setNoticeTime(item.getBeginDateTime() + (i + 1) * offset);
            }
            //最后一次直接设置为结束时间
            noticeList.get(times-1).setNoticeTime(item.getEndDateTime());
        }
    }

    /**
     * 修改提醒的次数,用于读取数据库中的Item
     * @param times
     */
    public void ModifyNoticeTimes(int times)
    {
        if(noticeList!=null && !noticeList.isEmpty())
        {
            int size=noticeList.size();
            //把所有的原始对象设置为修改的状态
            //因为下面的代码将会修改其提醒的日期时间
            for(int i=0;i<size;i++)
            {
                noticeList.get(i).setType(ItemNotice.MODIFY);
            }
            //设置的次数大于已存在的次数
            //则需要再添加提醒的数量
            if(times>size)
            {
                int tmp=times-size;
                for(int i=0;i<tmp;i++)
                {
                    //新添加的提醒，状态为添加
                    noticeList.add(itemNoticeUtil.getItemNotice());
                }
            }
            else if(times<size)//如果提醒的次数小于已存在的次数，需要删除
            {
                int tmp=size-times;
                for(int i=0;i<tmp;i++) {
                    noticeList.get(size + i).setType(ItemNotice.DELETE);//删除最后一个元素
                }
            }
            else//相等时就什么都不做
            {

            }
            item.setNoticeTime(times);//设置提醒的次数
            //这里处理提醒时间
            //先获得两个时间差，再除以提醒次数，得出每次提醒的时间段
            long timeoffset=item.getEndDateTime()-item.getBeginDateTime();
            long offset=timeoffset/times;
            //这里不设置最后一次提醒，放在下面的代码里处理，因为作除法操作时会涉及到四舍五入，很有可以最后一次提醒不太准，但也有可能只是差几十或几百毫秒
            //所以这里需要实际的测试才有结果
            for(int i=0;i<times-1;i++) {
                noticeList.get(i).setNoticeTime(item.getBeginDateTime() + (i + 1) * offset);
            }
            //最后一次直接设置为结束时间
            noticeList.get(times-1).setNoticeTime(item.getEndDateTime());
        }
    }

    void getItem(Cursor cursor, List<Item> itemList) {
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Item item = new Item();
                    item.setID(cursor.getInt(0));
                    item.setContent(cursor.getString(1));
                    item.setLevel(cursor.getInt(2));
                    item.setCreateDateTime(cursor.getLong(3));
                    item.setBeginDateTime(cursor.getLong(4));
                    item.setEndDateTime(cursor.getLong(5));
                    item.setNoticeTime(cursor.getInt(6));

                    itemList.add(item);

                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
    }

    /**
     * 获得指定类型的Item
     *
     * @param type 定义在StatusUtil中
     *             EXECUTE=1,FINISH=2,RELAY=3,DELETE=4,NOTE=5
     * @return
     */
    public List<Item> getItems(int type) {
        List<Item> itemList = new ArrayList<Item>();
        Cursor cursor = DB.query(TableName, columns, " id in (select itemid from itemstatus where statusid=? group by itemid)", new String[]{type + ""}, null, null, sortBy);
        getItem(cursor, itemList);
        return itemList;
    }

    /**
     * 对内容进行关键字匹配，忽略类型
     *
     * @param keyword
     * @return
     */
    public List<Item> getItemByContent(String keyword) {
        List<Item> itemList = new ArrayList<Item>();
        Cursor cursor = DB.query(TableName, columns, "content like ? ", new String[]{"%"+keyword+"%"}, null, null, sortBy);
        getItem(cursor, itemList);
        return itemList;
    }

    /**
     * 通过日期来搜索
     * @param datetime 是指在开始和结束之间的Item都将返回，此参数将大于开始日期和小于结束日期
     * @return
     */
    public List<Item> getItemByExecuteDateTime(long datetime) {
        List<Item> itemList = new ArrayList<Item>();
        Cursor cursor = DB.query(TableName, columns, "begindatetime < ? and enddatetime > ?", new String[]{datetime + "", datetime + ""}, null, null, sortBy);
        getItem(cursor, itemList);
        return itemList;
    }

    /**
     * 保存一个对象到数据库中
     * 如果是新添加，则所有的参数必须全部提供
     * 如果修改，Notice和Status可以为空
     * @return
     */
    public boolean SaveItem() {
        boolean flag = false;
        long result;
        //int itemID = item.getID();
        ContentValues values = new ContentValues();

        values.put("id", item.getID());
        values.put("content", item.getContent());
        values.put("level", item.getLevel());
        values.put("createdatetime", item.getCreateDateTime());
        values.put("begindatetime", item.getBeginDateTime());
        values.put("enddatetime", item.getEndDateTime());
        values.put("noticetime", item.getNoticeTime());


        try {
            //添加一个新的Item

            result = DB.insert(TableName, null, values);
            if (result > 0) {
                flag = itemNoticeUtil.AddItemNotices(noticeList);
                flag = itemStatusUtil.AddItemStatus(status);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //DB.endTransaction();
        }

        return flag;
    }

    /**
     * 保存一个对象到数据库中
     * 如果是新添加，则所有的参数必须全部提供
     * 如果修改，Notice和Status可以为空
     *
     * @param type       1为新增，2为更新
     * @return
     */
    public boolean SaveItem(int type) {
        boolean flag = false;
        long result;
        //int itemID = item.getID();
        ContentValues values = new ContentValues();

        values.put("id", item.getID());
        values.put("content", item.getContent());
        values.put("level", item.getLevel());
        values.put("createdatetime", item.getCreateDateTime());
        values.put("begindatetime", item.getBeginDateTime());
        values.put("enddatetime", item.getEndDateTime());
        values.put("noticetime", item.getNoticeTime());


        try {
            //添加一个新的Item
            if(type==1)
            {
                result=DB.insert(TableName, null, values);
                if(result>0)
                {
                    flag=itemNoticeUtil.AddItemNotices(noticeList);
                    flag=itemStatusUtil.AddItemStatus(status);
                }
            }
            //更新一个Item
            else if(type==2) {
                result = DB.update(TableName, values, "id=?", new String[]{item.getID() + ""});
                if(result>0)
                {
                    if(noticeList!=null && !noticeList.isEmpty()) {
                        //处理提醒，
                        // 如果传入空，则不处理
                        for (ItemNotice notice : noticeList) {
                            if(notice.getType()==1)
                            {
                                flag=itemNoticeUtil.AddItemNotice(notice);
                            }
                            else if (notice.getType() == 2)//修改
                            {
                                flag = itemNoticeUtil.ModifyItemNotice(notice);
                            } else if (notice.getType() == 3)//删除
                            {
                                flag = itemNoticeUtil.DeleteItemNotice(notice);
                            }
                        }
                    }
                }
            }

//            long result = -1;
//            //添加一个对象
//            if (type == 1) {
//                result = DB.insert(TableName, null, values);
//                if (result < 0) {//大于0则保存成功
//                    flag = false;
//                    return flag;
//                }
//
//
//            } else if (type == 2) {//修改一个对象
//                result = DB.update(TableName, values, "id=?", new String[]{item.getID() + ""});
//                if (result < 0) {//大于0则保存成功
//                    flag = false;
//                    return flag;
//                }
//            } else {//直接返回操失败
//                flag = false;
//                return flag;
//            }
//            //这里不管是新添加Item对象或者修改，都将添加新的提醒和状态
//            //将提醒信息存入数据库
//            for (ItemNotice notice : noticeList) {
//                notice.setItemID(itemID);
//                values = new ContentValues();
//                values.put("itemid", notice.getItemID());
//                values.put("noticeid", notice.getNoticeID());
//                values.put("noticetime", notice.getNoticeTime());
//                result = DB.insert(ItemNoticeTableName, null, values);
//                if (result < 0) {
//                    flag = false;
//                    return flag;
//                }
//            }
//            //将状态信息存入数据库
//            status.setItemID(itemID);
//            values = new ContentValues();
//            values.put("itemid", status.getItemID());
//            values.put("statusid", status.getStatusID());
//            values.put("opdatetime", status.getOpDateTime());
//            values.put("note", status.getNote());
//            result = DB.insert(ItemStatusTableName, null, values);
//            if (result < 0) {
//                flag = false;
//                return flag;
//            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //DB.endTransaction();
        }

        return flag;
    }

    /**
     * 更新Item的状态，这里需要指定类型
     * 例如：执行中到结束或者到滞后
     * type定义在StatusUtil中
     * EXECUTE=1,FINISH=2,RELAY=3,DELETE=4,NOTE=5
     * @param type
     * @return
     */
    public boolean ChangeItemStatus(int type)
    {
        boolean flag;

        status=itemStatusUtil.getItemStatus(type);
        flag=itemStatusUtil.AddItemStatus(status);
        return flag;
    }

    /**
     * 删除一个Item
     * @param id 传入需要删除的ID
     * @return 删除成功，返回true,否则返回false
     */
    public boolean DeleteItem(int id)
    {
        boolean flag=false;
        long result;
        try {
            result=DB.delete(TableName,"id=?",new String[]{id+""});
            if(result>0)
            {
                flag=itemNoticeUtil.DeleteItemNotices();
                flag=itemStatusUtil.DeleteItemStatus();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 返回Item的总数量
     * @return
     */
    public int getItemCount()
    {
        int count=0;
        Cursor cursor=DB.query(TableName,new String[]{"count(id)"},null,null,null,null,null);
        try
        {
            if(cursor!=null && cursor.moveToFirst())
            {
                count=cursor.getInt(0);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            cursor.close();
        }

        return count;
    }


}
