package com.example.donotforgetme.Utils;

import android.util.Log;

import com.example.donotforgetme.Entities.Status;

import junit.framework.TestCase;

import java.util.List;

/**
 * Created by ZJGJK03 on 2015/2/28.
 */
public class testStatusUtil extends TestCase {
    StatusUtil statusUtil;
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        statusUtil=StatusUtil.getInstance();
    }
    public void testGetAllStatus()
    {
        if(statusUtil==null)
            fail();
        List<Status> statusList=statusUtil.getAllStatus();
        for (Status status:statusList)
        {
            Log.d("testStatusUtil",status.toString());
        }
    }
    public void testGetStatusByID()
    {
        if(statusUtil==null)
            fail();
        Status status=statusUtil.getStatusByID(1);
        if(status==null)
            fail();
        Log.d("testStatusUtil",status.toString());
        assertEquals(1,status.getID());
        assertEquals("执行中",status.getName());
    }
    public void testGetStatusByID1()
    {
        if(statusUtil==null)
            fail();
        Status status=statusUtil.getStatusByID(6);
        if(status!=null)
            fail();

    }
    public void testGetStatusByName()
    {
        if(statusUtil==null)
            fail();
        Status status=statusUtil.getStatusByName("执行中");
        if(status==null)
            fail();
        assertEquals(1,status.getID());
        assertEquals("执行中",status.getName());
        Log.d("testStatusUtil",status.toString());
    }
    public void testGetStatusByName1()
    {
        if(statusUtil==null)
            fail();
        Status status=statusUtil.getStatusByName("执行中111");
        if(status!=null)
            fail();
    }
}
