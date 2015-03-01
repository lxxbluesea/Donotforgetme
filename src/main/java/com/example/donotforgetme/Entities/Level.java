package com.example.donotforgetme.Entities;

/**
 * Created by ZJGJK03 on 2015/3/1.
 */
public class Level {
    int ID;
    String name;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Level{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                '}';
    }
}
