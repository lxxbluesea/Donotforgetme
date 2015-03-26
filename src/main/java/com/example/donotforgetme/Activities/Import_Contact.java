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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.donotforgetme.Entities.CallLog;
import com.example.donotforgetme.Entities.Contact;
import com.example.donotforgetme.Entities.SMSInfo;
import com.example.donotforgetme.R;
import com.example.donotforgetme.Utils.CallLogUtil;
import com.example.donotforgetme.Utils.ContactUtil;
import com.example.donotforgetme.Utils.SMSUtil;
import com.example.donotforgetme.Utils.SleepUtil;

import java.text.Format;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Import_Contact extends Activity {
    ProgressDialog progressDialog;
    ListView listView;
    ContactUtil contactUtil;
    List<Contact> contactList;
    TextView tv_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_contact);
        progressDialog=ProgressDialog.show(Import_Contact.this,getResources().getString(R.string.import_normal_warn), getResources().getString(R.string.import_normal_warntext));

        tv_title=(TextView)findViewById(R.id.title_tv_title);
        tv_title.setText(getResources().getString(R.string.import_contact_text));

        listView=(ListView)this.findViewById(R.id.import_lv_contact);
        contactUtil=ContactUtil.getInstance();



        new Thread(new Runnable() {
            @Override
            public void run() {
                SleepUtil.Sleep();
                Message msg = new Message();
                contactList = contactUtil.getAllContact_FromPhone(); //先读取手机里的通讯录
                contactUtil.getAllContact_FromSIM(contactList);//再读取SIM卡里的通讯录
                msg.obj = contactList;
                handler.sendMessage(msg);
            }
        }).start();
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
//            List<HashMap<String, Object>> datas = (List<HashMap<String, Object>>) msg.obj;
//            SimpleAdapter simpleAdapter = new SimpleAdapter(Import_Contact.this, datas, R.layout.import_contact_listview_item, new String[]{"photo","name", "number"}, new int[]{R.id.import_contact_head, R.id.import_contact_tv_name,R.id.import_contact_tv_number});
//            listView.setAdapter(simpleAdapter);
            listView.setAdapter(new MyListAdapter((List<Contact>)msg.obj,getLayoutInflater()));
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
            final String result = "联系人[姓名：" + contact.getDisplayName() + "，手机号码：" + contact.getNumber()+"]";
            AlertDialog.Builder dialog=new AlertDialog.Builder(Import_Contact.this);
            dialog.setIcon(R.drawable.ic_launcher);
            dialog.setTitle(getResources().getString(R.string.import_normal_warn));
            dialog.setMessage("是否导入?" + result);
            dialog.setPositiveButton(getResources().getString(R.string.normal_confirm), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent();
                    intent.putExtra("import", result);
                    setResult(ContactUtil.CONTACTRESULTCODE, intent);
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
        List<Contact> contactList;
        LayoutInflater inflater;
        MyListAdapter(List<Contact> contacts,LayoutInflater inflater) {
            this.contactList=contacts;
            this.inflater=inflater;
        }

        @Override
        public int getCount() {
            return contactList.size();
        }

        @Override
        public Object getItem(int position) {
            return contactList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return contactList.get(position).getContactID();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Contact contact = contactList.get(position);
            //View view1=null,view2=null;
            ViewHoder viewHoder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.import_contact_listview_item, null);
                viewHoder = new ViewHoder();
                viewHoder.tv_name = (TextView) convertView.findViewById(R.id.import_contact_tv_name);
                viewHoder.tv_number = (TextView) convertView.findViewById(R.id.import_contact_tv_number);
                viewHoder.iv_head = (ImageView) convertView.findViewById(R.id.import_contact_head);
                convertView.setTag(viewHoder);
            } else {
                viewHoder = (ViewHoder) convertView.getTag();
            }
            //convertView=view1;

            viewHoder.tv_name.setText(contact.getDisplayName());
            viewHoder.tv_number.setText(contact.getNumber());
            viewHoder.iv_head.setImageBitmap(contact.getPhoto());

            return convertView;
        }

        class ViewHoder
        {
            TextView tv_number,tv_name;
            ImageView iv_head;
        }
    }

}
