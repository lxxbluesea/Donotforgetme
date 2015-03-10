package com.example.donotforgetme.Utils;

import com.example.donotforgetme.Entities.Item;

import junit.framework.TestCase;

import java.util.Date;

/**
 * Created by ZJGJK03 on 2015/3/10.
 */
public class testItemUtil extends TestCase {
    ItemUtil itemUtil;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        if(itemUtil==null)
            itemUtil=ItemUtil.getInstance();
    }

    public void testAddItem()
    {
        if(itemUtil==null)
            fail();

        long offset= 60 * 60 * 1000;
        Date date=new Date();
        Item item=itemUtil.getNewItem();
        item.setCreateDateTime(date.getTime());
        item.setLevel(LevelUtil.AVERAGER);
        item.setContent("this is a test");
        item.setBeginDateTime(date.getTime()+offset*2);
        item.setEndDateTime(date.getTime()+offset*5);
        item.setNoticeTime(3);
        boolean flag;
        flag=itemUtil.SaveItem(item);

        assertEquals(true,flag);


        int count=itemUtil.getItemCount();
        assertEquals(1,count);
        count=itemUtil.itemNoticeUtil.getItemNoticeCount();
        assertEquals(3,count);
        count=itemUtil.itemStatusUtil.getItemStatusCount();
        assertEquals(1,count);



        flag=itemUtil.DeleteItem(item.getID());
        assertEquals(true,flag);


        count=itemUtil.getItemCount();
        assertEquals(0,count);
        count=itemUtil.itemNoticeUtil.getItemNoticeCount();
        assertEquals(0,count);
        count=itemUtil.itemStatusUtil.getItemStatusCount();
        assertEquals(0,count);
    }
}
