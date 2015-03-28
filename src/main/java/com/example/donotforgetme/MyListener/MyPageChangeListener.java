package com.example.donotforgetme.MyListener;

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
public class MyPageChangeListener implements ViewPager.OnPageChangeListener {

    ImageView imageView;
    DisplayMetrics dm;
    int oldindex=0;
    int bmpW=0,offset=0;
    int aveWidth=0;
    int oldx,newx;
    List<TextView> textViewList;
    int viewCount;

    MyPageChangerUpdateDataListener pageChangerUpdateDataListener;

    public void setPageChangerUpdateDataListener(MyPageChangerUpdateDataListener pageChangerUpdateDataListener) {
        this.pageChangerUpdateDataListener = pageChangerUpdateDataListener;
    }

    /**
     *
     * @param imageView
     * @param dm
     */
    public MyPageChangeListener(List<TextView> textViewList,ImageView imageView,DisplayMetrics dm,int viewCount,MyPageChangerUpdateDataListener listener) {
        this.textViewList = textViewList;
        this.imageView = imageView;
        this.dm = dm;
        this.viewCount=viewCount;
        this.pageChangerUpdateDataListener=listener;

        this.bmpW = BitmapFactory.decodeResource(ApplicationUtil.getContext().getResources(), R.drawable.cursor).getWidth();
        int screenW = dm.widthPixels;
        offset = (screenW / viewCount - bmpW) / 2;
        oldx = offset;

        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        imageView.setImageMatrix(matrix);
        aveWidth = offset * 2 + bmpW;
    }

    /**
     *
     * @param imageView
     * @param dm
     */
    public MyPageChangeListener(List<TextView> textViewList,ImageView imageView,DisplayMetrics dm,int viewCount) {
        this.textViewList = textViewList;
        this.imageView = imageView;
        this.dm = dm;
        this.viewCount=viewCount;

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
    public void onPageScrolled(int i, float v, int i2) {

    }

    @Override
    public void onPageSelected(int i) {
        Animation animation;
        int tmp = i - oldindex;

        oldx=newx;
        newx=oldx+tmp*aveWidth;

        animation = new TranslateAnimation(oldx, newx, 0, 0);
        animation.setFillAfter(true);
        animation.setDuration(300);
        imageView.startAnimation(animation);
        oldindex = i;

        textViewList.get(i).requestFocus();

        if(pageChangerUpdateDataListener!=null)
            pageChangerUpdateDataListener.execute(i);

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
