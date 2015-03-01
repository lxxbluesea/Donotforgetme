package com.example.donotforgetme.Entities;

/**
 * Created by ZJGJK03 on 2015/2/25.
 */
public class Notice {

    int ID,Enable,ZhenDongNum;
    String Name,RingPath;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getEnable() {
        return Enable;
    }

    public void setEnable(int enable) {
        Enable = enable;
    }

    public int getZhenDongNum() {
        return ZhenDongNum;
    }

    public void setZhenDongNum(int zhenDongNum) {
        ZhenDongNum = zhenDongNum;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRingPath() {
        return RingPath;
    }

    public void setRingPath(String ringPath) {
        RingPath = ringPath;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "ID=" + ID +
                ", Enable=" + Enable +
                ", ZhenDongNum=" + ZhenDongNum +
                ", Name='" + Name + '\'' +
                ", RingPath='" + RingPath + '\'' +
                '}';
    }
}
