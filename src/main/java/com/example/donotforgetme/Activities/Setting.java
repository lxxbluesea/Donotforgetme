package com.example.donotforgetme.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.donotforgetme.MyListener.MyClickTouchListener;
import com.example.donotforgetme.MyListener.MyPageChanged;
import com.example.donotforgetme.MyListener.MyPagerAdapter;
import com.example.donotforgetme.R;

import java.util.ArrayList;
import java.util.List;

public class Setting extends Fragment {

    ViewPager viewPager;
    List<View> views;
    List<String> titles;
    ImageView cursor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return  inflater.inflate(R.layout.activity_setting,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //这里写上初始化代码
        initViewPager();
    }

    void initViewPager()
    {
        viewPager=(ViewPager)getView().findViewById(R.id.setting_viewpager);
        cursor=(ImageView)getView().findViewById(R.id.setting_cursor);
        if(viewPager!=null)
        {
            views=new ArrayList<View>();
            titles=new ArrayList<String>();

            LayoutInflater layoutInflater=LayoutInflater.from(this.getActivity());

            views.add(layoutInflater.inflate(R.layout.activity_setting_normal,null));
            views.add(layoutInflater.inflate(R.layout.activity_setting_warn,null));
            views.add(layoutInflater.inflate(R.layout.activity_setting_backup,null));


            titles.add(getResources().getString(R.string.title_activity_setting_normal));
            titles.add(getResources().getString(R.string.title_activity_setting_warn));
            titles.add(getResources().getString(R.string.title_activity_setting_backup));


            viewPager.setAdapter(new MyPagerAdapter(views,titles));
            viewPager.setCurrentItem(0);
            viewPager.setOnPageChangeListener(new MyPageChanged(getActivity(),initTextView(),cursor));


        }
    }

    List<TextView> initTextView()
    {
        TextView normal,warn,backup;
        normal=(TextView)getView().findViewById(R.id.setting_normal_tab_title);
        normal.setOnTouchListener(new MyClickTouchListener(0,viewPager));
        warn=(TextView)getView().findViewById(R.id.setting_warn_tab_title);
        warn.setOnTouchListener(new MyClickTouchListener(1,viewPager));
        backup=(TextView)getView().findViewById(R.id.setting_backup_tab_title);
        backup.setOnTouchListener(new MyClickTouchListener(2,viewPager));


        List<TextView> textViews=new ArrayList<TextView>();
        textViews.add(normal);
        textViews.add(warn);
        textViews.add(backup);


        return textViews;

    }

    /**
     * 获得屏幕的信息，其中包括有长和宽
     * @return
     */
    DisplayMetrics getDisplayMetrice()
    {
        DisplayMetrics displayMetrics=new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }
}
