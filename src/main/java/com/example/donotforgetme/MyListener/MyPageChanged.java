package com.example.donotforgetme.MyListener;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.donotforgetme.R;
import com.example.donotforgetme.Utils.ApplicationUtil;

import java.util.List;

/**
 * Created by ZJGJK03 on 2015/2/22.
 */
public class MyPageChanged extends ViewPager.SimpleOnPageChangeListener {

    ImageView imageView;
    DisplayMetrics dm=new DisplayMetrics();
    int oldindex=0;
    int bmpW=0,offset=0;
    int aveWidth=0;
    int oldx,newx;
    List<TextView> textViewList;
    int viewCount;
    Activity activity;

    MyPageChangedListener pageChangerUpdateDataListener;

    public void setPageChangerUpdateDataListener(MyPageChangedListener pageChangerUpdateDataListener) {
        this.pageChangerUpdateDataListener = pageChangerUpdateDataListener;
    }

    public MyPageChanged(Activity activity, List<TextView> textViewList, ImageView imageView, MyPageChangedListener listener) {
        this.activity=activity;
        this.textViewList = textViewList;
        this.imageView = imageView;
        this.viewCount=this.textViewList.size();
        this.pageChangerUpdateDataListener=listener;


        activity.getWindowManager().getDefaultDisplay().getMetrics(this.dm);
        this.bmpW = BitmapFactory.decodeResource(ApplicationUtil.getContext().getResources(), R.drawable.cursor).getWidth();
        int screenW = dm.widthPixels;
        offset = (screenW / viewCount - bmpW) / 2;
        oldx = offset;

        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        imageView.setImageMatrix(matrix);
        aveWidth = offset * 2 + bmpW;
    }

    public MyPageChanged(Activity activity, List<TextView> textViewList, ImageView imageView) {
        this.textViewList = textViewList;
        this.imageView = imageView;
        this.activity=activity;
        this.viewCount=textViewList.size();

        activity.getWindowManager().getDefaultDisplay().getMetrics(this.dm);
        this.bmpW = BitmapFactory.decodeResource(ApplicationUtil.getContext().getResources(), R.drawable.cursor).getWidth();
        int screenW = dm.widthPixels;
        offset = (screenW / viewCount - bmpW) / 2;
        oldx = offset;

        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        imageView.setImageMatrix(matrix);
        aveWidth = offset * 2 + bmpW;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        super.onPageScrollStateChanged(state);
    }

    @Override
    public void onPageSelected(int position) {
        Animation animation;
        int tmp = position - oldindex;
        oldx=newx;
        newx=oldx+tmp*aveWidth;

        animation = new TranslateAnimation(oldx, newx, 0, 0);
        animation.setFillAfter(true);
        animation.setDuration(300);
        imageView.startAnimation(animation);
        oldindex = position;

        textViewList.get(position).requestFocus();

        if(pageChangerUpdateDataListener!=null)
            pageChangerUpdateDataListener.Changed(position);
        super.onPageSelected(position);
    }




    /**
     * 获得屏幕的信息，其中包括有长和宽
     *
     * @return
     */
    DisplayMetrics getDisplayMetrice() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

}
