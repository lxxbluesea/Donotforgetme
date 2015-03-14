package com.example.donotforgetme.Entities;

/**
 * Created by ZJGJK03 on 2015/2/25.
 */
public class Item {
    int ID,NoticeTime=0,Level;

    String Content;

    long CreateDateTime,BeginDateTime,EndDateTime;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getNoticeTime() {
        return NoticeTime;
    }

    public void setNoticeTime(int noticeTime) {
        if(noticeTime<=0)
            NoticeTime=0;
        NoticeTime = noticeTime;
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

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }
}
