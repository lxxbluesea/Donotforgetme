package com.example.donotforgetme.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.donotforgetme.Entities.SMSInfo;
import com.example.donotforgetme.R;
import com.example.donotforgetme.Utils.SMSUtil;
import com.example.donotforgetme.Utils.SleepUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Import_SMS extends Activity {

    ProgressDialog progressDialog;
    ListView listView;
    SMSUtil smsUtil;
    List<SMSInfo> smsInfoList;
    TextView tv_title;
    final int SMSREQUESTCODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_sms);

        progressDialog = ProgressDialog.show(Import_SMS.this, getResources().getString(R.string.import_normal_warn), getResources().getString(R.string.import_normal_warntext));

        tv_title=(TextView)findViewById(R.id.title_tv_title);
        tv_title.setText(getResources().getString(R.string.import_sms_text));
        smsUtil = SMSUtil.getInstance(this);
        listView = (ListView) findViewById(R.id.import_lv_sms);


        new Thread(new Runnable() {
            @Override
            public void run() {

                SleepUtil.Sleep();

                Message msg = new Message();
                smsInfoList = smsUtil.getALLSMSByThreadID();
                msg.obj = smsInfoList;
                handler.sendMessage(msg);
            }
        }).start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            listView.setAdapter(new MyListAdapter((List<SMSInfo>) msg.obj,getLayoutInflater()));
            listView.setOnItemClickListener(clickListener);
            if (progressDialog != null)
                progressDialog.dismiss();
            super.handleMessage(msg);
        }
    };

//    void initControls() {
//
//        smsUtil = SMSUtil.getInstance(this);
//        listView = (ListView) findViewById(R.id.import_lv_sms);
//        smsInfoList = smsUtil.getALLSMSByThreadID();
//        List<HashMap<String, Object>> datas = new ArrayList<HashMap<String, Object>>();
//        if (!smsInfoList.isEmpty()) {
//            for (SMSInfo info : smsInfoList) {
//                HashMap<String, Object> map = new HashMap<String, Object>();
//                map.put("person", info.getPerson());
//                map.put("date", info.getDate());
//                map.put("body", info.getBody());
//                datas.add(map);
//            }
//            SimpleAdapter simpleAdapter = new SimpleAdapter(Import_SMS.this, datas, R.layout.import_sms_listview_item, new String[]{"person", "date", "body"}, new int[]{R.id.import_sms_listview_item_name, R.id.import_sms_listview_item_datetime, R.id.import_sms_listview_item_body});
//            listView.setAdapter(simpleAdapter);
//            listView.setOnItemClickListener(clickListener);
//        }
//    }

    AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(Import_SMS.this, Import_SMS_TheadID.class);
            SMSInfo info = smsInfoList.get(position);
            intent.putExtra("threadid", info.getThread_id());
            intent.putExtra("name", info.getPerson());
            //startActivity(intent);
            startActivityForResult(intent, SMSREQUESTCODE);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == SMSREQUESTCODE) {
            if (resultCode == SMSUtil.SMSRESULTCODE) {
                Intent intent = new Intent();
                intent.putExtra("import", data.getExtras().getString("import"));
                setResult(SMSUtil.SMSRESULTCODE, intent);
                finish();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    class MyListAdapter extends BaseAdapter
    {
        List<SMSInfo> smsInfoList;
        LayoutInflater inflater;
        MyListAdapter(List<SMSInfo> smsInfos,LayoutInflater inflater) {
            this.smsInfoList=smsInfos;
            this.inflater=inflater;
        }

        @Override
        public int getCount() {
            return smsInfoList.size();
        }

        @Override
        public Object getItem(int position) {
            return smsInfoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return smsInfoList.get(position).getThread_id();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            SMSInfo info = smsInfoList.get(position);
            //View view1=null,view2=null;
            ViewHoder viewHoder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.import_sms_listview_item, null);
                viewHoder = new ViewHoder();
                viewHoder.tv_name = (TextView) convertView.findViewById(R.id.import_sms_listview_item_name);
                viewHoder.tv_body = (TextView) convertView.findViewById(R.id.import_sms_listview_item_body);
                viewHoder.tv_date = (TextView) convertView.findViewById(R.id.import_sms_listview_item_datetime);
                convertView.setTag(viewHoder);
            } else {
                viewHoder = (ViewHoder) convertView.getTag();
            }
            //convertView=view1;

            viewHoder.tv_body.setText(info.getBody());
            viewHoder.tv_date.setText(info.getDate());
            viewHoder.tv_name.setText(info.getPerson());

            return convertView;
        }

        class ViewHoder
        {
            TextView tv_date,tv_body,tv_name;
        }
    }
}
