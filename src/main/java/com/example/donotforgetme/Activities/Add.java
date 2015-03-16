package com.example.donotforgetme.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.example.donotforgetme.Entities.Item;
import com.example.donotforgetme.Entities.ItemNotice;
import com.example.donotforgetme.MyListener.MyDateTimePickDialogListener;
import com.example.donotforgetme.MyListener.MyWarnControl;
import com.example.donotforgetme.R;
import com.example.donotforgetme.MyListener.MyPopWin;
import com.example.donotforgetme.Utils.DateTimePickDialogUtil;
import com.example.donotforgetme.Utils.DateUtil;
import com.example.donotforgetme.Utils.ItemUtil;

import java.util.Date;

public class Add extends Activity {

    Button btn_import,btn_beginDate,btn_endDate,btn_addwarn,btn_deletewarn;
    //EditText et_beginDatetime,et_endDatetime;
    MyPopWin popWin;

    LinearLayout ll_warn;
    String initDateTime;
    DateTimePickDialogUtil dateTimePicKDialog;

    Item item;
    ItemUtil itemUtil;

    RadioGroup rg_quickAdd;
    //RadioButton rb_tab_normal;
    Button btn_save;
    EditText et_content;
    Intent intent;

    int itemID=-1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        intent=getIntent();
        if(intent!=null) {
            itemID = intent.getIntExtra("id", -1);
        }

