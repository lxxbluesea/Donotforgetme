package com.example.donotforgetme.Entities;

/**
 * Created by ZJGJK03 on 2015/2/25.
 */
public class BaseInfo {
    int ID,ShowNum,ShowDay,TestMailResult;

    String Number,BackupDir,mailType,MailName,MailPassword,Smtp,pop3;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getShowNum() {
        return ShowNum;
    }

    public void setShowNum(int showNum) {
        ShowNum = showNum;
    }

    public int getShowDay() {
        return ShowDay;
    }

    public void setShowDay(int showDay) {
        ShowDay = showDay;
    }

    public int getTestMailResult() {
        return TestMailResult;
    }

    public void setTestMailResult(int testMailResult) {
        TestMailResult = testMailResult;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getBackupDir() {
        return BackupDir;
    }

    public void setBackupDir(String backupDir) {
        BackupDir = backupDir;
    }

    public String getMailType() {
        return mailType;
    }

    public void setMailType(String mailType) {
        this.mailType = mailType;
    }

    public String getMailName() {
        return MailName;
    }

    public void setMailName(String mailName) {
        MailName = mailName;
    }

    public String getMailPassword() {
        return MailPassword;
    }

    public void setMailPassword(String mailPassword) {
        MailPassword = mailPassword;
    }

    public String getSmtp() {
        return Smtp;
    }

    public void setSmtp(String smtp) {
        Smtp = smtp;
    }

    public String getPop3() {
        return pop3;
    }

    public void setPop3(String pop3) {
        this.pop3 = pop3;
    }
}
