package com.example.donotforgetme.Utils;

import android.util.Log;

import com.example.donotforgetme.Entities.SMSInfo;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.IllegalFormatException;
import java.util.List;

/**
 * Created by ZJGJK03 on 2015/2/26.
 */
public class testSMSUtil extends TestCase{

    SMSUtil smsUtil;
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        smsUtil=SMSUtil.getInstance(ApplicationUtil.getContext());
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }


    public void testGetALLSMS() {
        if(smsUtil==null)
            fail();
        List<SMSInfo> smsInfoList = smsUtil.getALLSMS();

        for (SMSInfo info : smsInfoList) {
            Log.d("SmS:", info.toString());
        }
    }

    public void testSendSms_for_emptyNum() {
        if(smsUtil==null)
            fail();
        IllegalArgumentException ex=null;
        String num = "", content = "111";
        boolean flag = false;
        try {
            flag = smsUtil.sendSMS(num, content);
            fail();
        }catch (IllegalArgumentException e)
        {
            ex=e;
        }
        assertEquals(false,flag);
        assertEquals(IllegalArgumentException.class,ex.getClass());
        assertEquals(ex.getMessage(),"无效的电话号码或内容为空");
    }

    public void testSendSms_for_emptyContent() {
        if(smsUtil==null)
            fail();
        IllegalArgumentException ex = null;
        String num = "15306205750", content = "";
        boolean flag = false;
        try {
            flag = smsUtil.sendSMS(num, content);
            fail();
        } catch (IllegalArgumentException e) {
            ex = e;
        }
        assertEquals(false, flag);
        assertEquals(IllegalArgumentException.class, ex.getClass());
        assertEquals(ex.getMessage(), "无效的电话号码或内容为空");
    }

//    public void testSendSms() {
//        if(smsUtil==null)
//            fail();
//        //IllegalArgumentException ex = null;
//        String num = "5554", content = "这是一条由测试用例发出的测试短信";
//        boolean flag = false;
//        try {
//            flag = smsUtil.sendSMS(num, content);
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//            fail();
//        }
//        assertEquals(true, flag);
//
//    }
}
