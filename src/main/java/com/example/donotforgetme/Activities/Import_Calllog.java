package com.example.donotforgetme.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.donotforgetme.Entities.CallLog;


import com.example.donotforgetme.Entities.SMSInfo;
import com.example.donotforgetme.R;
import com.example.donotforgetme.Utils.CallLogUtil;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Import_Calllog extends Activity {

    ProgressDialog progressDialog;
    ListView listView;
    CallLogUtil callLogUtil;
    List<CallLog> callLogList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_calllog);

        listView=(ListView)this.findViewById(R.id.import_lv_calllog);
        callLogUtil=CallLogUtil.getInstance();

        progressDialog=ProgressDialog.show(Import_Calllog.this,"提示", "正在加载中...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                callLogList = callLogUtil.getAllCallLog();
                List<HashMap<String, Object>> datas = new ArrayList<HashMap<String, Object>>();
                if (!callLogList.isEmpty()) {
                    for (CallLog info : callLogList) {
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("name", info.getName());
                        map.put("date", info.getDate());
                        map.put("type", info.getType());
                        map.put("number",info.getNumber());
                        datas.add(map);
                    }
                }
                msg.obj = datas;
                handler.sendMessage(msg);
            }
        }).start();
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            List<HashMap<String, Object>> datas = (List<HashMap<String, Object>>) msg.obj;
            SimpleAdapter simpleAdapter = new SimpleAdapter(Import_Calllog.this, datas, R.layout.import_calllog_listview_item, new String[]{"name", "date", "type","number"}, new int[]{R.id.import_calllog_listview_name, R.id.import_calllog_listview_date, R.id.import_calllog_listview_type,R.id.import_calllog_listview_number});
            listView.setAdapter(simpleAdapter);
            //listView.setOnItemClickListener(clickListener);
            if (progressDialog != null)
                progressDialog.dismiss();
            super.handleMessage(msg);
        }
    };

}
