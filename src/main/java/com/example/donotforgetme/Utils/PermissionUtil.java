package com.example.donotforgetme.Utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.donotforgetme.DB.MyDbHelper;
import com.example.donotforgetme.Entities.Permission;
import com.example.donotforgetme.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZJGJK03 on 2015/3/1.
 */
public class PermissionUtil {
    private static PermissionUtil ourInstance = new PermissionUtil();

    public static PermissionUtil getInstance() {
        return ourInstance;
    }

    String TableName;
    SQLiteDatabase DB;
    String[] columns = {"id", "name","enable"};
    String sortBy = "id asc";

    private PermissionUtil() {
        TableName = ApplicationUtil.getContext().getResources().getString(R.string.permissiontable);
        DB = MyDbHelper.getDBInstance();
    }

    public List<Permission> getAllPermission() {
        List<Permission> permissionList = new ArrayList<Permission>();
        Cursor cursor = DB.query(TableName, columns, null, null, null, null, sortBy);
        getPermission(cursor, permissionList);
        return permissionList;
    }

    public Permission getPermissionByID(int id) {
        List<Permission> permissionList = new ArrayList<Permission>();
        Cursor cursor = DB.query(TableName, columns, "id=?", new String[]{id + ""}, null, null, sortBy);
        getPermission(cursor, permissionList);
        if (permissionList.isEmpty())
            return null;
        else
            return permissionList.get(0);
    }

    public Permission getPermissionByName(String name) {
        List<Permission> permissionList = new ArrayList<Permission>();
        Cursor cursor = DB.query(TableName, columns, "name=?", new String[]{name}, null, null, sortBy);
        getPermission(cursor, permissionList);
        if (permissionList.isEmpty())
            return null;
        else
            return permissionList.get(0);
    }

    public Permission getPermission() {
        return new Permission();
    }

    void getPermission(Cursor cursor, List<Permission> permissionList) {
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Permission permission = getPermission();
                    permission.setID(cursor.getInt(0));
                    permission.setName(cursor.getString(1));
                    permission.setEnable(cursor.getInt(2));
                    permissionList.add(permission);

                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
    }
}
