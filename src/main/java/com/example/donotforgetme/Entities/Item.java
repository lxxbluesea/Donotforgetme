package com.example.donotforgetme.Entities;

import java.util.Date;

/**
 * Created by ZJGJK03 on 2015/2/25.
 */
public class Item {
    int ID,ItemStatusID;

    String Content;

    long CreateDateTime,BeginDateTime,EndDateTime;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getItemStatusID() {
        return ItemStatusID;
    }

    public void setItemStatusID(int itemStatusID) {
        ItemStatusID = itemStatusID;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public long getCreateDateTime() {
        return CreateDateTime;
    }

    public void setCreateDateTime(long createDateTime) {
        CreateDateTime = createDateTime;
    }

    public long getBeginDateTime() {
        return BeginDateTime;
    }

    public void setBeginDateTime(long beginDateTime) {
        BeginDateTime = beginDateTime;
    }

    public long getEndDateTime() {
        return EndDateTime;
    }

    public void setEndDateTime(long endDateTime) {
        EndDateTime = endDateTime;
    }
}
