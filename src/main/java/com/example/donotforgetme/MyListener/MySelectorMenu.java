package com.example.donotforgetme.MyListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.donotforgetme.R;

/**
 * Created by ZJGJK03 on 2015/2/25.
 */
public class MySelectorMenu extends Activity implements View.OnClickListener {

    Button btn_warn,btn_note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectormenu);

    }


    void initPopWindow()
    {
        LayoutInflater inflater=this.getLayoutInflater();
        View popwindow=inflater.inflate(R.layout.selectormenu,null);
        btn_warn=(Button)popwindow.findViewById(R.id.selectormenu_btn_warn);
        btn_warn=(Button)popwindow.findViewById(R.id.selectormenu_btn_note);
        btn_warn.setOnClickListener(this);
        btn_note.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

    }
}
