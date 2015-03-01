package com.example.donotforgetme.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.donotforgetme.R;
import com.example.donotforgetme.MyListener.MyPopWin;

public class Add extends Fragment {

    Button btn_import;
    MyPopWin popWin;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_add,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //这里可以添加代码
        init_ImportBtn();
    }

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
