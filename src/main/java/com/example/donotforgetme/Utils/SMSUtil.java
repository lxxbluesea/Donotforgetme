package com.example.donotforgetme.Utils;

import android.app.Activity;
import android.app.Application;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.donotforgetme.Entities.SMSInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZJGJK03 on 2015/2/25.
 */
public class SMSUtil {

    /**
     * 所有的短信
     */
    public static final String SMS_URI_ALL = "content://sms/";
    /**
     * 收件箱短信
     */
    public static final String SMS_URI_INBOX = "content://sms/inbox";
    /**
     * 已发送短信
     */
    public static final String SMS_URI_SEND = "content://sms/sent";
    /**
     * 草稿箱短信
     */
    public static final String SMS_URI_DRAFT = "content://sms/draft";
    /**
     * 发送和接收的广播
     */

    public static final int SMSRESULTCODE=101;

    //String SENT_SMS_ACTION = "SENT_SMS_ACTION";
    String SENT_SMS_ACTION = "SMS_SENT_ACTION";
    String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";
    //接收一个Context
    Context context;
    //内容提供器
    ContentResolver contentResolver;
    //短信的Uri地址
    Uri uri;
    //定义静态的SMSUtil对象，返回时使用
    private static SMSUtil smsUtil;

    /**
     * 私有的构适函数
     *
     * @param context
     */
    private SMSUtil(Context context) {
        this.context = context;
        //注册发送到接收广播
        //this.context.registerReceiver(sendSmS, new IntentFilter(SENT_SMS_ACTION));
        //this.context.registerReceiver(receiverSmS, new IntentFilter(DELIVERED_SMS_ACTION));
    }


    /**
     * 获得单例对象
     *
     * @param context
     * @return
     */
    public static SMSUtil getInstance(Context context) {
        if (smsUtil == null) {
            smsUtil = new SMSUtil(context);
        }
        return smsUtil;
    }

    /**
     * 获得全部的短信，包括有以下字段，
     * thread_id（标志位）,
     * address(来电的号码),
     * person（联系中的名字，如果是陌生号码则为空）,
     * date(接收时间),
     * type(类型，标志为发送或接收),
     * body(短信内容)
     *
     * @return
     */
    public List<SMSInfo> getALLSMS() {
        //定义SMSInfo集合
        List<SMSInfo> smsInfos = new ArrayList<SMSInfo>();
        //初始化短信的Uri，默认获取全部的短信
        uri = Uri.parse(SMS_URI_ALL);
        //定义需要返回的短信字段
        String[] items = new String[]{"thread_id", "address", "person", "date", "type", "body"};
        //初始化短信提供器
        contentResolver = context.getContentResolver();
        //定义游标，使用日期倒序显示
        Cursor cursor = contentResolver.query(uri, items, null, null, "date desc");
        getSMS(cursor, smsInfos);

        return smsInfos;
    }

    /**
     * 获得全部的短信，包括有以下字段，
     * thread_id（标志位）,
     * address(来电的号码),
     * person（联系中的名字，如果是陌生号码则为空）,
     * date(接收时间),
     * type(类型，标志为发送或接收),
     * body(短信内容)
     *
     * @return
     */
    public List<SMSInfo> getALLSMSByThreadID() {
        //定义SMSInfo集合
        List<SMSInfo> smsInfos = new ArrayList<SMSInfo>();
        //初始化短信的Uri，默认获取全部的短信
        uri = Uri.parse(SMS_URI_ALL);
        //定义需要返回的短信字段
        String[] items = new String[]{"thread_id", "address", "person", "date", "type", "body"};
        String where = "0==0) Group By(thread_id";
        //初始化短信提供器
        contentResolver = context.getContentResolver();
        //定义游标，使用日期倒序显示
        Cursor cursor = contentResolver.query(uri, items, where, null, "date desc");
        getSMS(cursor, smsInfos);

        return smsInfos;
    }