        //这里可以添加代码
        init_controls();
        init_ImportBtn();
        if(itemID==-1) {
            InitItem();
        }
        else {//如果不是-1，则传入ID，并获得此Item
            InitItem(itemID);
        }
    }

    /**
     * 初始化Item和ItemUtil对象
     */
    void InitItem()
    {
        itemUtil=new ItemUtil();
        item=itemUtil.getNewItem();
        btn_beginDate.setText(DateUtil.getDateString(item.getBeginDateTime()));
        btn_endDate.setText(DateUtil.getDateString(item.getEndDateTime()));
    }
    void InitItem(int id) {
        itemUtil = new ItemUtil(id);
        item=itemUtil.getItem();
        if (item.equals(null)) {
            Toast.makeText(this, getResources().getString(R.string.add_item_null), Toast.LENGTH_SHORT).show();
            finish();
        }
        //设置内容
        et_content.setText(item.getContent());
        //设置开始时间和结束时间
        btn_beginDate.setText(DateUtil.getDateString(item.getBeginDateTime()));
        btn_endDate.setText(DateUtil.getDateString(item.getEndDateTime()));
        //初始化提醒控件
        int noticecount = item.getNoticeTime();
        for (int i = 0; i < noticecount; i++) {
            MyWarnControl warnControl = new MyWarnControl(Add.this);
            warnControl.setNotice(itemUtil.getNoticeList().get(i));
            ll_warn.addView(warnControl);
        }
    }
    /**
     * 重置页面
     */
    void RemoveItem()
    {
        itemUtil=new ItemUtil();
        item=itemUtil.getNewItem();
        btn_beginDate.setText(DateUtil.getDateString(item.getBeginDateTime()));
        btn_endDate.setText(DateUtil.getDateString(item.getEndDateTime()));
        rg_quickAdd.check(R.id.add_rb_onehour);
        et_content.setText("");
        ll_warn.removeAllViews();

    }
    /**
     * 初始化相关的控件
     */
    void init_controls()
    {
        //显示开始日期和结束日期的两个按钮
        btn_beginDate=(Button)this.findViewById(R.id.add_btn_startdate);
        btn_beginDate.setOnClickListener(listener);
        btn_endDate=(Button)this.findViewById(R.id.add_btn_enddate);
        btn_endDate.setOnClickListener(listener);
        //===========结束============

        //添加提醒和删除提醒的按钮
        btn_addwarn=(Button)this.findViewById(R.id.add_btn_add_warn);
        btn_addwarn.setOnClickListener(warnControlListener);
        btn_deletewarn=(Button)this.findViewById(R.id.add_btn_delete_warn);
        btn_deletewarn.setOnClickListener(warnControlListener);
        //===========结束=========

        //获取用来放提醒控件的布局
        ll_warn=(LinearLayout)this.findViewById(R.id.add_ll_warntime);
        //初始化日期
        initDateTime=DateUtil.getDateString(new Date().getTime());

        //快速添加的RadioGroup
        rg_quickAdd=(RadioGroup)this.findViewById(R.id.add_rg_quickadd);
        rg_quickAdd.setOnCheckedChangeListener(rg_changeListener);

        //获得Titlebar的保存按钮
        btn_save=(Button)this.findViewById(R.id.title_btn_save);
        btn_save.setOnClickListener(save_listener);

        //内容编辑框
        et_content=(EditText)this.findViewById(R.id.add_et_content);

//        //获得主框架的第一个Button
//        rb_tab_normal=(RadioButton)this.findViewById(R.id.tab_item_normal);
    }

    /**
     * 保存按钮，把当前的Item保存到数据库中
     */
    View.OnClickListener save_listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Toast.makeText(this."保存按钮",Toast.LENGTH_SHORT).show();
            //保存时需要检查很多内容
            /**
             * 1、内容不能为空，如果为空提醒错误；
             * 2、结束日期必须要大于开始日期，否则提醒错误；
             * 3、提醒日期时间必须在开始日期和结束日期中间，否则提醒错误；
             * 4、如果没有提醒则视为备忘录
             */
            if(TextUtils.isEmpty(et_content.getText().toString())) {
                et_content.setError(getResources().getString(R.string.add_et_error_text));
                return;
            }
            else
            {
                //设置内容
                item.setContent(et_content.getText().toString().trim());
            }
            if(item.getBeginDateTime()>item.getEndDateTime())
            {
                btn_endDate.setError(getResources().getString(R.string.add_btnenddate_error_text));
                return;
            }
            int itemcount=item.getNoticeTime();
            for(int i=0;i<itemcount;i++)
            {
                long noticetime=((MyWarnControl)ll_warn.getChildAt(i)).getNotice().getNoticeTime();
                if(noticetime<item.getBeginDateTime()|| noticetime>item.getEndDateTime())
                {
                    Toast.makeText(Add.this,getResources().getString(R.string.add_warncontrol_error_text),Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            //把提醒控件内的信息更新到ItemUtil中去
            updateItemNoticeControls();

            boolean result;
            if(itemID==-1)
            {
                result=itemUtil.SaveItem();//新增
            }
            else
            {
                result=itemUtil.SaveItem(2);//修改
            }

            if(result)
            {
                finish();
                //保存成功后，需要把主框架设置到
                //RemoveItem();
                //Toast.makeText(Add.this,getResources().getString(R.string.add_savesuccess_text),Toast.LENGTH_SHORT).show();
                //rb_tab_normal.setChecked(true);
            }
            else
            {
                Toast.makeText(Add.this,getResources().getString(R.string.add_savefailed_text),Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**
     * 快速设置事件
     */
    RadioGroup.OnCheckedChangeListener rg_changeListener=new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            long begintime = item.getBeginDateTime();
            long endtime;
            long offset = DateUtil.Onehour;
            switch (checkedId) {
                case R.id.add_rb_onehour:
                    offset *= 1;
                    break;
                case R.id.add_rb_twohours:
                    offset *= 2;
                    break;
                case R.id.add_rb_fourhours:
                    offset *= 4;
                    break;
                case R.id.add_rb_oneday:
                    offset *= 24;
                    break;
                case R.id.add_rb_twodays:
                    offset *= 48;
                    break;
            }
            endtime = begintime + offset;
            item.setEndDateTime(endtime);
            btn_endDate.setText(DateUtil.getDateString(endtime));

            //重新设置次数，并重新计算提醒时间
            itemUtil.setNoticeTimes(item.getNoticeTime());
            //更新控件
            updateControlsItemNotice();
        }
    };

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
                    MyWarnControl warnControl = new MyWarnControl(Add.this);
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
     * 从Control里取出ItemNotice并更新到ItemUtil的ItemNotictList中
     */
    void updateItemNoticeControls()
    {
        int childCount=ll_warn.getChildCount();
        //Log.d("Add",childCount+"");
        for(int i=0;i<childCount;i++) {
            ItemNotice my=((MyWarnControl)ll_warn.getChildAt(i)).getNotice();
            itemUtil.getNoticeList().get(i).setNoticeID(my.getNoticeID());
            itemUtil.getNoticeList().get(i).setNoticeTime(my.getNoticeTime());
        }
    }


    /**
     * 弹出日期和时间的按钮
     */
    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dateTimePicKDialog = new DateTimePickDialogUtil(Add.this,((Button)v).getText().toString());
            dateTimePicKDialog.setDateTimePickDialogListener(new MyDateTimePickDialogListener() {
                @Override
                public void DateTimeChanged(View view) {
//                    switch (view.getId())
//                    {
//                        case R.id.add_btn_startdate:
//                            item.setBeginDateTime(DateUtil.getDateLong(btn_beginDate.getText().toString()));
//                            break;
//                        case R.id.add_btn_enddate:
//                            item.setEndDateTime(DateUtil.getDateLong(btn_endDate.getText().toString()));
//                            break;
//                    }
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
        btn_import=(Button)this.findViewById(R.id.add_btn_import);
        btn_import.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWin.showPopMenu();
            }
        });

        LayoutInflater inflater=this.getLayoutInflater();
        popWin=new MyPopWin(inflater,btn_import,R.layout.importmenu);
    }

}
