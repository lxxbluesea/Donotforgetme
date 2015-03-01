package com.example.donotforgetme.Utils;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;


import com.example.donotforgetme.Entities.CallLog;

/**
 * Created by ZJGJK03 on 2015/2/27.
 */
public class CallLogUtil {

    static CallLogUtil callLogUtil;
    ContentResolver resolver;

    Uri uri = android.provider.CallLog.Calls.CONTENT_URI;
    // 查询的列
    String[] projection = {android.provider.CallLog.Calls.DATE, // 日期
            android.provider.CallLog.Calls.NUMBER, // 号码
            android.provider.CallLog.Calls.TYPE, // 类型
            android.provider.CallLog.Calls.CACHED_NAME, // 名字
            android.provider.CallLog.Calls._ID, // id
    };
    static int DATE = 0, NUMBER = 1, TYPE = 2, CACHED_NAME = 3, _ID = 4;

    private CallLogUtil() {
        resolver = ApplicationUtil.getContext().getContentResolver();
    }

    public static CallLogUtil getInstance() {
        if (callLogUtil == null)
            callLogUtil = new CallLogUtil();
        return callLogUtil;
    }

    public List<CallLog> getAllCallLog() {
        List<CallLog> callLogList = new ArrayList<CallLog>();
        Cursor cursor = resolver.query(uri, projection, null, null, "date desc");
        try {

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    CallLog callLog1 = new CallLog();
                    callLog1.setDate(cursor.getLong(DATE));
                    callLog1.setNumber(cursor.getString(NUMBER));
                    callLog1.setType(cursor.getInt(TYPE));
                    callLog1.setName(cursor.getString(CACHED_NAME));
                    callLog1.setId(cursor.getInt(_ID));

                    callLogList.add(callLog1);

                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        return callLogList;
    }

}
