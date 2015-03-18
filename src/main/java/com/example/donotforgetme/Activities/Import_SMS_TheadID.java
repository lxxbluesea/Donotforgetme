package com.example.donotforgetme.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.donotforgetme.Entities.SMSInfo;
import com.example.donotforgetme.R;
import com.example.donotforgetme.Utils.SMSUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Import_SMS_TheadID extends Activity {

    ListView listView;
    int threadid=-1;
    SMSUtil smsUtil;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_sms_theadid);
        Intent intent=getIntent();
        if(intent!=null)
        {
            threadid=intent.getIntExtra("threadid",-1);
            name=intent.getStringExtra("name");
            initDates();
        }
    }

    void initDates()
    {
        if(threadid>0 && !TextUtils.isEmpty(name))
        {
            smsUtil=SMSUtil.getInstance(this);
            List<SMSInfo> smsInfoList=smsUtil.getSMSByThreadID(threadid);
            listView=(ListView)this.findViewById(R.id.import_sms_threadid_lv);
            List<HashMap<String, Object>> datas = new ArrayList<HashMap<String, Object>>();
            if (!smsInfoList.isEmpty()) {
                for (SMSInfo info : smsInfoList) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("date", info.getDate());
                    map.put("body", info.getBody());
                    datas.add(map);
                }
                SimpleAdapter simpleAdapter = new SimpleAdapter(this, datas, R.layout.import_sms_threadid_item, new String[]{"body","date"}, new int[]{R.id.import_sms_threadid_body, R.id.import_sms_threadid_datetime});
                listView.setAdapter(simpleAdapter);
                //listView.setOnItemClickListener(clickListener);
            }
        }
    }


}
