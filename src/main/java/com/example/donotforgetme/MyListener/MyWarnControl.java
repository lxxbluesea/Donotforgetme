package com.example.donotforgetme.MyListener;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.donotforgetme.R;

import java.util.Date;

/**
 * Created by ZJGJK03 on 2015/2/25.
 */
public class MyWarnControl extends LinearLayout {

    String num = "1.";
    int warnType;
    Date warnDate;

    Context context;
    TextView tv_num;
    Spinner sp_warnType;
    EditText et_warnDate;

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

        tv_num = (TextView) this.findViewById(R.id.warncontrol_number);
        sp_warnType = (Spinner) this.findViewById(R.id.warncontrol_warntype);
        et_warnDate=(EditText)this.findViewById(R.id.warncontrol_warndate);
        initWarnType();
    }

    /**
     * 初始化提醒的方式，只显示可用的提醒方式
     */
    void initWarnType() {
        //这里代码先空着
    }

    /**
     * 设置序号,字符类型，比如：1.
     */
    public void setNum(String num) {
        this.num = num;
        tv_num.setText(this.num);
    }

    public int getWarnType() {

        if (sp_warnType != null && sp_warnType.getCount() > 0) {
            this.warnType = getWarnTypeNum(sp_warnType.getSelectedItem().toString());
        }
        return this.warnType;
    }


    public void setWarnType(int warnType) {
        this.warnType = warnType;
        sp_warnType.setSelection(this.warnType);
    }

//    public Date getWarnDate() {
//        return Date.parse(et_warnDate.getText().toString());
//    }
//
//    public void setWarnDate(Date warnDate) {
//        this.warnDate = warnDate;
//        et_warnDate.setText(this.warnDate.toString());
//    }

    /**
     * 获得提醒方式描述文字所对应的序号
     *
     * @param warnType
     * @return
     */
    int getWarnTypeNum(String warnType) {
        return 1;
    }

}
