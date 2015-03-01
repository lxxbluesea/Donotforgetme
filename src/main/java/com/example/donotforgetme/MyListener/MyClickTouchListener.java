package com.example.donotforgetme.MyListener;

import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ZJGJK03 on 2015/2/22.
 */
public class MyClickTouchListener implements View.OnTouchListener,View.OnClickListener {

    int index = 0;
    ViewPager viewPager = null;

    public MyClickTouchListener(int index, ViewPager viewPager) {
        this.index = index;
        this.viewPager = viewPager;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            viewPager.setCurrentItem(index);
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        //viewPager.setCurrentItem(index);
    }
}