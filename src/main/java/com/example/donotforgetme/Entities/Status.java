package com.example.donotforgetme.Entities;

/**
 * Created by ZJGJK03 on 2015/2/25.
 */
public class Status {
    int ID;
    String Name;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        String text = "";
        text += "编号：" + getID();
        text += "，名称：" + getName();

        return text;
    }
}
