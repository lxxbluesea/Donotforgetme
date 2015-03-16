package com.example.donotforgetme.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.donotforgetme.Entities.Item;
import com.example.donotforgetme.MyListener.MyClickTouchListener;
import com.example.donotforgetme.MyListener.MyPageAdapterListener;
import com.example.donotforgetme.MyListener.MyPageChangeListener;
import com.example.donotforgetme.MyListener.MyPagerAdapter;
import com.example.donotforgetme.R;
import com.example.donotforgetme.Utils.DateUtil;
import com.example.donotforgetme.Utils.ItemUtil;
import com.example.donotforgetme.Utils.StatusUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Normal extends Fragment {

    ViewPager viewPager;
    List<View> views;
    List<String> titles;
    ImageView cursor;


    //Normal_Executing窗口的代码
    ListView listView;
    ItemUtil itemUtil;

    //=========结束==========


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        System.out.println("onCreateView");
        return inflater.inflate(R.layout.activity_normal,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //这里写一些代码加载的工作
        System.out.println("onActivityCreated");
        initViewPager();
    }

    void initViewPager()
    {
        viewPager=(ViewPager)getView().findViewById(R.id.normal_viewpager);
        cursor=(ImageView)getView().findViewById(R.id.normal_cursor);
        if(viewPager!=null)
        {
            views=new ArrayList<View>();
            titles=new ArrayList<String>();

            LayoutInflater layoutInflater=LayoutInflater.from(this.getActivity());

            views.add(layoutInflater.inflate(R.layout.activity_normal_executing,null));
            views.add(layoutInflater.inflate(R.layout.activity_normal_delay,null));
            views.add(layoutInflater.inflate(R.layout.activity_normal_delete,null));
            views.add(layoutInflater.inflate(R.layout.activity_normal_finish,null));

            titles.add(getResources().getString(R.string.title_activity_executing));
            titles.add(getResources().getString(R.string.title_activity_delay));
            titles.add(getResources().getString(R.string.title_activity_delete));
            titles.add(getResources().getString(R.string.title_activity_finish));

            MyPagerAdapter myPagerAdapter=new MyPagerAdapter(views,titles);
            myPagerAdapter.setMyPageAdapterListener(new MyPageAdapterListener() {
                @Override
                public void Execute(int index) {
                    switch (index)
                    {
                        case 0:
                            initExecuteControls();
                            break;
                        case 1:

                            break;

                        case 2:

                            break;
                        case 3:

                            break;
                        case 4:

                            break;

                    }

                }
            });

            viewPager.setAdapter(myPagerAdapter);
            viewPager.setCurrentItem(0);
            viewPager.setOnPageChangeListener(new MyPageChangeListener(initTextView(),cursor,getDisplayMetrice(),4));

        }
    }

    List<TextView> initTextView()
    {
        TextView execute,delay,delete,finish;
        execute=(TextView)getView().findViewById(R.id.normal_execute_tab_title);
        execute.setOnTouchListener(new MyClickTouchListener(0,viewPager));
        delay=(TextView)getView().findViewById(R.id.normal_delay_tab_title);
        delay.setOnTouchListener(new MyClickTouchListener(1,viewPager));
        delete=(TextView)getView().findViewById(R.id.normal_delete_tab_title);
        delete.setOnTouchListener(new MyClickTouchListener(2,viewPager));
        finish=(TextView)getView().findViewById(R.id.normal_finish_tab_title);
        finish.setOnTouchListener(new MyClickTouchListener(3,viewPager));

        List<TextView> textViews=new ArrayList<TextView>();
        textViews.add(execute);
        textViews.add(delay);
        textViews.add(delete);
        textViews.add(finish);

        return textViews;

    }

    /**
     * 获得屏幕的信息，其中包括有长和宽
     * @return
     */
    DisplayMetrics getDisplayMetrice()
    {
        DisplayMetrics displayMetrics=new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }


    //以下代码是处理Normal_Executing

    void initExecuteControls(){
        listView=(ListView)getActivity().findViewById(R.id.normal_execute_listview);
        itemUtil=new ItemUtil();
        getExecuteDate();
    }

    List<Item> itemList;
    void getExecuteDate()
    {

        itemList=itemUtil.getItems(StatusUtil.EXECUTE);
        List<HashMap<String,Object>> datas=new ArrayList<HashMap<String, Object>>();
        if(!itemList.isEmpty())
        {
            for(Item item:itemList)
            {
                HashMap<String,Object> d=new HashMap<String, Object>();
                d.put("content",item.getContent());
                d.put("begindatetime", DateUtil.getDateString(item.getBeginDateTime()));
                d.put("enddatetime",DateUtil.getDateString(item.getEndDateTime()));
                datas.add(d);
            }
        }

        SimpleAdapter simpleAdapter=new SimpleAdapter(this.getActivity(),datas,R.layout.listview_item,new String[]{"content","begindatetime","enddatetime"},new int[]{R.id.listview_item_lv_title,R.id.listview_item_1,R.id.listview_item_2});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(clickListener);
        listView.setOnItemLongClickListener(longClickListener);
    }

    AdapterView.OnItemClickListener clickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            int itemId=itemList.get(position).getID();
            Intent intent=new Intent(getActivity(),Add.class);
            intent.putExtra("id",itemId);
            startActivity(intent);
        }
    };

    AdapterView.OnItemLongClickListener longClickListener=new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            return false;
        }
    };
    //=========结束==========

}
