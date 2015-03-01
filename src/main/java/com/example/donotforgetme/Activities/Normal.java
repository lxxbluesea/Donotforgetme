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
import com.example.donotforgetme.MyListener.MyPageChangeListener;
import com.example.donotforgetme.MyListener.MyPagerAdapter;
import com.example.donotforgetme.R;

import java.util.ArrayList;
import java.util.List;

public class Normal extends Fragment {

    ViewPager viewPager;
    List<View> views;
    List<String> titles;
    ImageView cursor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        System.out.println("onCreateView");
        return inflater.inflate(R.layout.activity_normal,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //这里写一些代码加载的工作
        System.out.println("onActivityCreated");
        initViewPager();
    }

    void initViewPager()
    {
        viewPager=(ViewPager)getView().findViewById(R.id.normal_viewpager);
        cursor=(ImageView)getView().findViewById(R.id.normal_cursor);
        if(viewPager!=null)
        {
            views=new ArrayList<View>();
            titles=new ArrayList<String>();

            LayoutInflater layoutInflater=LayoutInflater.from(this.getActivity());

            views.add(layoutInflater.inflate(R.layout.activity_normal_executing,null));
            views.add(layoutInflater.inflate(R.layout.activity_normal_delay,null));
            views.add(layoutInflater.inflate(R.layout.activity_normal_delete,null));
            views.add(layoutInflater.inflate(R.layout.activity_normal_finish,null));

            titles.add(getResources().getString(R.string.title_activity_executing));
            titles.add(getResources().getString(R.string.title_activity_delay));
            titles.add(getResources().getString(R.string.title_activity_delete));
            titles.add(getResources().getString(R.string.title_activity_finish));

            viewPager.setAdapter(new MyPagerAdapter(views,titles));
            viewPager.setCurrentItem(0);
            viewPager.setOnPageChangeListener(new MyPageChangeListener(initTextView(),cursor,getDisplayMetrice(),4));


        }
    }

    List<TextView> initTextView()
    {
        TextView execute,delay,delete,finish;
        execute=(TextView)getView().findViewById(R.id.normal_execute_tab_title);
        execute.setOnTouchListener(new MyClickTouchListener(0,viewPager));
        delay=(TextView)getView().findViewById(R.id.normal_delay_tab_title);
        delay.setOnTouchListener(new MyClickTouchListener(1,viewPager));
        delete=(TextView)getView().findViewById(R.id.normal_delete_tab_title);
        delete.setOnTouchListener(new MyClickTouchListener(2,viewPager));
        finish=(TextView)getView().findViewById(R.id.normal_finish_tab_title);
        finish.setOnTouchListener(new MyClickTouchListener(3,viewPager));

        List<TextView> textViews=new ArrayList<TextView>();
        textViews.add(execute);
        textViews.add(delay);
        textViews.add(delete);
        textViews.add(finish);

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
//
//    /**
//     * 得到游标图片的宽度
//     * @return
//     */
//    int getCursorWidth()
//    {
//        int width= BitmapFactory.decodeResource(getResources(),R.drawable.cursor).getWidth();
//        return 1;
//    }

}
