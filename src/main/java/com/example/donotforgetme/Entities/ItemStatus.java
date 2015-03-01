package com.example.donotforgetme.Entities;

import java.util.Date;

/**
 * Created by ZJGJK03 on 2015/2/25.
 */
public class ItemStatus {
    int ID,ItemID,StatusID;
    long OpDateTime;
    String Note;

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

    public int getStatusID() {
        return StatusID;
    }

    public void setStatusID(int statusID) {
        StatusID = statusID;
    }

    public long getOpDateTime() {
        return OpDateTime;
    }

    public void setOpDateTime(long opDateTime) {
        OpDateTime = opDateTime;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }
}
