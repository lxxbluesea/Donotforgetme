package com.example.donotforgetme.Entities;

import android.text.TextUtils;

import com.example.donotforgetme.R;
import com.example.donotforgetme.Utils.ApplicationUtil;
import com.example.donotforgetme.Utils.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ZJGJK03 on 2015/2/27.
 */
public class CallLog {
    private int id;
    private String name; // 名称
    private String number; // 号码
    private long date; // 日期
    private int type; // 来电:1，拨出:2,未接:3
    private int count; // 通话次数

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        if (TextUtils.isEmpty(name))
            return number;
            //return ApplicationUtil.getContext().getResources().getString(R.string.import_calllog_empty_text);
        else
            return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {

//        Date date1=new Date(this.date);
//        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(ApplicationUtil.getContext().getResources().getString(R.string.datetimeformat));
//        return simpleDateFormat.format(date1);

        return DateUtil.getDateString(this.date);

    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getType() {

        String stype = "";
        switch (this.type) {
            case 1:
                stype = "呼入";
                break;
            case 2:
                stype = "呼出";
                break;
            case 3:
                stype = "未接";
                break;
            case 4:
                stype = "拒接";
                break;
        }
        return stype;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        String text = "";
        text += "通话记录：\t";
        text += "编号：" + getId();

        if (TextUtils.isEmpty(getName()))
            text += ",姓名：" + getNumber();
        else
            text += ",姓名：" + getName();

        text += ",号码：" + getNumber();
        text += ",日期：" + getDate();
        text += ",类型：" + getType();
        return text;
    }
}
