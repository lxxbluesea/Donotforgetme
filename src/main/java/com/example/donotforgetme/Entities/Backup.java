package com.example.donotforgetme.Entities;

/**
 * Created by ZJGJK03 on 2015/2/25.
 */
public class Backup {

    int ID,BackupTypeID;

    String List,BackupDir;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getBackupTypeID() {
        return BackupTypeID;
    }

    public void setBackupTypeID(int backupTypeID) {
        BackupTypeID = backupTypeID;
    }

    public String getList() {
        return List;
    }

    public void setList(String list) {
        List = list;
    }

    public String getBackupDir() {
        return BackupDir;
    }

    public void setBackupDir(String backupDir) {
        BackupDir = backupDir;
    }
}
