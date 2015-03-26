package com.example.donotforgetme.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.donotforgetme.Entities.CallLog;


import com.example.donotforgetme.Entities.Contact;
import com.example.donotforgetme.Entities.SMSInfo;
import com.example.donotforgetme.R;
import com.example.donotforgetme.Utils.CallLogUtil;
import com.example.donotforgetme.Utils.SMSUtil;
import com.example.donotforgetme.Utils.SleepUtil;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Import_Calllog extends Activity {

    ProgressDialog progressDialog;
    ListView listView;
    CallLogUtil callLogUtil;
    List<CallLog> callLogList;
    TextView tv_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_calllog);

        progressDialog=ProgressDialog.show(Import_Calllog.this,getResources().getString(R.string.import_normal_warn), getResources().getString(R.string.import_normal_warntext));

        tv_title=(TextView)findViewById(R.id.title_tv_title);
        tv_title.setText(getResources().getString(R.string.import_calllog_text));

        listView=(ListView)this.findViewById(R.id.import_lv_calllog);
        callLogUtil=CallLogUtil.getInstance();


        new Thread(new Runnable() {
            @Override
            public void run() {
                SleepUtil.Sleep();
                Message msg = new Message();
                callLogList = callLogUtil.getAllCallLog();
//                List<HashMap<String, Object>> datas = new ArrayList<HashMap<String, Object>>();
//                if (!callLogList.isEmpty()) {
//                    for (CallLog info : callLogList) {
//                        HashMap<String, Object> map = new HashMap<String, Object>();
//                        map.put("name", info.getName());
//                        map.put("date", info.getDate());
//                        map.put("type", info.getType());
//                        map.put("number",info.getNumber());
//                        datas.add(map);
//                    }
//                }
                msg.obj = callLogList;
                handler.sendMessage(msg);
            }
        }).start();
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
//            List<HashMap<String, Object>> datas = (List<HashMap<String, Object>>) msg.obj;
//            SimpleAdapter simpleAdapter = new SimpleAdapter(Import_Calllog.this, datas, R.layout.import_calllog_listview_item, new String[]{"name", "date", "type","number"}, new int[]{R.id.import_calllog_listview_name, R.id.import_calllog_listview_date, R.id.import_calllog_listview_type,R.id.import_calllog_listview_number});
//            listView.setAdapter(simpleAdapter);
            listView.setAdapter(new MyListAdapter((List<CallLog>) msg.obj,getLayoutInflater()));
            listView.setOnItemClickListener(clickListener);
            if (progressDialog != null)
                progressDialog.dismiss();
            super.handleMessage(msg);
        }
    };

    AdapterView.OnItemClickListener clickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            CallLog callLog = callLogList.get(position);
            final String result = "通话记录[姓名：" + callLog.getName() + "，手机号码：" + callLog.getNumber()+",日期："+callLog.getDate()+"]";
            AlertDialog.Builder dialog=new AlertDialog.Builder(Import_Calllog.this);
            dialog.setIcon(R.drawable.ic_launcher);
            dialog.setTitle(getResources().getString(R.string.import_normal_warn));
            dialog.setMessage("是否导入?" + result);
            dialog.setPositiveButton(getResources().getString(R.string.normal_confirm), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent();
                    intent.putExtra("import", result);
                    setResult(CallLogUtil.CALLLOGRESULTCODE, intent);
                    finish();
                }
            });
            dialog.setNegativeButton(getResources().getString(R.string.normal_cancel),new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
    };

    class MyListAdapter extends BaseAdapter
    {
        List<CallLog> callLogList;
        LayoutInflater inflater;
        MyListAdapter(List<CallLog> callLogs,LayoutInflater inflater) {
            this.callLogList=callLogs;
            this.inflater=inflater;
        }

        @Override
        public int getCount() {
            return callLogList.size();
        }

        @Override
        public Object getItem(int position) {
            return callLogList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return callLogList.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            CallLog callLog = callLogList.get(position);
            //View view1=null,view2=null;
            ViewHoder viewHoder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.import_calllog_listview_item, null);
                viewHoder = new ViewHoder();
                viewHoder.tv_date = (TextView) convertView.findViewById(R.id.import_calllog_listview_date);
                viewHoder.tv_type = (TextView) convertView.findViewById(R.id.import_calllog_listview_type);
                viewHoder.tv_name = (TextView) convertView.findViewById(R.id.import_calllog_listview_name);
                viewHoder.tv_number = (TextView) convertView.findViewById(R.id.import_calllog_listview_number);

                convertView.setTag(viewHoder);
            } else {
                viewHoder = (ViewHoder) convertView.getTag();
            }
            //convertView=view1;

            viewHoder.tv_date.setText(callLog.getDate());
            viewHoder.tv_type.setText(callLog.getType());
            viewHoder.tv_name.setText(callLog.getName());
            viewHoder.tv_number.setText(callLog.getNumber());
            return convertView;
        }

        class ViewHoder
        {
            TextView tv_date,tv_type,tv_name,tv_number;
        }
    }

}
