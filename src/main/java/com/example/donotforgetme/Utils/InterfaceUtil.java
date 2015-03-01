package com.example.donotforgetme.Utils;

import com.example.donotforgetme.Entities.InterfaceEntity;

import java.util.List;

/**
 * Created by ZJGJK03 on 2015/2/28.
 */
public interface InterfaceUtil {

    public List<InterfaceEntity> getAllEntity();
    public InterfaceEntity getEntityByID();
    public InterfaceEntity getEntityByName();
    public InterfaceEntity getEntity();

}
