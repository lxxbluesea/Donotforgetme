package com.example.donotforgetme.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.donotforgetme.Entities.Item;
import com.example.donotforgetme.MyListener.MyListViewAdapter;
import com.example.donotforgetme.MyListener.MyClickTouchListener;
import com.example.donotforgetme.MyListener.MyListViewAdapterListener;
import com.example.donotforgetme.MyListener.MyPageAdapterInstanceListener;
import com.example.donotforgetme.MyListener.MyPageChanged;
import com.example.donotforgetme.MyListener.MyPageChangedListener;
import com.example.donotforgetme.MyListener.MyPagerAdapter;
import com.example.donotforgetme.R;
import com.example.donotforgetme.Utils.ItemUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Normal extends Fragment {

    ViewPager viewPager;
    List<View> views;
    List<String> titles;
    ImageView cursor;


    //Normal_Executing窗口的代码

    HashMap<String, ListView> listViewList;
    HashMap<String, List<Item>> itemList;
    HashMap<String, MyListViewAdapter> adapterList;
    //ListView listview_execute=null, listview_nobegin=null, listview_delete=null, listview_note=null, listview_finish=null;
    //List<Item> itemList_execute=null, itemList_delete=null, itemList_nobegin=null, itemList_note=null, itemList_finish=null;
    //MyAdapter executeAdapter, nobeginAdapter, deleteAdapter, finishAdapter, noteAdpater;
    ItemUtil itemUtil;


    //    MyAdpater[] adpaters={executeAdapter, nobeginAdapter, deleteAdapter, finishAdapter, noteAdpater};
    View head;
    int Index = 0;//当前ViewPager值
    Item currentItem = null;
    //int type = 1;//分类，传入不同的值，得到不同的结果
    ProgressDialog progressDialog;
    MyPagerAdapter myPagerAdapter;
    MyPageChanged myPageChanged;

    boolean ctrlFlag = true;//一个控制位，来控制更新当前的ListView

    //=========结束==========


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        System.out.println("onCreateView");
        return inflater.inflate(R.layout.activity_normal, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //这里写一些代码加载的工作
        System.out.println("onActivityCreated");
        itemUtil = new ItemUtil();//实例化ItemUtil
        //initListView();
        listViewList = new HashMap<String, ListView>();
        itemList = new HashMap<String, List<Item>>();
        adapterList = new HashMap<String, MyListViewAdapter>();
        initViewPager();

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Normal", "页面显示,Index is:"+Index+",ctrlFlag is:"+ctrlFlag);
//        if(Index==0 || Index==4) {
//            if (ctrlFlag) {
//                Log.d("Normal", "执行更新");
//                getData();
//            }
//        }
    }

    /**
     * 初始化ListView和对应的List<Item>
     */
    void initListView() {
//        listViewList=new ArrayList<ListView>();
//        ListView listview_execute=null, listview_nobegin=null, listview_delete=null, listview_note=null, listview_finish=null;
//        listViewList.add(listview_execute);
//        listViewList.add(listview_finish);
//        listViewList.add(listview_nobegin);
//        listViewList.add(listview_delete);
//        listViewList.add(listview_note);
//
//        itemList=new ArrayList<List<Item>>();
//        List<Item> itemList_execute=null, itemList_delete=null, itemList_nobegin=null, itemList_note=null, itemList_finish=null;
//        itemList.add(itemList_execute);
//        itemList.add(itemList_finish);
//        itemList.add(itemList_nobegin);
//        itemList.add(itemList_delete);
//        itemList.add(itemList_note);
    }

    /**
     * 实例化ViewPager的Tab部分
     *
     * @return
     */
    List<TextView> initTextView() {
        TextView execute, nobegin, delete, finish, note;
        execute = (TextView) getActivity().findViewById(R.id.normal_execute_tab_title);
        execute.setOnTouchListener(new MyClickTouchListener(0, viewPager));
        finish = (TextView) getActivity().findViewById(R.id.normal_finish_tab_title);
        finish.setOnTouchListener(new MyClickTouchListener(1, viewPager));
        nobegin = (TextView) getActivity().findViewById(R.id.normal_nobegin_tab_title);
        nobegin.setOnTouchListener(new MyClickTouchListener(2, viewPager));
        delete = (TextView) getActivity().findViewById(R.id.normal_delete_tab_title);
        delete.setOnTouchListener(new MyClickTouchListener(3, viewPager));
        note = (TextView) getView().findViewById(R.id.normal_note_tab_title);
        note.setOnTouchListener(new MyClickTouchListener(4, viewPager));

        List<TextView> textViews = new ArrayList<TextView>();
        textViews.add(execute);
        textViews.add(finish);
        textViews.add(nobegin);
        textViews.add(delete);
        textViews.add(note);

        return textViews;

    }

    /**
     * 初始化ViewPager
     */
    void initViewPager() {
        viewPager = (ViewPager) getView().findViewById(R.id.normal_viewpager);
        cursor = (ImageView) getView().findViewById(R.id.normal_cursor);
        if (viewPager != null) {
            views = new ArrayList<View>();
            titles = new ArrayList<String>();

            LayoutInflater layoutInflater = LayoutInflater.from(this.getActivity());

            head = layoutInflater.inflate(R.layout.listview_head, null);

            views.add(layoutInflater.inflate(R.layout.activity_normal_executing, null));
            views.add(layoutInflater.inflate(R.layout.activity_normal_finish, null));
            views.add(layoutInflater.inflate(R.layout.activity_normal_nobegin, null));
            views.add(layoutInflater.inflate(R.layout.activity_normal_delete, null));
            views.add(layoutInflater.inflate(R.layout.activity_normal_note, null));

            titles.add(getResources().getString(R.string.title_activity_executing));
            titles.add(getResources().getString(R.string.title_activity_finish));
            titles.add(getResources().getString(R.string.title_activity_nobegin));
            titles.add(getResources().getString(R.string.title_activity_delete));
            titles.add(getResources().getString(R.string.title_activity_note));

            //自定义ViewPager的PagerAdapter事件
            myPagerAdapter = new MyPagerAdapter(views, titles);
            myPagerAdapter.setMyPageAdapterListener(myPageAdapterListener);//为PagerAdapter添加回调，当一个页面添加完成时，执行此方法
            viewPager.setAdapter(myPagerAdapter);//设置Adpater
            myPageChanged=new MyPageChanged(getActivity(),initTextView(),cursor,myPageChangedListener);
            viewPager.setOnPageChangeListener(myPageChanged);
            viewPager.setCurrentItem(Index);//设置当前页面为第一个页面


        }
    }

    MyPageChangedListener myPageChangedListener=new MyPageChangedListener() {
        @Override
        public void Changed(int index) {
            Index=index;
        }
    };


    /**
     * 设置ViewPager的Adapter，当View添加到ViewPager完成后执行此代码
     * 这里的作用是第一次加载数据时使用
     */
    MyPageAdapterInstanceListener myPageAdapterListener = new MyPageAdapterInstanceListener() {
        @Override
        public void Instance(View view, int index) {
            //Log.d("Normal","Instance"+index);
            //progressDialog = ProgressDialog.show(getActivity(), getResources().getString(R.string.import_normal_warn), getResources().getString(R.string.import_normal_warntext));
            //Index = index;
            if (!listViewList.containsKey(Integer.toString(index))) {
                switch (index) {
                    case 0:
                        listViewList.put(Integer.toString(index), (ListView) view.findViewById(R.id.normal_execute_listview));
                        break;
                    case 1:

                        listViewList.put(Integer.toString(index), (ListView) view.findViewById(R.id.normal_finish_listview));
                        break;
                    case 2:

                        listViewList.put(Integer.toString(index), (ListView) view.findViewById(R.id.normal_nobegin_listview));
                        break;
                    case 3:

                        listViewList.put(Integer.toString(index), (ListView) view.findViewById(R.id.normal_delete_listview));
                        break;
                    case 4:

                        listViewList.put(Integer.toString(index), (ListView) view.findViewById(R.id.normal_note_listview));
                        break;
                }
            }
            getData(index);

        }

        @Override
        public void Destory(View view, int index) {
            //Log.d("Normal", "Destory" + index);
            if (listViewList.containsKey(Integer.toString(index)))
                listViewList.remove(Integer.toString(index));
            if (itemList.containsKey(Integer.toString(index)))
                itemList.remove(Integer.toString(index));
            if (adapterList.containsKey(Integer.toString(index)))
                adapterList.remove(Integer.toString(index));
        }
    };


//以下代码是处理ViewPager内部的Activity代码

//    void initData() {
//        Index = 0;
//        progressDialog = ProgressDialog.show(getActivity(), getResources().getString(R.string.import_normal_warn), getResources().getString(R.string.import_normal_warntext));
//        //listview_execute = (ListView) getActivity().findViewById(R.id.normal_execute_listview);
//        if (!listViewList.containsKey(Integer.toString(Index)))
//            listViewList.put(Integer.toString(Index), (ListView) getActivity().findViewById(R.id.normal_execute_listview));
//        //type = StatusUtil.EXECUTE;
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                getData();
//            }
//        }).start();
//    }

    /**
     * 获得数据，
     */
    void getData(int index) {
        //如果Key不存在，则插入新的数据，如果Key存在，则替换掉

        itemList.put(Integer.toString(index), itemUtil.getItems(index));
        if (!adapterList.containsKey(Integer.toString(index))) {
            //这里添加一个回调，用来更新Listview
            adapterList.put(Integer.toString(index), new MyListViewAdapter(getActivity(),itemList.get(Integer.toString(index)), index,this,new MyListViewAdapterListener() {
                @Override
                public void Finished(int index) {
                    ctrlFlag=false;
                    getData();
                    ctrlFlag=true;
                }
            }));
            listViewList.get(Integer.toString(index)).setAdapter(adapterList.get(Integer.toString(index)));
        } else {
            myPagerAdapter.notifyDataSetChanged();
        }

//                Message msg = new Message();
//                getDateHandle.sendMessage(msg);
    }

    void getData() {

        itemList.put(Integer.toString(Index), itemUtil.getItems(Index));
        myPagerAdapter.notifyDataSetChanged();

    }


//    Handler getDateHandle = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
////            if (!adapterList.containsKey(Index)) {
////                adapterList.put(Integer.toString(Index), new MyListViewAdapter(getActivity(), itemList.get(Integer.toString(Index)), Index));
////                listViewList.get(Index).setAdapter(adapterList.get(Index));
////            } else {
////                adapterList.get(Index).notifyDataSetChanged();
////            }
//            if (progressDialog != null)
//                progressDialog.dismiss();
//            super.handleMessage(msg);
//        }
//    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==MyListViewAdapter.AddNotice ||requestCode==MyListViewAdapter.AddNote)
        {
            if(resultCode==MyListViewAdapter.AddNoticeResult || resultCode==MyListViewAdapter.AddNoteResult)
            {
                Log.d("Normal", "onActivityResult:" + requestCode+","+resultCode);
                getData();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
