package com.example.donotforgetme.Utils;

import android.util.Log;

import com.example.donotforgetme.Entities.CallLog;

import junit.framework.TestCase;

import java.util.List;

/**
 * Created by ZJGJK03 on 2015/2/27.
 */
public class testCallLogUtil extends TestCase {
    CallLogUtil callLogUtil;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        callLogUtil=CallLogUtil.getInstance();
    }

    public void testGetAllCallLog()
    {
        if(callLogUtil==null)
            fail();
        List<CallLog> callLog1List=callLogUtil.getAllCallLog();
        for (CallLog callLog1:callLog1List)
        {
            Log.d("testGetAllCallLog",callLog1.toString());
        }
    }
}
