package com.example.donotforgetme.Utils;

import android.util.Log;

import com.example.donotforgetme.Entities.Level;

import junit.framework.TestCase;

import java.util.List;

/**
 * Created by ZJGJK03 on 2015/3/8.
 */
public class testLevelUtil extends TestCase {
    LevelUtil levelUtil;
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        levelUtil=LevelUtil.getInstance();
    }
    public void testGetAllLevel()
    {
        if(levelUtil==null)
            fail();
        List<Level> permissionList=levelUtil.getAllLevel();
        for(Level permission:permissionList)
        {
            Log.d("testLevelUtil", permission.toString());
        }
    }
    public void testGetLevelByID()
    {
        if(levelUtil==null)
            fail();
        Level permission=levelUtil.getLevelByID(1);
        if(permission==null)
            fail();
        Log.d("testLevelUtil",permission.toString());
        assertEquals(1, permission.getID());
        assertEquals("低",permission.getName());

    }
    public void testGetLevelByID1()
    {
        if(levelUtil==null)
            fail();
        Level permission=levelUtil.getLevelByID(0);
        assertEquals(null,permission);
    }
    public void testGetLevelByName()
    {
        if(levelUtil==null)
            fail();
        Level permission=levelUtil.getLevelByName("低");
        if(permission==null)
            fail();
        Log.d("testLevelUtil",permission.toString());
        assertEquals(1,permission.getID());
        assertEquals("低",permission.getName());

    }
    public void testGetLevelByName1()
    {
        if(levelUtil==null)
            fail();
        Level permission=levelUtil.getLevelByName("");
        assertEquals(null,permission);
    }

}
