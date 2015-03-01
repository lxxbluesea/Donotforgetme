package com.example.donotforgetme.Entities;

import android.graphics.Bitmap;

/**
 * Created by ZJGJK03 on 2015/2/27.
 */
public class Contact {
    String DisplayName = "", Number = "";
    long ContactID = 0;
    Bitmap Photo=null;

    public Contact() {
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public Bitmap getPhoto() {
        return Photo;
    }

    public void setPhoto(Bitmap photo) {
        Photo = photo;
    }

    public long getContactID() {
        return ContactID;
    }

    public void setContactID(long contactID) {
        ContactID = contactID;
    }

    @Override
    public String toString() {
        //return super.toString();
        String text = "";
        text += "联系人信息：";
        text += "\t编号：" + getContactID();
        text += ",姓名：" + getDisplayName();
        text += ",号码：" + getNumber();
        return text;
    }

}
