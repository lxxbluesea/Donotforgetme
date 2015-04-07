package com.example.donotforgetme.MyListener;

import android.view.View;

/**
 * Created by ZJGJK03 on 2015/3/16.
 */
public interface MyPageAdapterInstanceListener {
    void Instance(View view,int index);
    void Destory(View view,int index);
}
