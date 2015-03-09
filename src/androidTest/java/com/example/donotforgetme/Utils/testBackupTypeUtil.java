package com.example.donotforgetme.Utils;

import android.util.Log;

import com.example.donotforgetme.Entities.BackupType;

import junit.framework.TestCase;

import java.util.List;

/**
 * Created by ZJGJK03 on 2015/3/8.
 */
public class testBackupTypeUtil extends TestCase {

    BackupTypeUtil backupTypeUtil;
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        backupTypeUtil=BackupTypeUtil.getInstance();
    }
    public void testGetAllBackupType()
    {
        if(backupTypeUtil==null)
            fail();
        List<BackupType> permissionList=backupTypeUtil.getAllBackupType();
        for(BackupType permission:permissionList)
        {
            Log.d("testBackupTypeUtil", permission.toString());
        }
    }
    public void testGetBackupTypeByID()
    {
        if(backupTypeUtil==null)
            fail();
        BackupType permission=backupTypeUtil.getBackupTypeByID(1);
        if(permission==null)
            fail();
        Log.d("testBackupTypeUtil",permission.toString());
        assertEquals(1, permission.getID());
        assertEquals("本地备份",permission.getName());

    }
    public void testGetBackupTypeByID1()
    {
        if(backupTypeUtil==null)
            fail();
        BackupType permission=backupTypeUtil.getBackupTypeByID(0);
        assertEquals(null,permission);
    }
    public void testGetBackupTypeByName()
    {
        if(backupTypeUtil==null)
            fail();
        BackupType permission=backupTypeUtil.getBackupTypeByName("本地备份");
        if(permission==null)
            fail();
        Log.d("testBackupTypeUtil",permission.toString());
        assertEquals(1,permission.getID());
        assertEquals("本地备份",permission.getName());

    }
    public void testGetBackupTypeByName1()
    {
        if(backupTypeUtil==null)
            fail();
        BackupType permission=backupTypeUtil.getBackupTypeByName("");
        assertEquals(null,permission);
    }

}
