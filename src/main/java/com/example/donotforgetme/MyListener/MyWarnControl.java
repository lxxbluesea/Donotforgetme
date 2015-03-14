package com.example.donotforgetme.MyListener;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.text.method.Touch;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.donotforgetme.Activities.Add;
import com.example.donotforgetme.Entities.ItemNotice;
import com.example.donotforgetme.Entities.Notice;
import com.example.donotforgetme.R;
import com.example.donotforgetme.Utils.ApplicationUtil;
import com.example.donotforgetme.Utils.DateTimePickDialogUtil;
import com.example.donotforgetme.Utils.DateUtil;
import com.example.donotforgetme.Utils.NoticeUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ZJGJK03 on 2015/2/25.
 */
public class MyWarnControl extends LinearLayout {

    int warnType;
    String warnDate;

    Button btn_dateTime;
    //Button btn_delete;
    Context context;
    Spinner sp_warnType;
    Button sp_warnDate;

    ItemNotice notice;

    /**
     * 读取ItemNotice对象
     * @return
     */
    public ItemNotice getNotice() {
        notice.setNoticeID(getWarnType());
        notice.setNoticeTime(DateUtil.getDateLong(sp_warnDate.getText().toString()));
        return notice;
    }

    /**
     * 绑定ItemNotice对象
     * @param notice
     */
    public void setNotice(ItemNotice notice) {
        setWarnType(notice.getNoticeID()-1);//减1的原因是Spinner的第一个元素索引是0，而数据库中存的是1开始，这里的设置是以数据的值来设置的。
        setWarnDate(DateUtil.getDateString(notice.getNoticeTime()));

        this.notice = notice;
    }

    public MyWarnControl(Context context) {
        super(context);
        this.context = context;
        initControl();
    }



    public MyWarnControl(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initControl();
    }

    void initControl() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.warncontrol, this);

        sp_warnType = (Spinner) this.findViewById(R.id.warncontrol_sp_warntype);
//        sp_warnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Log.d("MyWarnControl","position:"+position+",内容是："+sp_warnType.getSelectedItem().toString());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        sp_warnDate=(Button)this.findViewById(R.id.warncontrol_btn_datetime);
        sp_warnDate.setOnClickListener(listener);

        btn_dateTime=(Button)this.findViewById(R.id.warncontrol_btn_datetime);
        btn_dateTime.setOnClickListener(listener);
        initTypeSpinner();
    }


    OnClickListener listener=new OnClickListener() {
        @Override
        public void onClick(View v) {
            if(TextUtils.isEmpty(sp_warnDate.getText().toString()))
                return;
            else
            {
                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil((Activity)context,sp_warnDate.getText().toString());
                dateTimePicKDialog.dateTimePicKDialog(sp_warnDate);
            }
        }
    };

    public int getWarnType() {

        if (sp_warnType != null && sp_warnType.getCount() > 0) {
            this.warnType = sp_warnType.getSelectedItemPosition() + 1;
        }
        return this.warnType;
    }

    public void setWarnType(int warnType) {
        this.warnType = warnType;
        sp_warnType.setSelection(this.warnType);
    }

    public String getWarnDate() {
        return warnDate;
    }

    public void setWarnDate(String warnDate) {
        this.warnDate = warnDate;
        sp_warnDate.setText(this.warnDate);
    }

    void initTypeSpinner()
    {
        List<String> items=new ArrayList<String>();
        List<Notice> noticeList= NoticeUtil.getInstance().getAllNotice();
        for (Notice notice:noticeList)
        {
            items.add(notice.getName());
        }

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this.getContext(),android.R.layout.simple_spinner_dropdown_item,items);
        sp_warnType.setAdapter(adapter);

    }

}
