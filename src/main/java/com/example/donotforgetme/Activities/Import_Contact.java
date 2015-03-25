package com.example.donotforgetme.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.donotforgetme.Entities.CallLog;
import com.example.donotforgetme.Entities.Contact;
import com.example.donotforgetme.R;
import com.example.donotforgetme.Utils.CallLogUtil;
import com.example.donotforgetme.Utils.ContactUtil;
import com.example.donotforgetme.Utils.SMSUtil;

import java.text.Format;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Import_Contact extends Activity {
    ProgressDialog progressDialog;
    ListView listView;
    ContactUtil contactUtil;
    List<Contact> contactList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_contact);
        listView=(ListView)this.findViewById(R.id.import_lv_contact);
        contactUtil=ContactUtil.getInstance();

        progressDialog=ProgressDialog.show(Import_Contact.this,getResources().getString(R.string.import_normal_warn), getResources().getString(R.string.import_normal_warntext));
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                contactList = contactUtil.getAllContact_FromPhone();
                //先读取手机里的通讯录
                List<HashMap<String, Object>> datas = new ArrayList<HashMap<String, Object>>();
                if (!contactList.isEmpty()) {
                    for (Contact info : contactList) {
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("name", info.getDisplayName());
                        map.put("number",info.getNumber());
                        map.put("photo",info.getPhoto());
                        datas.add(map);
                    }
                }
                //再读取SIM卡里的通讯录
                contactUtil.getAllContact_FromSIM(contactList);
                if (!contactList.isEmpty()) {
                    for (Contact info : contactList) {
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("name", info.getDisplayName());
                        map.put("number",info.getNumber());
                        map.put("photo",info.getPhoto());
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
            SimpleAdapter simpleAdapter = new SimpleAdapter(Import_Contact.this, datas, R.layout.import_contact_listview_item, new String[]{"photo","name", "number"}, new int[]{R.id.import_contact_head, R.id.import_contact_tv_name,R.id.import_contact_tv_number});
            listView.setAdapter(simpleAdapter);
            listView.setOnItemClickListener(clickListener);
            if (progressDialog != null)
                progressDialog.dismiss();
            super.handleMessage(msg);
        }
    };

    AdapterView.OnItemClickListener clickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Contact contact = contactList.get(position);
            final String result = "姓名：" + contact.getDisplayName() + "，手机号码：" + contact.getNumber();
            AlertDialog.Builder dialog=new AlertDialog.Builder(Import_Contact.this);
            dialog.setIcon(R.drawable.ic_launcher);
            dialog.setTitle(getResources().getString(R.string.import_normal_warn));
            dialog.setMessage(result);
            dialog.setPositiveButton(getResources().getString(R.string.normal_confirm),new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent();
                    intent.putExtra("import", result);
                    setResult(SMSUtil.SMSRESULTCODE, intent);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_import_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
