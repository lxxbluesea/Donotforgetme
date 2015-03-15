package com.example.donotforgetme.Utils;

import com.example.donotforgetme.Entities.Item;
import com.example.donotforgetme.Entities.ItemNotice;

import junit.framework.TestCase;

import java.util.Date;
import java.util.List;

/**
 * Created by ZJGJK03 on 2015/3/10.
 */
public class testItemUtil extends TestCase {
    ItemUtil itemUtil;
    Item item;
    boolean flag;
    Date date;
    long time;
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        itemUtil = new ItemUtil();

        long offset = 60 * 60 * 1000;
        date = new Date();
        time = date.getTime();
        item = itemUtil.getNewItem();
        item.setCreateDateTime(time);
        item.setLevel(LevelUtil.AVERAGER);
        item.setContent("this is a test");
        item.setBeginDateTime(time + offset * 2);
        item.setEndDateTime(time + offset * 5);
        item.setNoticeTime(3);
        itemUtil.setItem(item);
        itemUtil.setNoticeTimes(4);

        flag = itemUtil.SaveItem();

        assertEquals(true, flag);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();

        flag=itemUtil.DeleteItem(item.getID());
        assertEquals(true,flag);

    }

    public void testItemInfo()
    {

        int count=itemUtil.getItemCount();
        assertEquals(1,count);
        count=itemUtil.itemNoticeUtil.getItemNoticeCount();
        assertEquals(4,count);
        count=itemUtil.itemStatusUtil.getItemStatusCount();
        assertEquals(1,count);

    }

    public void testGetItemByType()
    {
        List<Item> itemList=itemUtil.getItems(StatusUtil.EXECUTE);
        if(itemList==null)
            fail();
        assertEquals(1,itemList.size());
        itemUtil.getItemByID(itemList.get(0).getID());
        assertEquals(1,itemList.size());
        assertEquals(1,itemList.get(0).getID());
        assertEquals("this is a test",itemList.get(0).getContent());
        assertEquals(itemList.get(0).getNoticeTime(),itemUtil.itemNoticeUtil.getItemNoticeCount());
    }

    public void testModifyItemInfo()
    {
        Item item1=itemUtil.getItemByID(1);
        if(item1==null)
            fail();

        itemUtil.ModifyNoticeTimes(5);
        assertEquals(ItemNotice.MODIFY,itemUtil.noticeList.get(3).getType());
        assertEquals(ItemNotice.ADD,itemUtil.noticeList.get(4).getType());
        itemUtil.SaveItem(2);

        int count=itemUtil.getItemCount();
        assertEquals(1,count);
        count=itemUtil.itemNoticeUtil.getItemNoticeCount();
        assertEquals(item1.getNoticeTime(),count);
        count=itemUtil.itemStatusUtil.getItemStatusCount();
        assertEquals(1,count);
    }

    public void testSearchItemByContent()
    {
        List<Item> itemList=itemUtil.getItemByContent("test");
        itemUtil.getItemByID(itemList.get(0).getID());
        assertEquals(1,itemList.size());
        assertEquals(1,itemList.get(0).getID());
        assertEquals("this is a test",itemList.get(0).getContent());
        assertEquals(itemList.get(0).getNoticeTime(),itemUtil.itemNoticeUtil.getItemNoticeCount());
    }

    public void testSearchItemByDatetime()
    {
        List<Item> itemList=itemUtil.getItemByExecuteDateTime(time+(60 * 60 * 1000)*3);
        itemUtil.getItemByID(itemList.get(0).getID());
        assertEquals(1,itemList.size());
        assertEquals(1,itemList.get(0).getID());
        assertEquals("this is a test",itemList.get(0).getContent());
        assertEquals(itemList.get(0).getNoticeTime(),itemUtil.itemNoticeUtil.getItemNoticeCount());
    }
}
