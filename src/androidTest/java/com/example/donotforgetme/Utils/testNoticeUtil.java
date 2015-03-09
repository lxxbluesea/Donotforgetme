package com.example.donotforgetme.Utils;

import android.util.Log;

import com.example.donotforgetme.Entities.Notice;

import junit.framework.TestCase;

import java.util.List;

/**
 * Created by ZJGJK03 on 2015/3/8.
 */
public class testNoticeUtil extends TestCase {
    NoticeUtil noticeUtil;
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        noticeUtil=NoticeUtil.getInstance();
    }
    public void testGetAllNotice()
    {
        if(noticeUtil==null)
            fail();
        List<Notice> permissionList=noticeUtil.getAllNotice();
        for(Notice permission:permissionList)
        {
            Log.d("testNoticeUtil", permission.toString());
        }
    }
    public void testGetNoticeByID()
    {
        if(noticeUtil==null)
            fail();
        Notice permission=noticeUtil.getNoticeByID(1);
        if(permission==null)
            fail();
        Log.d("testNoticeUtil",permission.toString());
        assertEquals(1, permission.getID());
        assertEquals("消息",permission.getName());

    }
    public void testGetNoticeByID1()
    {
        if(noticeUtil==null)
            fail();
        Notice permission=noticeUtil.getNoticeByID(0);
        assertEquals(null,permission);
    }
    public void testGetNoticeByName()
    {
        if(noticeUtil==null)
            fail();
        Notice permission=noticeUtil.getNoticeByName("消息");
        if(permission==null)
            fail();
        Log.d("testNoticeUtil",permission.toString());
        assertEquals(1,permission.getID());
        assertEquals("消息",permission.getName());

    }
    public void testGetNoticeByName1()
    {
        if(noticeUtil==null)
            fail();
        Notice permission=noticeUtil.getNoticeByName("");
        assertEquals(null,permission);
    }
}