    /**
     * 通过ThreadID来获取短信，同一个人的短信的ThreadID相同
     * @param threadid
     * @return
     */
    public List<SMSInfo> getSMSByThreadID(int threadid) {
        //定义SMSInfo集合
        List<SMSInfo> smsInfos = new ArrayList<SMSInfo>();
        //初始化短信的Uri，默认获取全部的短信
        uri = Uri.parse(SMS_URI_ALL);
        //定义需要返回的短信字段
        String[] items = new String[]{"thread_id", "address", "person", "date", "type", "body"};
        //初始化短信提供器
        contentResolver = context.getContentResolver();
        //定义游标，使用日期倒序显示
        Cursor cursor = contentResolver.query(uri, items, "thread_id=?", new String[]{threadid + ""}, "date desc");
        getSMS(cursor, smsInfos);
        return smsInfos;
    }

    void getSMS(Cursor cursor, List<SMSInfo> smsInfos) {
        try {
            //开始读取短信信息，并移动到第一条
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    SMSInfo smsInfo = new SMSInfo();//初始化短信实体类，并开始读取数据内容
                    smsInfo.setThread_id(cursor.getInt(cursor.getColumnIndex("thread_id")));
                    smsInfo.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                    smsInfo.setPerson(cursor.getInt(cursor.getColumnIndex("person")));
                    smsInfo.setDate(cursor.getLong(cursor.getColumnIndex("date")));
                    smsInfo.setType(cursor.getInt(cursor.getColumnIndex("type")));
                    smsInfo.setBody(cursor.getString(cursor.getColumnIndex("body")));
                    smsInfos.add(smsInfo);
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();//关闭游标
        }
    }

    /**
     * 发送短信
     *
     * @param phoneNum 电话号码
     * @param content  发送内容
     * @return
     */
    public boolean sendSMS(String phoneNum, String content) throws IllegalArgumentException {

        boolean flag = false;
        //判断是否为空
        if (TextUtils.isEmpty(phoneNum) || TextUtils.isEmpty(content)) {
            throw new IllegalArgumentException("无效的电话号码或内容为空");
            //return flag;
        }
        //获取短信实例
        SmsManager smsManager = SmsManager.getDefault();

        Intent sendIntent = new Intent(SENT_SMS_ACTION);
        PendingIntent sendPI = PendingIntent.getBroadcast(context, 0, sendIntent, 0);
//        Intent deliverIntent = new Intent(DELIVERED_SMS_ACTION);
//        PendingIntent deliverPI = PendingIntent.getBroadcast(context, 0, deliverIntent, 0);
        //分割短信
        List<String> smsContents = smsManager.divideMessage(content);
        //循环发送短信
        for (String sms : smsContents) {
            try {
                smsManager.sendTextMessage(phoneNum, null, sms, sendPI, null);
                //writeSmS(phoneNum, content);
                flag = true;
            } catch (Exception e) {
                flag = false;
                e.printStackTrace();
                break;
            }
        }

        return flag;
    }

//    /**
//     * 往数据库里写入短信内容
//     * @param phoneNum
//     * @param content
//     */
//    void writeSmS(String phoneNum, String content) {
//        ContentValues values = new ContentValues();
//        //时间
//        values.put("date", System.currentTimeMillis());
//        //未读
//        values.put("read", 0);
//        //接收
//        values.put("type", 1);
//        //来电号码
//        values.put("address", phoneNum);
//        //短信内容
//        values.put("body", content);
//        //插入短信到短信库
//        contentResolver.insert(uri,values);
//    }

//    BroadcastReceiver sendSmS = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            switch (getResultCode()) {
//                case Activity.RESULT_OK:
//                    Toast.makeText(ApplicationUtil.getContext(), "短信发送成功", Toast.LENGTH_SHORT).show();
//                    break;
//                default:
//                    Toast.makeText(ApplicationUtil.getContext(), "短信发送失败", Toast.LENGTH_SHORT).show();
//            }
//        }
//    };
//
//    BroadcastReceiver receiverSmS = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            Toast.makeText(ApplicationUtil.getContext(), "对方接收成功", Toast.LENGTH_SHORT).show();
//        }
//    };

}
