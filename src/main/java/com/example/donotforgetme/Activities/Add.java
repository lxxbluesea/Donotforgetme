package com.example.donotforgetme.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.donotforgetme.R;
import com.example.donotforgetme.MyListener.MyPopWin;
import com.example.donotforgetme.Utils.ApplicationUtil;
import com.example.donotforgetme.Utils.DateTimePickDialogUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Add extends Fragment {

    Button btn_import,btn_beginDatetime,btn_endDatetime;
    EditText et_beginDatetime,et_endDatetime;
    MyPopWin popWin;

    String initDateTime;
    DateTimePickDialogUtil dateTimePicKDialog;

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
    }

    void init_controls()
    {
        btn_beginDatetime=(Button)getActivity().findViewById(R.id.add_btn_selectbegindatetime);
        btn_beginDatetime.setOnClickListener(listener);
        btn_endDatetime=(Button)getActivity().findViewById(R.id.add_btn_selectenddatetime);
        btn_endDatetime.setOnClickListener(listener);

        et_beginDatetime=(EditText)getActivity().findViewById(R.id.add_et_startdate);
        et_endDatetime=(EditText)getActivity().findViewById(R.id.add_et_enddate);


        Date date1=new Date(new Date().getTime());
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(ApplicationUtil.getContext().getResources().getString(R.string.datetimeformat1));
        initDateTime= simpleDateFormat.format(date1);

        Log.d("Add",initDateTime);

        dateTimePicKDialog = new DateTimePickDialogUtil(Add.this.getActivity(), initDateTime);

    }

    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId())
            {
                case R.id.add_btn_selectbegindatetime:
                    dateTimePicKDialog.dateTimePicKDialog(et_beginDatetime);
                    break;
                case R.id.add_btn_selectenddatetime:
                    dateTimePicKDialog.dateTimePicKDialog(et_endDatetime);
                    break;
            }

        }
    };

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
