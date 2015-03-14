package com.example.donotforgetme.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.donotforgetme.Entities.Item;
import com.example.donotforgetme.MyListener.MyDateTimePickDialogListener;
import com.example.donotforgetme.MyListener.MyWarnControl;
import com.example.donotforgetme.R;
import com.example.donotforgetme.MyListener.MyPopWin;
import com.example.donotforgetme.Utils.ApplicationUtil;
import com.example.donotforgetme.Utils.DateTimePickDialogUtil;
import com.example.donotforgetme.Utils.DateUtil;
import com.example.donotforgetme.Utils.ItemUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Add extends Fragment {

    Button btn_import,btn_beginDate,btn_endDate,btn_addwarn,btn_deletewarn;
    //EditText et_beginDatetime,et_endDatetime;
    MyPopWin popWin;

    LinearLayout ll_warn;
    String initDateTime;
    DateTimePickDialogUtil dateTimePicKDialog;

    Item item;
    ItemUtil itemUtil;

    //List<MyWarnControl>  warnControlList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_add,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //这里可以添加代码
        init_controls();
        init_ImportBtn();
        InitItem();

    }


    /**
     * 初始化Item和ItemUtil对象
     */
    void InitItem()
    {
        itemUtil=ItemUtil.getInstance();
        item=itemUtil.getNewItem();
        btn_beginDate.setText(DateUtil.getDateString(item.getBeginDateTime()));
        btn_endDate.setText(DateUtil.getDateString(item.getEndDateTime()));
    }

    /**
     * 初始化相关的控件
     */
    void init_controls()
    {
        btn_beginDate=(Button)getActivity().findViewById(R.id.add_btn_startdate);
        btn_beginDate.setOnClickListener(listener);

        btn_endDate=(Button)getActivity().findViewById(R.id.add_btn_enddate);
        btn_endDate.setOnClickListener(listener);


        btn_addwarn=(Button)getActivity().findViewById(R.id.add_btn_add_warn);
        btn_addwarn.setOnClickListener(warnControlListener);
        btn_deletewarn=(Button)getActivity().findViewById(R.id.add_btn_delete_warn);
        btn_deletewarn.setOnClickListener(warnControlListener);


        ll_warn=(LinearLayout)getActivity().findViewById(R.id.add_ll_warntime);

        initDateTime=DateUtil.getDateString(new Date().getTime());


        //Log.d("Add",initDateTime);



    }

    /**
     * 添加和删控件时所执行代码
     */
    View.OnClickListener warnControlListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //添加Notice对象
            int count = item.getNoticeTime();
            switch (v.getId()) {
                case R.id.add_btn_add_warn:
                    count++;
                    item.setNoticeTime(count);
                    itemUtil.setNoticeTimes(count);
                    //添加Notice控件
                    MyWarnControl warnControl = new MyWarnControl(Add.this.getActivity());
                    warnControl.setNotice(itemUtil.getNoticeList().get(count - 1));
                    ll_warn.addView(warnControl);
                    break;
                case R.id.add_btn_delete_warn:
                    if (count == 0)
                        return;
                    count--;
                    item.setNoticeTime(count);
                    itemUtil.setNoticeTimes(count);

                    //删除Notice控件
                    if (count >= 0)
                        ll_warn.removeViewAt(count);
                    break;
                default:

            }
            updateControlsItemNotice();
        }
    };


    /**
     * 更新提醒控件里的ItemNotice对象，其实主要是更新时间
     */
    void updateControlsItemNotice()
    {
        int childCount=ll_warn.getChildCount();
        //Log.d("Add",childCount+"");
        for(int i=0;i<childCount;i++) {
            //Log.d("Add",itemUtil.getNoticeList().get(i).getNoticeID()+"");
            //Log.d("Add",DateUtil.getDateString(itemUtil.getNoticeList().get(i).getNoticeTime()));
            ((MyWarnControl) ll_warn.getChildAt(i)).setNotice(itemUtil.getNoticeList().get(i));
        }
    }

    /**
     * 弹出日期和时间的按钮
     */
    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dateTimePicKDialog = new DateTimePickDialogUtil(Add.this.getActivity(), ((Button)v).getText().toString());
            dateTimePicKDialog.setDateTimePickDialogListener(new MyDateTimePickDialogListener() {
                @Override
                public void DateTimeChanged(View view) {
                    switch (view.getId())
                    {
                        case R.id.add_btn_startdate:
                            item.setBeginDateTime(DateUtil.getDateLong(btn_beginDate.getText().toString()));
                            break;
                        case R.id.add_btn_enddate:
                            item.setEndDateTime(DateUtil.getDateLong(btn_endDate.getText().toString()));
                            break;
                    }
                    //重新设置次数，并重新计算提醒时间
                    itemUtil.setNoticeTimes(item.getNoticeTime());
                    //更新控件
                    updateControlsItemNotice();
                }
            });

            switch (v.getId())
            {
                case R.id.add_btn_startdate:
                    dateTimePicKDialog.dateTimePicKDialog(btn_beginDate);
                    item.setBeginDateTime(DateUtil.getDateLong(btn_beginDate.getText().toString()));
                    break;
                case R.id.add_btn_enddate:
                    dateTimePicKDialog.dateTimePicKDialog(btn_endDate);
                    item.setEndDateTime(DateUtil.getDateLong(btn_endDate.getText().toString()));
                    break;
            }


        }
    };

    /**
     * 初始化导入按钮
     */
    void init_ImportBtn()
    {
        btn_import=(Button)getActivity().findViewById(R.id.add_btn_import);
        btn_import.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWin.showPopMenu();
            }
        });

        LayoutInflater inflater=getActivity().getLayoutInflater();
        popWin=new MyPopWin(inflater,btn_import,R.layout.importmenu);
    }

}
