package com.example.donotforgetme.Entities;

import com.example.donotforgetme.R;
import com.example.donotforgetme.Utils.ApplicationUtil;
import com.example.donotforgetme.Utils.ContactUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by ZJGJK03 on 2015/2/26.
 */
public class SMSInfo {

    String body,address;
    long date;
    int thread_id,type,person;

    public String getBody() {
        return body;
    }

    public void setBody(String smsBody) {
        this.body = smsBody;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        Date date1=new Date(this.date);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(ApplicationUtil.getContext().getResources().getString(R.string.datetimeformat));
        return simpleDateFormat.format(date1);
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getType() {
        return type == 1 ? "接收" : "发送";
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getThread_id() {
        return thread_id;
    }

    public void setThread_id(int thread_id) {
        this.thread_id = thread_id;
    }

    public String getPerson() {
        ContactUtil contactUtil = ContactUtil.getInstance();
        List<Contact> contacts = contactUtil.getContactByNumber(this.address);
        if ((contacts != null) && (!contacts.isEmpty()))
            return contacts.get(0).getDisplayName();
        else
            return this.address;
    }

    public void setPerson(int person) {
        this.person = person;
    }

    @Override
    public String toString() {
        String toString = "";
        toString += "短信内容：";
        toString += "\t标志:" + getThread_id();
        toString += ",号码:" + getAddress();
        toString += ",姓名:" + getPerson();
        toString += ",日期:" +getDate() ;
        toString += ",类型:" + getType();
        toString += ",内容:" + getBody();
        toString+="\n";
        return toString;
    }

}
