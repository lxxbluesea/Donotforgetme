package com.example.donotforgetme.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.donotforgetme.R;

public class Search extends Fragment {

    ListView listView;
    Button btn_search;
    EditText et_keyword;
    Button btn_dateoffset;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.activity_search,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //这里写一些初始化的代码
    }

    void initControl()
    {
        listView=(ListView)getActivity().findViewById(R.id.search_listview);
        btn_search=(Button)getActivity().findViewById(R.id.search_btn_execute);
        btn_search.setOnClickListener(onClickListener);
        btn_dateoffset=(Button)getActivity().findViewById(R.id.search_btn_date);
        btn_dateoffset.setOnClickListener(onClickListener);
        et_keyword=(EditText)getActivity().findViewById(R.id.search_et_keyword);
    }
    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
}
