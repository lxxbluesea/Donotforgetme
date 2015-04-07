package com.example.donotforgetme.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

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
    TextView tv_title;
    List<SMSInfo> smsInfoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_sms_theadid);

        tv_title=(TextView)this.findViewById(R.id.title_tv_title);

        Intent intent=getIntent();
        if(intent!=null)
        {
            threadid=intent.getIntExtra("threadid",-1);
            name=intent.getStringExtra("name");
            tv_title.setText(name);
            initDates();
        }
    }

    void initDates()
    {
        if(threadid>0 && !TextUtils.isEmpty(name))
        {
            smsUtil=SMSUtil.getInstance(this);
            smsInfoList=smsUtil.getSMSByThreadID(threadid);
            listView=(ListView)this.findViewById(R.id.import_sms_threadid_lv);
//            List<HashMap<String, Object>> datas = new ArrayList<HashMap<String, Object>>();
//            if (!smsInfoList.isEmpty()) {
//                for (SMSInfo info : smsInfoList) {
//                    HashMap<String, Object> map = new HashMap<String, Object>();
//                    map.put("date", info.getDate());
//                    map.put("body", info.getBody());
//                    datas.add(map);
//                }
//                SimpleAdapter simpleAdapter = new SimpleAdapter(this, datas, R.layout.import_sms_threadid_item, new String[]{"body","date"}, new int[]{R.id.import_sms_threadid_body, R.id.import_sms_threadid_datetime});
//                listView.setAdapter(new MyListAdapter());
//                listView.setOnItemClickListener(clickListener);
//            }

            listView.setAdapter(new MyListAdapter(smsInfoList,getLayoutInflater()));
            listView.setOnItemClickListener(clickListener);
        }
    }

    AdapterView.OnItemClickListener clickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SMSInfo smsInfo = smsInfoList.get(position);
            final String result = "来自：短信，\n姓名：" + smsInfo.getPerson() + "，\n手机号码：" + smsInfo.getAddress()+",\n日期："+smsInfo.getDate()+",\n内容："+smsInfo.getBody()+"\n";
            AlertDialog.Builder dialog=new AlertDialog.Builder(Import_SMS_TheadID.this);
            dialog.setIcon(R.drawable.ic_launcher);
            dialog.setTitle(R.string.import_normal_warn);
            dialog.setMessage("是否导入?\n"+result);
            dialog.setPositiveButton(R.string.normal_confirm,new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent();
                    intent.putExtra("import", result);
                    setResult(SMSUtil.SMSRESULTCODE, intent);
                    finish();
                }
            });
            dialog.setNegativeButton(R.string.normal_cancel,new DialogInterface.OnClickListener() {
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

            SMSInfo info=smsInfoList.get(position);
            //View view1=null,view2=null;
            ViewHoder viewHoder;
            if(info.getType()=="接收") {
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.import_sms_threadid_item,null);
                    viewHoder=new ViewHoder();
                    viewHoder.tv_body=(TextView)convertView.findViewById(R.id.import_sms_threadid_body);
                    viewHoder.tv_date=(TextView)convertView.findViewById(R.id.import_sms_threadid_datetime);
                    convertView.setTag(viewHoder);
                }
                else
                {
                    viewHoder=(ViewHoder)convertView.getTag();
                }
                //convertView=view1;
            }
            else
            {
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.import_sms_threadid_item_to,null);
                    viewHoder=new ViewHoder();
                    viewHoder.tv_body=(TextView)convertView.findViewById(R.id.import_sms_threadid_body);
                    viewHoder.tv_date=(TextView)convertView.findViewById(R.id.import_sms_threadid_datetime);
                    convertView.setTag(viewHoder);
                }
                else
                {
                    viewHoder=(ViewHoder)convertView.getTag();
                }
                //convertView=view2;
            }

            viewHoder.tv_body.setText(info.getBody());
            viewHoder.tv_date.setText(info.getDate());

            return convertView;
        }

        class ViewHoder
        {
            TextView tv_date,tv_body;
        }
    }
}
