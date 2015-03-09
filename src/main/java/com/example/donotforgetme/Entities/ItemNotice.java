package com.example.donotforgetme.Entities;


/**
 * Created by ZJGJK03 on 2015/2/25.
 */
public class ItemNotice {
    int ID, ItemID, NoticeID, Type = ADD;
    long NoticeTime;
    static final int ADD = 1, MODIFY = 2, DELETE = 3;

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int itemID) {
        ItemID = itemID;
    }

    public int getNoticeID() {
        return NoticeID;
    }

    public void setNoticeID(int noticeID) {
        NoticeID = noticeID;
    }

    public long getNoticeTime() {
        return NoticeTime;
    }

    public void setNoticeTime(long noticeTime) {
        NoticeTime = noticeTime;
    }
}
