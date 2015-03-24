package com.example.donotforgetme.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.donotforgetme.Entities.SMSInfo;
import com.example.donotforgetme.R;
import com.example.donotforgetme.Utils.SMSUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Import_SMS extends Activity {

    ProgressDialog progressDialog;
    ListView listView;
    SMSUtil smsUtil;
    List<SMSInfo> smsInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_sms);
        smsUtil = SMSUtil.getInstance(this);
        listView = (ListView) findViewById(R.id.import_lv_sms);
        progressDialog = ProgressDialog.show(Import_SMS.this, "提示", "正在加载中...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                smsInfoList = smsUtil.getALLSMSByThreadID();
                List<HashMap<String, Object>> datas = new ArrayList<HashMap<String, Object>>();
                if (!smsInfoList.isEmpty()) {
                    for (SMSInfo info : smsInfoList) {
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("person", info.getPerson());
                        map.put("date", info.getDate());
                        map.put("body", info.getBody());
                        datas.add(map);
                    }
                }
                msg.obj = datas;
                handler.sendMessage(msg);
            }
        }).start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            List<HashMap<String, Object>> datas = (List<HashMap<String, Object>>) msg.obj;
            SimpleAdapter simpleAdapter = new SimpleAdapter(Import_SMS.this, datas, R.layout.import_sms_listview_item, new String[]{"person", "date", "body"}, new int[]{R.id.import_sms_listview_item_name, R.id.import_sms_listview_item_datetime, R.id.import_sms_listview_item_body});
            listView.setAdapter(simpleAdapter);
            listView.setOnItemClickListener(clickListener);
            if (progressDialog != null)
                progressDialog.dismiss();
            super.handleMessage(msg);
        }
    };

    void initControls() {

        smsUtil = SMSUtil.getInstance(this);
        listView = (ListView) findViewById(R.id.import_lv_sms);
        smsInfoList = smsUtil.getALLSMSByThreadID();
        List<HashMap<String, Object>> datas = new ArrayList<HashMap<String, Object>>();
        if (!smsInfoList.isEmpty()) {
            for (SMSInfo info : smsInfoList) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("person", info.getPerson());
                map.put("date", info.getDate());
                map.put("body", info.getBody());
                datas.add(map);
            }
            SimpleAdapter simpleAdapter = new SimpleAdapter(Import_SMS.this, datas, R.layout.import_sms_listview_item, new String[]{"person", "date", "body"}, new int[]{R.id.import_sms_listview_item_name, R.id.import_sms_listview_item_datetime, R.id.import_sms_listview_item_body});
            listView.setAdapter(simpleAdapter);
            listView.setOnItemClickListener(clickListener);
        }
    }

    AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(Import_SMS.this, Import_SMS_TheadID.class);
            SMSInfo info = smsInfoList.get(position);
            intent.putExtra("threadid", info.getThread_id());
            intent.putExtra("name", info.getPerson());
            startActivity(intent);
        }
    };
}
