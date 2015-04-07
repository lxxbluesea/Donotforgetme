package com.example.donotforgetme.MyListener;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by ZJGJK03 on 2015/2/22.
 */
public class MyPagerAdapter extends PagerAdapter {

    List<View> views;
    List<String> titles;
    int childCount=0;

    MyPageAdapterInstanceListener myPageAdapterListener;

    public void setMyPageAdapterListener(MyPageAdapterInstanceListener myPageAdapterListener) {
        this.myPageAdapterListener = myPageAdapterListener;
    }

    /**
     *
     * @param views
     * @param titles
     */
    public MyPagerAdapter(List<View> views, List<String> titles) {
        this.views = views;
        this.titles = titles;
        childCount=views.size();
    }

    @Override
    public int getCount() {
        return childCount;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
        //Log.d("instantiateItem","destroyItem:"+position);
        if(myPageAdapterListener!=null)
            myPageAdapterListener.Destory(views.get(position),position);
        container.removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //return super.instantiateItem(container, position);
        //Log.d("instantiateItem","instantiateItem:"+position);
        container.addView(views.get(position));
        if (myPageAdapterListener != null)
            myPageAdapterListener.Instance(views.get(position), position);
        return views.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
