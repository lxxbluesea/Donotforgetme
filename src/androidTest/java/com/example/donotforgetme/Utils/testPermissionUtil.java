package com.example.donotforgetme.Utils;

import android.util.Log;

import com.example.donotforgetme.Entities.Permission;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.List;

/**
 * Created by ZJGJK03 on 2015/3/1.
 */
public class testPermissionUtil extends TestCase {
    PermissionUtil permissionUtil;
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        permissionUtil=PermissionUtil.getInstance();
    }
    public void testGetAllPermission()
    {
        if(permissionUtil==null)
            fail();
        List<Permission> permissionList=permissionUtil.getAllPermission();
        for(Permission permission:permissionList)
        {
            Log.d("testPermissionUtil",permission.toString());
        }
    }
    public void testGetPermissionByID()
    {
        if(permissionUtil==null)
            fail();
        Permission permission=permissionUtil.getPermissionByID(1);
        if(permission==null)
            fail();
        Log.d("testPermissionUtil",permission.toString());
        assertEquals(1, permission.getID());
        assertEquals("通讯录",permission.getName());
        assertEquals(0,permission.getEnable());
    }
    public void testGetPermissionByID1()
    {
        if(permissionUtil==null)
            fail();
        Permission permission=permissionUtil.getPermissionByID(0);
        assertEquals(null,permission);
    }
    public void testGetPermissionByName()
    {
        if(permissionUtil==null)
            fail();
        Permission permission=permissionUtil.getPermissionByName("通讯录");
        if(permission==null)
            fail();
        Log.d("testPermissionUtil",permission.toString());
        assertEquals(1,permission.getID());
        assertEquals("通讯录",permission.getName());
        assertEquals(0,permission.getEnable());
    }
    public void testGetPermissionByName1()
    {
        if(permissionUtil==null)
            fail();
        Permission permission=permissionUtil.getPermissionByName("");
        assertEquals(null,permission);
    }


}
